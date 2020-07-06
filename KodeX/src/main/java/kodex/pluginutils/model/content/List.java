package kodex.pluginutils.model.content;

import java.io.File;
import java.util.Map;

import kodex.plugininterface.Content;

/**
 * 
 */
public abstract class List implements Content {

    /**
     * Default constructor
     */
    public List() {
    }

    /**
     * @return
     */
    public List getList() {
        // TODO implement here
        return null;
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