package kodex.model;

import javafx.collections.ObservableList;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This is the interface that a given filter must implement for the list of procedures.
 * A filter has the task of sorting the transferred list of procedures 
 * and / or filtering them according to certain properties.
 * 
 * @author StarUML
 * 
 * @version 1.0
 *
 */
public interface FilterStrategy {

    /**
     * The concrete filter strategy
     * 
     * @param selectedProcedures : List of procedures to filter
     * @return filtered list of plugins
     */
	public ObservableList<ProcedurePlugin> filterProcedures(ObservableList<ProcedurePlugin> selectedProcedures);
}