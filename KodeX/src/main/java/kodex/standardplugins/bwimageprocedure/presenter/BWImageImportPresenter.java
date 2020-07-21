package kodex.standardplugins.bwimageprocedure.presenter;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This class is responsible for managing the import 
 * of the black-and-white image or a binary sequence.
 * 
 * @author Patrick Spiesberger
 * @version 1.0
 *
 */
public class BWImageImportPresenter extends ImportPresenter {

	private WritableImage img;
	private String binaryChain;
	
	public BWImageImportPresenter(ProcedurePlugin plugin) {
		super(plugin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validateEncodeImport() {
		if (img == null) {
			System.out.println("Invalid import, no file selected");
			return false;
		}
		else if (img.getWidth() > 500 || img.getHeight() > 500) {
			System.out.println("Invalid import, file too large");
			return false;
		}
		
		//Check if every pixel is black or white
		for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (img.getPixelReader().getColor(x, y) != Color.BLACK 
                		&& img.getPixelReader().getColor(x, y) != Color.WHITE) {
                	return false;
                }
    
            }
		}
		return true;
	}

	@Override
	public boolean validateDecodeImport() {
		if (binaryChain == null) {
			return false;
		}
		for (int i = 0; i < binaryChain.length(); i++) {
			if (binaryChain.charAt(i) != '0' && binaryChain.charAt(i) != '1') {
				return false;
			}
		}
		return true;
	}
                
	@Override
	public void handleEncodeImport() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDecodeImport() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AnchorPane getView() {
		
		AnchorPane importView = new AnchorPane();
		
		// loads the template
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("importexample.fxml"));
            loader.setController(this);
            importView = loader.load();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
		
		return importView;
	}
	
	/**
	 * Converts an image to a writable image
	 */
	private static WritableImage convertToFxImage(Image image) {
	    WritableImage wr = null;
	    if (image != null) {
	        wr = new WritableImage((int) image.getWidth(), (int) image.getHeight());
	        PixelWriter pw = wr.getPixelWriter();
	        for (int x = 0; x < image.getWidth(); x++) {
	            for (int y = 0; y < image.getHeight(); y++) {
	                pw.setArgb(x, y, image.getPixelReader().getArgb(x, y));
	            }
	        }
	    }
	    return wr;
	}
}