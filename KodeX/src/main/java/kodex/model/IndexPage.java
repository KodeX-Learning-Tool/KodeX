package kodex.model;

import java.util.*;

import kodex.plugininterface.ProcedurePlugin;

/**
 * This class contains the list of selected procedure to be displayed
 * on the start page. It can perform operations such as sorting and
 * searching on the list. Every time a new instance of this
 * class is created, it gets the current list of all activated procedures
 * from the Singleton instance of the PluginLoader
 * and is therefore always up to date.
 * 
 * @author Patrick Spiesberger
 *
 * @version 1.0
 */
public class IndexPage {

	/* List of all aviable procedures */
    private List<ProcedurePlugin> availableProcedures = new LinkedList<>();

    /* List of all procedures according to the desired restriction */
    private List<ProcedurePlugin> selectedProcedures = new LinkedList<>();
    
    /**
     * Constructor of class IndexPage. Loads the current list
     * of all activated procededures from PluginLoader.
     */
    public IndexPage() {
    	availableProcedures = PluginLoader.getEnabledProcedurePlugins();
    }



    /**
     * @param procedures
     */
    public void updateProcedures(List<ProcedurePlugin> procedures) {
    }

    /**
     * Returns a list of those procedures related to the
     * one entered Keyword match.
     * 
     * @param searchTerm : search query
     * @return list of procedure plugins
     */
    public List<ProcedurePlugin> findProcedures(String searchTerm) {
        for (ProcedurePlugin plugin : availableProcedures) {
        	if (plugin.getPluginName().startsWith(searchTerm)) {
        		selectedProcedures.add(plugin);
        	}
        }
        return selectedProcedures;
    }

    /**
     * Returns a list of those procedures related to the selected one
     * Filter methods fit. It uses a strategy that uses exactly the 
     * filtering method that matches the selected filter.
     * 
     * @param filter : concrete strategy
     * @return list of procedure plugins
     */
    public List<ProcedurePlugin> filterProcedures(Filter filter) {
    	FilterStrategy strategy = null;
        if (filter.equals(Filter.ALPHABETIC)) {
        	strategy = new AlphaNumericalSort();
        }
        else if (filter.equals(Filter.GRADE)) {
        	strategy = new LabelSort();
        }
        else if (filter.equals(Filter.RELEVANCE)) {
        	strategy = new RelevancySort();
        }
        else if (filter.equals(Filter.NO_FILTER)) {
        	return availableProcedures;
        }
        else {
        	System.out.println("no suitable filter found");
        	return availableProcedures;
        }
    	return strategy.filterProcedures(availableProcedures);
    }

    /**
     * Increases the entered value for relevance in 
     * Sorting_relevancy.properties for the given plugin.
     * 
     * @param plugin : The procedure whose relevance should be increased
     */
    public void increaseRelevancy(ProcedurePlugin plugin) {
        // TODO implement here
    }

}