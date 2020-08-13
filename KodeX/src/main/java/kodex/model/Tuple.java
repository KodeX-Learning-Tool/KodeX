package kodex.model;

/**
 * This class represents a tuple of two generic elements.
 *
 * @author Leonhard Kraft
 * @param <A> Type of the first element.
 * @param <B> Type of the second element.
 */
public class Tuple<A, B> {

  A first;
  B second;

  public Tuple(A first, B second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Get the first element.
   *
   * @return The first element.
   */
  public A getFirst() {
    return this.first;
  }

  /**
   * Get the second element.
   *
   * @return The second element.
   */
  public B getSecond() {
    return this.second;
  }

  /**
   * Set the first element.
   *
   * @param first The first element.
   */
  public void setFirst(A first) {
    this.first = first;
  }

  /**
   * Set the second element.
   *
   * @param second The first element.
   */
  public void setSecond(B second) {
    this.second = second;
  }

  @Override
  public String toString() {
    if (first != null && second != null) {
      return first.toString() + ":" + second.toString();
    } else {
      return null;
    }
  }
}
