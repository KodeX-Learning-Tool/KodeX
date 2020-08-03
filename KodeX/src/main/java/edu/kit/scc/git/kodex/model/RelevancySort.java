package edu.kit.scc.git.kodex.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import edu.kit.scc.git.kodex.plugininterface.ProcedurePlugin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is a filter strategy for the list of procedures. This strategy returns a list of all
 * procedures, which are sorted in order of their relevancy. Each click on a procedure increases the
 * relevance of a procedure
 *
 * @author Patrick Spiesberger
 * @author https://mkyong.com/java/how-to-sort-a-map-in-java/
 * @version 1.0
 */
public class RelevancySort implements FilterStrategy {

  /**
   * Source: https://mkyong.com/java/how-to-sort-a-map-in-java/
   *
   * <p>Sorts map by value
   */
  private static Map<ProcedurePlugin, Integer> sortByValue(
      Map<ProcedurePlugin, Integer> unsortMap) {

    List<Map.Entry<ProcedurePlugin, Integer>> list =
        new LinkedList<Map.Entry<ProcedurePlugin, Integer>>(unsortMap.entrySet());

    Collections.sort(
        list,
        new Comparator<Map.Entry<ProcedurePlugin, Integer>>() {
          @Override
          public int compare(
              Map.Entry<ProcedurePlugin, Integer> o1, Map.Entry<ProcedurePlugin, Integer> o2) {
            return (o1.getValue()).compareTo(o2.getValue());
          }
        });

    Map<ProcedurePlugin, Integer> sortedMap = new LinkedHashMap<ProcedurePlugin, Integer>();
    for (Map.Entry<ProcedurePlugin, Integer> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }

    return sortedMap;
  }

  /* unsorted tuple of plugins and their relevancy */
  private Map<ProcedurePlugin, Integer> unsortMap = new HashMap<ProcedurePlugin, Integer>();

  String url = "sorting_relevancy.properties";

  InputStream input = getClass().getResourceAsStream(url);

  Properties prop = new Properties();

  /** Default constructor of RelevancySort. */
  public RelevancySort() {
    try {
      prop.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void filterProcedures(ObservableList<ProcedurePlugin> selectedProcedures) {
    ObservableList<ProcedurePlugin> filteredProcedures = FXCollections.observableArrayList();
    ObservableList<ProcedurePlugin> noRelevancy = FXCollections.observableArrayList();
    Collections.sort(selectedProcedures); // alphabetical sort

    String value;

    for (ProcedurePlugin plugin : selectedProcedures) {
      value = prop.getProperty(plugin.createProcedureInformation().getName());
      if (value == null || !isNumber(value)) {
        noRelevancy.add(plugin);
      } else {
        unsortMap.put(plugin, Integer.parseInt(value));
      }
    }

    Map<ProcedurePlugin, Integer> sortedMap = sortByValue(unsortMap);

    // add all plugins with entry in relvancy
    filteredProcedures.addAll(sortedMap.keySet());

    // add all plugins with no class label
    filteredProcedures.addAll(noRelevancy);
  }

  /**
   * Checks whether the input is a number.
   *
   * @param toCheck : input as a String
   * @return true if input is a number, otherweise false
   */
  private boolean isNumber(String toCheck) {
    Pattern ptrn = Pattern.compile("-?\\d+");
    return ptrn.matcher(toCheck).matches();
  }
}
