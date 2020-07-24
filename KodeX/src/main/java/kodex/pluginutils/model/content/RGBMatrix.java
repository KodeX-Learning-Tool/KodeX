package kodex.pluginutils.model.content;

import java.io.File;

import javafx.scene.paint.Color;

/**
 * This class holds data in Matrix format. An RGBMatrix consists of a 2D array [rows][cols]
 * containing elements of the type Color.
 */
public class RGBMatrix extends AbstractMatrix<Color> {

	/**
	 * Creates a new RGBMatrix with the given dimensions
	 * @param width The matrix's width
	 * @param height The matrix's height
	 * @throws IllegalArgumentException If either argument is less than or equal to 0
	 */
    public RGBMatrix(int width, int height) throws IllegalArgumentException {
    	if (width <= 0 || height <= 0) throw new IllegalArgumentException();
    	super.matrix = new Color[height][width];
    }

	@Override
	protected File toFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(Object input) {
		RGBMatrix object;
    	
    	if (input == null) {
			System.out.println("Invalid import, no import to validate");
			return false;
		}
    	
    	try {
			object = ((RGBMatrix) input);
		} catch (ClassCastException e) {
			System.out.println("Invalid import, import is of wrong type");
			return false;
		}
    	
    	if (object.getWidth() > MAX_MATRIX_WIDTH || MIN_MATRIX_WIDTH > object.getWidth()) {
    		System.out.println("Ivalid import, wrong dimensions");
    		return false;
    	}

    	if (object.getHeight() > MAX_MATRIX_HEIGHT || MIN_MATRIX_HEIGHT > object.getHeight()) {
    		System.out.println("Ivalid import, wrong dimensions");
    		return false;
    	}
    	
        return true;
	}

}