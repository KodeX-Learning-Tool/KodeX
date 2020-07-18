package kodex.plugininterface;

import java.io.File;
import java.util.Map;

/**
 * This abstract class is the superclass to every type of Content. It manages the content of a level as well
 * the associated header. Additionally, this class contains the functionality to export the Content.
 * Every Content must implement this interface.
 * 
 * @author Patrick Spiesberger
 * @author Yannick Neubert
 * 
 * @version 1.0
 * 
 */
public abstract class Content {

	/**
	 * The header for this content.
	 */
	protected Map<String, Object> header;
	
	
    /**
     * Sets the header for this content
     * @param header : Header of the content as a map
     */
    public void setHeader(Map<String, Object> header) {
    	this.header = header;
	}
    
    
    /**
     * Returns the header of this content
     * @return This contents header
     */
    public Map<String, Object> getHeader() {
		return header;
	}

    /**
     * Exports the content to the given location
     * @param file : The location where this content should be written
     */
    public void export(File file) {
    	//TODO exporting files
    	File export = this.toFile();
	}
    
    /**
     * Creates and returns a file containing the data of this content
     * @return A file containing this contents data
     */
    protected abstract File toFile();

    /**
     * Returns whether the input for this content is valid
     * @param input : Input of the content
     * Note: Must be defined more precisely by classes that implement this interface
     * @return true, if content is valid, otherwise false
     */
    public abstract Boolean isValid(Object input);

}