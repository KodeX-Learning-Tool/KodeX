package kodex.pluginutils.model.content;

import java.io.File;

import kodex.plugininterface.Content;

/**
 * This class holds data in string format. A CharacterString consists of only alphabetical characters.
 * Extending AbstractString, it adds validation and exporting capabilities to Javas String.
 */
public class CharacterString extends AbstractString {

    /**
     * Creates a new CharacterString
     */
    public CharacterString(String str) {
    	super.data = str;
    }
    
	@Override
	protected File toFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public boolean isValid(String input) {
		if (input == null) {
			return false;
		}
		for (int i = 0; i < input.length(); i++) {
			if (!Character.isDigit(input.charAt(i))) {
				return false;
			}
		}
		return true;
    }

}