package kodex.pluginutils.model.content;

import java.io.File;

import javafx.scene.image.WritableImage;
import kodex.plugininterface.Content;

/**
 * This class holds data in Image format. An GrayScaleImage consists of a WritableImage 
 * where each pixels red, green and blue value are all equal.
 * Extending AbstractImage, it adds validation and exporting capabilities to JavaFX's WritableImage.
 */
public class GrayScaleImage extends AbstractImage{
	
	/**
     * Creates a new GrayScaleImage
     */
    public GrayScaleImage() {
    	super.image = new WritableImage(1, 1);
    }

    /**
     * Creates a new GrayScaleImage and sets its data to the image passed in the arguments
     * @param image The WritableImage to be used as data
     */
    public GrayScaleImage(WritableImage image) {
        this.image = image;
    }

	@Override
	protected File toFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isValid(Object input) {
		// TODO Auto-generated method stub
		return null;
	}

}