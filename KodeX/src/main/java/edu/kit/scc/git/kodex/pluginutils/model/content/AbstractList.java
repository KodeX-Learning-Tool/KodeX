package edu.kit.scc.git.kodex.pluginutils.model.content;

import java.util.List;

import edu.kit.scc.git.kodex.plugininterface.Content;

/**
 * This class holds data in list format. It adds validation and exporting capabilities to Javas
 * LinkedList.
 */

//This is a workaround for the isValid method since generics could not be
//used in this context.
//This does however create the warning
//"Content is a raw type. References to generic type Content<T> should be
//parameterized"
//and requires class casting in the subclasses so that the isValid method
//receives the correct generic type of input.
@SuppressWarnings("rawtypes")
public abstract class AbstractList<E> extends Content {

  /** The Constant MAX_IMAGE_WIDTH. */
  protected static final int MAX_LIST_LENGTH = 500000;
  
  /** The LinkedList containing this Contents data. */
  protected List<E> list;

  // below some shortcuts for common actions
  public void add(E element) {
    list.add(element);
  }

  public E get(int index) {
    return list.get(index);
  }

  /**
   * Returns the LinkedList containing this Contents data.
   *
   * @return The LinkedList containing this Contents data
   */
  public List<E> getList() {
    return list;
  }

  /**
   * Sets the LinkedList containing this Contents data.
   *
   * @param list The LinkedList containing this Contents data
   */
  public void setList(List<E> list) {
    this.list = list;
  }

  public int size() {
    return list.size();
  }
}
