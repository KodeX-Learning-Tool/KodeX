package kodex.model;

import java.util.*;

import kodex.plugininterface.ProcedurePlugin;

/**
 * Dies ist eine Filter Strategie für die Liste der Kodierungsverfahren. Diese Strategie gibt die
 * Liste der Verfahrens-Plugins zurück, welche in alphanumerischer Reihenfolge sortiert ist.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public class AlphaNumericalSort implements FilterStrategy {

    /**
     * Konstrukor der Klasse AlphaNumericalSort
     */
    public AlphaNumericalSort() {}

    /**
     * Dies ist eine Filter Strategie für die Liste der Kodierungsverfahren.
     * Diese Strategie gibt die Liste der Verfahrens-Plugins zurück, welche in
     * alphanumerischer Reihenfolge sortiert ist.
     * @param selectedProcedures : zu sortierende Liste der Plugins
     * @return sortierte Liste der Plugins
     */
    @Override
    public List<ProcedurePlugin> filterProcedures(List<ProcedurePlugin> selectedProcedures) {
        Collections.sort(selectedProcedures);
        return selectedProcedures;
    }


}