package kodex.plugininterface;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import kodex.exceptions.AlertWindowException;

/**
 * This class forms the communication interface between a plugin and the framework. All classes that
 * extend this class are loaded by the PluginLoader using the ServiceLoader and viewed as a process
 * plugin. The framework thus initially communicates with a generalization of this class.
 *
 * @author Patrick Spiesberger
 * @author Leonhard Kraft
 * @version 1.0
 */
public abstract class ProcedurePlugin extends Plugin implements Comparable<ProcedurePlugin> {
  
  /** The array of ChainLinkPresenter chainLinks stores the chain links for fast access. */
  protected ChainLinkPresenter[] chainLinks;
  
  /** The BooleanProperty activated stores the state of the procedure. */
  protected final BooleanProperty activated = new SimpleBooleanProperty(false);

  @Override
  public BooleanProperty activatedProperty() {
    return activated;
  }
  
  @Override
  public boolean equals(Object v) {
    boolean retVal = false;
    
    // use the plugin name for comparing two plugins
    if (v instanceof ProcedurePlugin) {
      ProcedurePlugin ptr = (ProcedurePlugin) v;
      retVal = ptr.pluginNameProperty().get().equals(this.pluginNameProperty().get());
    }

    return retVal;
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash
        + (this.pluginNameProperty().get() != null ? this.pluginNameProperty().get().hashCode()
            : 0);
    return hash;
  }

  @Override
  public int compareTo(ProcedurePlugin plugin) {
    ProcedureInformation ourInfo = this.createProcedureInformation();
    ProcedureInformation theirInfo = plugin.createProcedureInformation();

    return ourInfo.getName().compareTo(theirInfo.getName());
  }
  
  /**
   * Initializes the Procedure. This method is called when a procedure is selected
   * on the index page. It should initialize the procedure by creating all chain
   * link presenter and storing them in the {@link ProcedurePlugin#chainLinks}
   * array (default) or any other data structure which is used to get the
   * ChainLinkPresenter. If the user has selected the same procedure during a
   * single session more than once then this method clears the data each time.
   */
  public abstract void initializeProcedure();

  /**
   * Creates and returns a new ImportPresenter instance.
   *
   * @return created instance of ImportPresenter
   */
  public abstract ImportPresenter createImportPresenter();

  /**
   * Creates and returns a new ProcedureInformation instance.
   *
   * @return created instance of ProcedureInformation
   */
  public abstract ProcedureInformation createProcedureInformation();

  /**
   * Returns the start chain link of the procedure, whereby the stages are saved internally as a
   * double-linked list and are linked accordingly.
   *
   * @return presenter for first link of procedure
   */
  public ChainLinkPresenter getChainHead() {
    return chainLinks[0];
  }
  

  /**
   * Returns the last chain link of the procedure, whereby the stages are saved internally as a
   * double-linked list and are linked accordingly.
   *
   * @return presenter for last link of procedure
   */
  public ChainLinkPresenter getChainTail() {
    return chainLinks[chainLinks.length - 1];
  }


  /**
   * Sets the content for the first chain link of the process when decoding. In addition, the method
   * creates all linkes of the process and thus initializes them
   *
   * @param content : Content for the first step of the procedure
   * @throws AlertWindowException if an error happens when calculating the chain
   */
  public void initDecodeProcedure(Content<?> content) throws AlertWindowException {
    chainLinks[chainLinks.length - 1].setContent(content);
  }


  /**
   * Sets the content for the first chain link of the process when encoding. In addition, the method
   * creates all linkes of the process and thus initializes them
   *
   * @param content : Content for the first step of the procedure
   * @throws AlertWindowException if an error happens when calculating the chain
   */
  public void initEncodeProcedure(Content<?> content) throws AlertWindowException {
    chainLinks[0].setContent(content);
  }
}
