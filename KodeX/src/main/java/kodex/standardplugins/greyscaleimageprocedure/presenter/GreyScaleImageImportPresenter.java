package kodex.standardplugins.greyscaleimageprocedure.presenter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This class is responsible for managing the import 
 * of the greyscale image or a binary sequence.
 * 
 * @author Patrick Spiesberger
 * @version 1.0
 *
 */
public class GreyScaleImageImportPresenter extends ImportPresenter {
	
	private WritableImage img;
	private String binaryChain;

	public GreyScaleImageImportPresenter(ProcedurePlugin plugin) {
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
		
		//Check if image is a greyscale image
		//Source of Code: https://stackoverflow.com/a/36157968
		int pixel,red, green, blue;
	    for (int x = 0; x < img.getWidth(); x++) {
	        for (int y = 0; y < img.getHeight(); y++) {
	            pixel = img.getPixelReader().getArgb(x, y);
	            red = (pixel >> 16) & 0xff;
	            green = (pixel >> 8) & 0xff;
	            blue = (pixel) & 0xff;
	            if (red != green || green != blue ) return false;
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
		// TODO Auto-generated method stub
		return null;
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