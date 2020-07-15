package kodex.pluginutils.model.content;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import kodex.plugininterface.Content;

/**
 * This class holds data in image format. It adds validation and exporting capabilities
 * to JavaFX's WritableImage.
 */
public abstract class AbstractImage extends Content {

	/**
	 * The WritableImage containing this Contents data
	 */
	protected WritableImage image;

	/**
	 * Sets the size of this Contents WritableImage
	 * @param width The width of the new Image
	 * @param height The height of the new Image
	 * @throws IllegalArgumentException If either argument is less than or equal 0
	 */
    public void setSize(int width, int height) throws IllegalArgumentException {
    	image = new WritableImage(width, height);
    }

	/**
	 * Returns the WritableImage containing this Contents data
	 * @return The WritableImage containing this Contents data
	 */
    public WritableImage getImage() {
    	return image;
    }
    
    /**
     * Sets the WritableImage containing this Contents data
     * @param image The WritableImage containing this Contents data
     */
    public void setImage(WritableImage image) {
    	this.image = image;
    }
    
    
    
    //below some shortcuts for common actions
    public int getWidth() {
    	return (int) image.getWidth();
    }
    
    public int getHeight() {
    	return (int) image.getHeight();
    }
    
    public Color getColor(int x, int y) {
    	return image.getPixelReader().getColor(x, y);
    }
    
    public void setColor(int x, int y, Color color) {
    	image.getPixelWriter().setColor(x, y, color);
    }
}