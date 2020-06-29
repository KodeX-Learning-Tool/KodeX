package kodex.model;

import java.util.*;

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