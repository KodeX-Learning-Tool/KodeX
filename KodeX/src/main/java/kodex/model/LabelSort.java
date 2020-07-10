package kodex.model;

import java.util.*;
import java.util.regex.Pattern;

import kodex.plugininterface.ProcedurePlugin;

/**
 * 
 */
public class LabelSort implements FilterStrategy {

    /**
     * Default constructor
     */
    public LabelSort() {}

	@Override
	public List<ProcedurePlugin> filterProcedures(List<ProcedurePlugin> selectedProcedures) {
		List<ProcedurePlugin> filteredProcedures = new LinkedList<ProcedurePlugin>();
		List<ProcedurePlugin> noLabel = new LinkedList<ProcedurePlugin>();
		Collections.sort(selectedProcedures); //alphabetical sort
		for (ProcedurePlugin plugin : selectedProcedures) {
			if (plugin.createProcedureInformation().getLabels().get(0).isEmpty() || 
					!isNumber(plugin.createProcedureInformation().getLabels().get(0))) {
				selectedProcedures.remove(plugin);
				noLabel.add(plugin);
			}
		}
		return filteredProcedures;
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

}
