package kodex.pluginutils.model.content;

import kodex.plugininterface.Content;

/**
 * This class holds data in string format. It adds validation and exporting capabilities
 * to Javas String.
 */
public abstract class AbstractString extends Content{

	/**
	 * The String containing this Contents data
	 */
	protected String data;

	/**
	 * Returns the String containing this Contents data
	 * @return The String containing this Contents data
	 */
	public String getString() {
		return data;
	}
	
	/**
	 * Sets the String containing this Contents data
	 * @param data The String containing this Contents data
	 */
	public void setString(String data) {
		this.data = data;
	}
}