package kodex.plugininterface;

import java.io.File;
import java.util.Map;

/**
 * This class represents the interface for content. It manages the content of a level as well
 * the associated header. It also contains the functionality, to exported the content and
 * implements the identification number.
 * Every content must implement this interface.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 * 
 */
public interface Content {

    /**
     * Sets the header for this content
     * @param header : Header of the content as a map
     */
    public void setHeader(Map header);
    
    
    /**
     * Returns the value of the header of this content for a given key
     * @param key : The key to access elements of the header
     * @return The value that is identified using the key from the header
     */
    public String getHeaderValue(String key);

    /**
     * Exports the content to the given location
     * @param file : The location where this content should be written
     */
    public void export(File file);

    /**
     * Returns whether the input for this content is valid
     * @param input : Input of the content
     * Note: Must be defined more precisely by classes that implement this interface
     * @return true, if content is valid, otherwise false
     */
    public Boolean isValid(Object input);

}