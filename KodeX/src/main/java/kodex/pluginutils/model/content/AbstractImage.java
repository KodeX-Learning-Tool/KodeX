package kodex.pluginutils.model.content;

import java.io.File;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import kodex.plugininterface.Content;

/**
 * 
 */
public abstract class AbstractImage implements Content {

    /**
     * Default constructor
     */
    public AbstractImage() {
    }

    /**
     * 
     */
    protected Image image;

    /**
     * @param x 
     * @param y 
     * @return
     */
    public Color getColor(int x, int y) {
        // TODO implement here
        return null;
    }

    /**
     * @param x 
     * @param y 
     * @param color
     */
    public void setColor(int x, int y, Color color) {
        // TODO implement here
    }

    /**
     * @param header
     */
    public void setHeader(Map header) {
        // TODO implement here
    }

    /**
     * @param key 
     * @return
     */
    public String getHeaderValue(String key) {
        // TODO implement here
        return "";
    }

    /**
     * @param file
     */
    public void export(File file) {
        // TODO implement here
    }

    /**
     * @param input 
     * @return
     */
    public Boolean isValid(Object input) {
        // TODO implement here
        return null;
    }

}