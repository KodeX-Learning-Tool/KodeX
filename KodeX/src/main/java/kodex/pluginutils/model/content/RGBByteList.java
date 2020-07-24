package kodex.pluginutils.model.content;

import java.io.File;
import java.util.LinkedList;

/**
 * This class holds data in LinkedList format. An RGBByteList consists of 
 * RGB triplets in exactly that order in binary.
 * Extending AbstractList, it adds validation and exporting capabilities to Javas List.
 */
public class RGBByteList extends AbstractList<String> {

    /**
     * Creates a new RGBByteList
     */
    public RGBByteList() {
    	super.list = new LinkedList<String>();
    }

    @Override
    public boolean isValid(Object input) { 
    	RGBByteList object;
    	
    	if (input == null) {
			System.out.println("Invalid import, no import to validate");
			return false;
		}
    	
    	try {
			object = ((RGBByteList) input);
		} catch (ClassCastException e) {
			System.out.println("Invalid import, import is of wrong type");
			return false;
		}
    	
    	if (object.size() % 3 != 0) {
    		System.out.println("Invalid import, import does not excludingly contain rgb triplets");
    		return false;
    	}
    	
    	for (int i = 0; i < object.size(); i++) {
    		String rgb = object.get(i);
    		if (rgb.length() != 8) {
    			System.out.println("Invalid import, import does not excludingly contain rgb values");
    			return false;
    		}
    		
    		for (int j = 0; j < rgb.length(); j++) {
    			if (rgb.charAt(i) != '0' && rgb.charAt(i) != '1') {
    				System.out.println("Invalid import, import does not excludingly contain rgb values");
    				return false;
    			}
    		}
    	}
    	
        return true;
    }

	@Override
	public void export(File file) {
		// TODO Auto-generated method stub
		
	}

}