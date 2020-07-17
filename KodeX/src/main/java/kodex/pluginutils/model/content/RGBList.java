package kodex.pluginutils.model.content;

import java.io.File;
import java.util.LinkedList;

import javafx.scene.paint.Color;
import kodex.plugininterface.Content;

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