package PluginUtils.Model.Content;

import java.util.Map;

/**
 * 
 */
public abstract class DataString implements Content {

    /**
     * Default constructor
     */
    public DataString() {
    }

    /**
     * @return
     */
    public String getDataString() {
        // TODO implement here
        return "";
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