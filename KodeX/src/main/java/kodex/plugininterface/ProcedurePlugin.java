package kodex.plugininterface;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * This class forms the communication interface between a plugin and the framework.
 * All classes that extend this class are loaded by the PluginLoader using the 
 * ServiceLoader and viewed as a process plugin. The framework thus initially
 * communicates with a generalization of this class.
 * 
 * @author Patrick Spiesberger
 * @author Leonhard Kraft
 * 
 * @version 1.0
 *
 */
public abstract class ProcedurePlugin implements Pluginable, Comparable<ProcedurePlugin> {
	
	/** The BooleanProperty activated stores the state of the procedure. */
	protected final BooleanProperty activated = new SimpleBooleanProperty(false);

    /**
     * Returns the start link of the procedure, whereby the stages
     * are saved internally as a double-linked list and are linked accordingly
     * @return presenter for first link of procedure
     */
    public abstract ChainLinkPresenter getChainHead();

    /**
     * Sets the content for the first link of the process when encoding.
     * In addition, the method creates all linkes of the process and thus initializes them
     * @param content : Content for the first step of the procedure
     */
    public abstract void initEncodeProcedure(Content content);

    /**
     * Sets the content for the first link of the process when decoding.
     * In addition, the method creates all linkes of the process and thus initializes them
     * @param content : Content for the first step of the procedure
     */
    public abstract void initDecodeProcedure(Content content);

    /**
     * Creates and returns a new ProcedureInformation instance
     * @return created instance of ProcedureInformation
     */
    public abstract ProcedureInformation createProcedureInformation();

    /**
     * Creates and returns a new ImportPresenter instance
     * @return created instance of ImportPresenter
     */
    public abstract ImportPresenter createImportPresenter();
    
    /**
     * alphabetische Sortierfunktion
     */
    @Override
    public int compareTo(ProcedurePlugin plugin) {
        ProcedureInformation ourInfo = this.createProcedureInformation();
        ProcedureInformation theirInfo = plugin.createProcedureInformation();
        
    	return ourInfo.getName().compareTo(theirInfo.getName());
    }
    
    @Override
	public BooleanProperty activatedProperty() {
		return activated;
	}
}