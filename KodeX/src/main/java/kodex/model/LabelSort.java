package kodex.model;

import java.util.*;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This is a filter strategy for the list of procedures.
 * This strategy returns a list of all procedures, which 
 * are sorted in order of their given class level.
 * If the class level is the same, the notation is alphanumeric.
 * If no class level is specified, the plugins can be found at the end of the list
 * 
 * @author Patrick Spiesberger
 * @author https://mkyong.com/java/how-to-sort-a-map-in-java/
 * 
 * @version 1.0
 */
public class LabelSort implements FilterStrategy {
    
    /* unsorted tuple of plugins and their class level */
    private Map<ProcedurePlugin, Integer> unsortMap = new HashMap<ProcedurePlugin, Integer>();
    
    /**
     * Default constructor of LabelSort
     */
    public LabelSort() {}
    

	@Override
	public void filterProcedures(ObservableList<ProcedurePlugin> selectedProcedures) {
		
		ObservableList<ProcedurePlugin> filteredProcedures = FXCollections.observableArrayList();
		ObservableList<ProcedurePlugin> noLabel = FXCollections.observableArrayList();
		Collections.sort(selectedProcedures); //alphabetical sort
		
		for (ProcedurePlugin plugin : selectedProcedures) {
		    
		    ProcedureInformation info = plugin.createProcedureInformation();
		    ObservableList<String> labels = info.getLabels();
		    
			if (labels.isEmpty()|| 
					!isNumber(labels.get(0))) {
				noLabel.add(plugin);
			}
			else {
				unsortMap.put(plugin, Integer.valueOf(labels.get(0)));
			}
		}
		
		Map<ProcedurePlugin, Integer> sortedMap = sortByValue(unsortMap);
		
		//add all plugins with class label
		filteredProcedures.addAll(sortedMap.keySet());
		
		//add all plugins with no class label
		filteredProcedures.addAll(noLabel);
	}	
	
	/**
	 * Checks whether the input is a number
	 * 
	 * @param toCheck : input as a String
	 * @return true if input is a number, otherweise false
	 */
	private  boolean isNumber(String toCheck) {
		Pattern ptrn = Pattern.compile("-?\\d+");
		return ptrn.matcher(toCheck).matches();
	}
	
	/**
	 * Source: https://mkyong.com/java/how-to-sort-a-map-in-java/
	 * 
	 * Sorts map by value
	 */
	private static Map<ProcedurePlugin, Integer> sortByValue(Map<ProcedurePlugin, Integer> unsortMap) {

        List<Map.Entry<ProcedurePlugin, Integer>> list =
                new LinkedList<Map.Entry<ProcedurePlugin, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<ProcedurePlugin, Integer>>() {
            public int compare(Map.Entry<ProcedurePlugin, Integer> o1,
                               Map.Entry<ProcedurePlugin, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<ProcedurePlugin, Integer> sortedMap = new LinkedHashMap<ProcedurePlugin, Integer>();
        for (Map.Entry<ProcedurePlugin, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        return sortedMap;
    }
}
