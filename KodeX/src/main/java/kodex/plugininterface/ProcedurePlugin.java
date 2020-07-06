package kodex.plugininterface;

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
public abstract class ProcedurePlugin implements Pluginable {

    /**
     * Constructor of the ProcedurePlugin class
     */
    public ProcedurePlugin() {}

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
     * Erzeugt eine neue ProcedureInformation Instanz und gibt diese zurück
     * @return Erzeugte Instanz von ProcedureInformation
     */
    public abstract ProcedureInformation createProcedureInformation();

    /**
     * Erzeugt eine neue ImportPresenter Instanz und gibt diese zurück
     * @return Erzeugte Instanz von ImportPresenter
     */
    public abstract ImportPresenter createImportPresenter();

}