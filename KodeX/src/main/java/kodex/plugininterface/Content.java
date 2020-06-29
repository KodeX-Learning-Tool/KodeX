package kodex.plugininterface;

import java.util.*;

/**
 * 
 */
public interface Content {

    /**
     * @param header
     */
    public void setHeader(Map header);

    /**
     * @param key 
     * @return
     */
    public String getHeaderValue(String key);

    /**
     * @param file
     */
    public void export(File file);

    /**
     * @param input 
     * @return
     */
    public Boolean isValid(Object input);

}