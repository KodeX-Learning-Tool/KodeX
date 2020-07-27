package kodex.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The class is an enumeration of different ways of sorting / filtering procedures.
 *
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * @version 1.0
 */
public enum Filter {
  ALPHABETIC("filter.alphabetic"), // alphabetical order
  RELEVANCE("filter.relevance"), // Sorting how often a procedure was used
  GRADE("filter.grade"), // Filter by specified class level
  NO_FILTER("filter.nofilter"); // no active Filter

  private StringProperty filterName = new SimpleStringProperty(this, "filterName", "");

  Filter(String string) {
    this.filterName.bind(I18N.createStringBinding(string));
  }

  @Override
  public String toString() {
    return filterName.get();
  }
}
