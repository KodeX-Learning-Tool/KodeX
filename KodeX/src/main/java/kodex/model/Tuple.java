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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((first == null) ? 0 : first.hashCode());
    result = prime * result + ((second == null) ? 0 : second.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Tuple)) {
      return false;
    }
    Tuple<A, B> other = (Tuple<A, B>) obj;
    if (first == null) {
      if (other.first != null) {
        return false;
      }
    } else if (!first.equals(other.first)) {
      return false;
    }
    if (second == null) {
      if (other.second != null) {
        return false;
      }
    } else if (!second.equals(other.second)) {
      return false;
    }
    return true;
  }
}
