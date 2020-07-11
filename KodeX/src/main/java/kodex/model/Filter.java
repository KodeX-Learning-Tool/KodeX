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
    ALPHABETIC, //alphabetical order
    RELEVANCE, //Sorting how often a procedure was used
    GRADE, //Filter by specified class level
    NO_FILTER //no active Filter
}