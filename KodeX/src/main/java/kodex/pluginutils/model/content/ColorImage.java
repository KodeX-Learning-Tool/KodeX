package kodex.pluginutils.model.content;

import java.io.File;

import javafx.scene.image.WritableImage;
import kodex.plugininterface.Content;

/**
 * This class holds data in Image format. An ColorImage consists of a Writable RGB Image.
 * Extending AbstractImage, it adds validation and exporting capabilities to JavaFX's WritableImage.
 */
public class ColorImage extends AbstractImage {
	
	/**
     * Creates a new ColorImage
     */
    public ColorImage() {
    	super.image = new WritableImage(1, 1);
    }

    /**
     * Creates a new ColorImage and sets its data to the image passed in the arguments
     * @param image The WritableImage to be used as data
     */
    public ColorImage(WritableImage image) {
        this.image = image;
    }

	@Override
	protected File toFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public boolean isValid(WritableImage input) {
        // TODO Auto-generated method stub
        return false;
    }
}