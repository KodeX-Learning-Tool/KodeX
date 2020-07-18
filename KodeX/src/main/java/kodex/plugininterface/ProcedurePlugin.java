package kodex.plugininterface;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class forms the communication interface between a plugin and the framework.
 * All classes that extend this class are loaded by the PluginLoader using the 
 * ServiceLoader and viewed as a process plugin. The framework thus initially
 * communicates with a generalization of this class.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public abstract class ProcedurePlugin implements Pluginable, Comparable<ProcedurePlugin> {
	
	/** The BooleanProperty activated stores the state of the procedure. */
	protected final BooleanProperty activated = new SimpleBooleanProperty(false);
	
	/** The StringProperty of the plugin name. */
	protected StringProperty pluginName = new SimpleStringProperty("missing_plugin_name");
	
	/** The StringProperty of the plugin description. */
	protected StringProperty pluginDescription = new SimpleStringProperty("missing_plugin_description");
	
    /**
     * Constructor of the ProcedurePlugin class
     */
    public ProcedurePlugin() {
    	
    }

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
    	return this.createProcedureInformation().getName().compareTo(pluginNameProperty().get());
    }
    
    @Override
	public BooleanProperty activatedProperty() {
		return activated;
	}
    

	@Override
	public StringProperty pluginNameProperty() {
		return pluginName;
	}

	@Override
	public StringProperty pluginDescriptionProperty() {
		return pluginDescription;
	}
}