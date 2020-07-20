package kodex.pluginutils.model.content;

import java.io.File;

import javafx.scene.image.WritableImage;
import kodex.plugininterface.Content;

/**
 * This class holds data in Image format. An BlackWhitImage consists of a WritableImage 
 * using only the RGB values 0x000000 and 0xFFFFFF.
 * Extending AbstractImage, it adds validation and exporting capabilities to JavaFX's WritableImage.
 */
public class BlackWhiteImage extends AbstractImage{
	
	/**
     * Creates a new BlackWhiteImage
     */
    public BlackWhiteImage() {
    	super.image = new WritableImage(1, 1);
    }

    /**
     * Creates a new BlackWhiteImage and sets its data to the image passed in the arguments
     * @param image The WritableImage to be used as data
     */
    public BlackWhiteImage(WritableImage image) {
        this.image = image;
    }

	@Override
	protected File toFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public boolean isValid(Object input) {
        // TODO Auto-generated method stub
        return false;
    }

}