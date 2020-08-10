package kodex.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This is a filter strategy for the list of procedures. This strategy returns a list of all
 * procedures, which are sorted in order of their relevancy. Each click on a procedure increases the
 * relevance of a procedure
 *
 * @author Patrick Spiesberger
 * @author https://dzone.com/articles/how-to-sort-a-map-by-value-in-java-8
 * @version 1.0
 */
public class RelevancySort implements FilterStrategy {

  /**
   * Source: https://dzone.com/articles/how-to-sort-a-map-by-value-in-java-8
   * Sorts map by value
   */
  private static Map<ProcedurePlugin, Integer> 
      sortByValue(final Map<ProcedurePlugin, Integer> wordCounts) {
    return wordCounts.entrySet()
            .stream()
            .sorted((Map.Entry.<ProcedurePlugin, Integer>comparingByValue().reversed()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (e1, e2) -> e1, LinkedHashMap::new));
  }

  /* unsorted tuple of plugins and their relevancy */
  Map<ProcedurePlugin, Integer> unsortedMap = new HashMap<ProcedurePlugin, Integer>();

  /** Default constructor of RelevancySort. */
  public RelevancySort() {
    unsortedMap = new IndexPage().getValueProcedure();
  }

  @Override
  public void filterProcedures(ObservableList<ProcedurePlugin> selectedProcedures) {

    Map<ProcedurePlugin, Integer> sortedMap = sortByValue(unsortedMap);
    
    selectedProcedures.clear();

    // add all plugins in sorted list
    selectedProcedures.addAll(sortedMap.keySet());

  }
}
