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
		return true;
	}

	@Override
	public boolean validateDecodeImport() {
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
		
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bwImageImport.fxml"));
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