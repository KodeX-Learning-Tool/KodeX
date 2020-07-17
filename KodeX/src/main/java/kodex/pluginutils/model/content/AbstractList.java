package kodex.pluginutils.model.content;

import java.util.LinkedList;

import kodex.plugininterface.Content;

/**
 * This class holds data in list format. It adds validation and exporting capabilities
 * to Javas LinkedList.
 */
public abstract class AbstractList<E> extends Content {

	/**
	 * The LinkedList containing this Contents data
	 */
	protected LinkedList<E> list;
	
    /**
     * Returns the LinkedList containing this Contents data
     * @return The LinkedList containing this Contents data
     */
    public LinkedList<E> getList() {
        return list;
    }
    
    /**
     * Sets the LinkedList containing this Contents data
     * @param list The LinkedList containing this Contents data
     */
    public void setList(LinkedList<E> list) {
    	this.list = list;
    }

    
    
    //below some shortcuts for common actions
    public void add(E element) {
    	list.add(element);
    }
    
    public E get(int index) {
    	return list.get(index);
    }
    
    public int size() {
    	return list.size();
    }
}