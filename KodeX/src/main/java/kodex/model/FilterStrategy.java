package kodex.model;

import java.util.List;

import kodex.plugininterface.ProcedurePlugin;

/**
 * Dies ist das Interface, dass ein gegebener Filter für die Liste der Kodierungsverfahren 
 * implementieren muss. Ein Filter hat die Aufgabe, die übergebene Liste der 
 * Kodierungsverfahren zu sortieren oder/und diese nach bestimmten Eigenschaften zu filtern.
 * 
 * @author Automatisch erzeugt durch StarUML
 * 
 * @version 1.0
 *
 */
public interface FilterStrategy {

    /**
     * Die konkrete Filter Strategie
     * 
     * @param selectedProcedures : zu filternde Liste der Plugins
     * @return gefilterte Liste der Plugins
     */
    public List<ProcedurePlugin> filterProcedures(List<ProcedurePlugin> selectedProcedures);

}