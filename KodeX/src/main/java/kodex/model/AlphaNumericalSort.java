package kodex.model;

import java.util.*;

import javafx.collections.ObservableList;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This is a filter strategy for the list of procedures.
 * This strategy returns the list of procedures, which are sorted in alphanumeric order.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public class AlphaNumericalSort implements FilterStrategy {

    /**
     * Constructor of the AlphaNumeric Sort class
     */
    public AlphaNumericalSort() {}

    /**
     * This is a filter strategy for the list of procedures.
     * This strategy returns the list of procedures, which are sorted in alphanumeric order.
     * 
     * @param selectedProcedures : ObservableList of plugins to be sorted
     * @return sorted ObservableList of plugins
     */
    @Override
    public void filterProcedures(ObservableList<ProcedurePlugin> selectedProcedures) {
        Collections.sort(selectedProcedures);
    }
}