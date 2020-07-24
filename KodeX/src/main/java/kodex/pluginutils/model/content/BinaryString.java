package kodex.pluginutils.model.content;

import java.io.File;

/**
 * This class holds data in string format. A BinaryString consists of only 1's and 0's.
 * Extending AbstractString, it adds validation and exporting capabilities to Javas String.
 */
public class BinaryString extends AbstractString {
	
    /**
     * Creates a new BinaryString
     */
    public BinaryString() {
    	super.data = "";
    }
	
    /**
     * Creates a new BinaryString with the given input.
     */
    public BinaryString(String input) {
    	super.data = input;
    }

	@Override
	public boolean isValid(String input) {
		if (input == null) {
			return false;
		}
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) != '0' && input.charAt(i) != '1') {
				return false;
			}
		}
		return true;
	}

	@Override
	public void export(File file) {
		// TODO Auto-generated method stub
		
	}

}