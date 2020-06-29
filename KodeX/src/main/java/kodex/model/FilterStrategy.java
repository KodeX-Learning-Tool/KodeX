package kodex.model;

import java.util.*;

import kodex.plugininterface.ProcedurePlugin;

/**
 * 
 */
public interface FilterStrategy {

    /**
     * @param selectedProcedures 
     * @return
     */
    public List<ProcedurePlugin> filterProcedures(List<ProcedurePlugin> selectedProcedures);

}