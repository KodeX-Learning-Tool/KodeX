package kodex.model;

/**
 * The class is an enumeration of different ways of
 * sorting / filtering procedures.
 * 
 * @author StarUML
 * 
 * @version 1.0
 *
 */
public enum Filter {
    ALPHABETIC("Alphabetic"), //alphabetical order
    RELEVANCE("Relevance"), //Sorting how often a procedure was used
    GRADE("Grade"), //Filter by specified class level
    NO_FILTER("Don't filter"); //no active Filter
	
	public final String filterName;
	
	Filter(String name) {
		this.filterName = name;
	}
	
    @Override
    public String toString() {
        return filterName;
    }
}