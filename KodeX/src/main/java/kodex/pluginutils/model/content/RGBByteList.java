package kodex.pluginutils.model.content;

import java.io.File;
import java.util.LinkedList;

import kodex.plugininterface.Content;

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
    public Boolean isValid(Content input) {
        // TODO implement here
        return null;
    }

	@Override
	protected File toFile() {
		// TODO Auto-generated method stub
		return null;
	}

}