package kodex.pluginutils.model.content;

import java.io.File;
import java.util.Map;

import kodex.plugininterface.Content;

/**
 * 
 */
public abstract class Matrix implements Content {

    /**
     * Default constructor
     */
    public Matrix() {
    }

    /**
     * 
     */
    protected int rows;

    /**
     * 
     */
    protected int cols;

    /**
     * @return
     */
    public int getRows() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public int getCols() {
        // TODO implement here
        return 0;
    }

    /**
     * @param markId 
     * @param value
     */
    public void setCell(int markId, int value) {
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
        return true;
    }

}