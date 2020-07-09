package kodex.model;

import java.util.*;

import kodex.plugininterface.ProcedurePlugin;

/**
 * 
 */
public class IndexPage {

    /**
     * Default constructor
     */
    public IndexPage() {
    }

    /**
     * 
     */
    private List<ProcedurePlugin> availableProcedures = new LinkedList<ProcedurePlugin>();

    /**
     * 
     */
    private List<ProcedurePlugin> selectedProcedures = new LinkedList<ProcedurePlugin>();

    /**
     * 
     */
    private FilterStrategy filterStrategy;



    /**
     * @param procedures
     */
    public void updateProcedures(List<ProcedurePlugin> procedures) {
        // TODO implement here
    }

    /**
     * @param searchTerm 
     * @return
     */
    public List<ProcedurePlugin> findProcedures(String searchTerm) {
        // TODO implement here
        return null;
    }

    /**
     * @param filter 
     * @return
     */
    public List<ProcedurePlugin> filterProcedures(Filter filter) {
        // TODO implement here
        return null;
    }

    /**
     * @param plugin
     */
    public void increaseRelevancy(ProcedurePlugin plugin) {
        // TODO implement here
    }

}