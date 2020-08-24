package kodex.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This is a filter strategy for the list of procedures. This strategy returns a
 * list of all procedures, which are sorted in order of their given class level.
 * If the class level is the same, the notation is alphanumeric. If no class
 * level is specified, the plugins can be found at the end of the list
 *
 * @author Patrick Spiesberger
 * @author https://dzone.com/articles/how-to-sort-a-map-by-value-in-java-8
 * @version 1.0
 */
public class LabelSort implements FilterStrategy {

  /**
   * Source: https://dzone.com/articles/how-to-sort-a-map-by-value-in-java-8
   * Sorts map by value
   */
  private static Map<ProcedurePlugin, Integer> 
      sortByValue(final Map<ProcedurePlugin, Integer> wordCounts) {
    return wordCounts.entrySet().stream().sorted((Map.Entry.<ProcedurePlugin,
        Integer>comparingByValue()))
        .collect(Collectors.toMap(Map.Entry::getKey,
            Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }

  /* unsorted tuple of plugins and their class level */
  private Map<ProcedurePlugin, Integer> unsortMap = new HashMap<>();

  /** Default constructor of LabelSort. */
  public LabelSort() {
  }

  @Override
  public void filterProcedures(ObservableList<ProcedurePlugin> selectedProcedures) {

    ObservableList<ProcedurePlugin> noLabel = FXCollections.observableArrayList();
    Collections.sort(selectedProcedures); // alphabetical sort


    for (ProcedurePlugin plugin : selectedProcedures) {

      ProcedureInformation info = plugin.createProcedureInformation();
      ObservableList<String> labels = info.getLabels();

      if (labels.isEmpty() || !isNumber(labels.get(0))) {
        noLabel.add(plugin);
      } else {
        unsortMap.put(plugin, Integer.valueOf(labels.get(0)));
      }
    }
    
    Map<ProcedurePlugin, Integer> sortedMap = sortByValue(unsortMap);

    selectedProcedures.clear();
    // add all plugins with class label
    selectedProcedures.addAll(sortedMap.keySet());
    
    Collections.sort(noLabel); // alphabetical sort
    
    // add all plugins with no class label
    selectedProcedures.addAll(noLabel);
  }

  /**
   * Checks whether the input is a number.
   *
   * @param toCheck : input as a String
   * @return true if input is a number, otherwise false
   */
  private boolean isNumber(String toCheck) {
    Pattern ptrn = Pattern.compile("-?\\d+");
    return ptrn.matcher(toCheck).matches();
  }
}
