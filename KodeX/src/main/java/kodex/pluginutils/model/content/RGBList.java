package kodex.pluginutils.model.content;

import java.io.File;
import java.util.LinkedList;

import javafx.scene.paint.Color;

/**
 * This class holds data in LinkedList format. An RGBList consists of colors.
 * Extending AbstractList, it adds validation and exporting capabilities to Javas List.
 */
public class RGBList extends AbstractList<Color> {

    /**
     * Creates a new RGBList
     */
    public RGBList() {
    	super.list = new LinkedList<Color>();
    }

    @Override
    public boolean isValid(Object input) {
    	RGBList object;
    	
    	if (input == null) {
			System.out.println("Invalid import, no import to validate");
			return false;
		}
    	
    	try {
			object = ((RGBList) input);
		} catch (ClassCastException e) {
			System.out.println("Invalid import, import is of wrong type");
			return false;
		}
    	
    	return true;
    }

	@Override
	public void export(File file) {
		// TODO Auto-generated method stub
		
	}

}