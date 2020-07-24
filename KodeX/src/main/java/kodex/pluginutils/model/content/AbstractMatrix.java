package kodex.pluginutils.model.content;

import kodex.plugininterface.Content;

/**
 * This class holds data in matrix format. The Matrix is stored in a 2D Array 
 * with the format [rows][cols].
 */
public abstract class AbstractMatrix<E> extends Content {  

	protected E[][] matrix;
	
	//Java doesn't allow the creation of an array of a generic type directly, so a cast is needed
	//This will, however, create the unavoidable warning "Unchecked cast"
	//which we can safely ignore in the context of our usage due to the abstract nature of this class
	@SuppressWarnings("unchecked")
	public void setSize(int width, int height) throws IllegalArgumentException {
    	if (width <= 0 || height <= 0) throw new IllegalArgumentException();
		matrix = (E[][]) new Object[height][width];
	}
	
	/**
	 * Returns the Matrix containing this Contents data. Note that this matrix has the Format [rows][cols}
	 * @return The Matrix containing this Contents data
	 */
	public E[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * Sets the Matrix containing this Contents data. Note that this matrix has the Format [rows][cols}
	 * @param matrix The Matrix containing this Contents data
	 */
	public void setMatrix(E[][] matrix) {
		this.matrix = matrix;
	}
	
	/**
	 * Returns the height of the Matrix containing this Contents data
	 * @return The height of the Matrix containing this Contents data
	 */
	public int getHeight() {
		return matrix.length;
	}
	
	/**
	 * Returns the width of the Matrix containing this Contents data
	 * @return The width of the Matrix containing this Contents data
	 */
	public int getWidth() {
		return matrix[0].length;
	}
	
	
	
	 //below some shortcuts for common actions
	public E get(int x, int y) {
		return matrix[y][x];
	}
	
	public void set(int x, int y, E element) {
		matrix[y][x] = element;
	}
}