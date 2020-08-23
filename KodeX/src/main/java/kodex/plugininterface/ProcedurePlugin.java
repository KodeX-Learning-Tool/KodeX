package kodex.plugininterface;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import kodex.presenter.PresenterManager;

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
   * Creates and returns a new ImportPresenter instance.
   * @param pm the reference to the presenter manager
   *
   * @return created instance of ImportPresenter
   */
  public abstract ImportPresenter createImportPresenter(PresenterManager pm);

  /**
   * Creates and returns a new ProcedureInformation instance.
   *
   * @return created instance of ProcedureInformation
   */
  public abstract ProcedureInformation createProcedureInformation();

  /**
   * Returns the start link of the procedure, whereby the stages are saved internally as a
   * double-linked list and are linked accordingly.
   *
   * @return presenter for first link of procedure
   */
  public abstract ChainLinkPresenter getChainHead();
  

  /**
   * Returns the last link of the procedure, whereby the stages are saved internally as a
   * double-linked list and are linked accordingly.
   *
   * @return presenter for last link of procedure
   */
  public abstract ChainLinkPresenter getChainTail();

  /**
   * Sets the content for the first link of the process when decoding. In addition, the method
   * creates all linkes of the process and thus initializes them
   *
   * @param content : Content for the first step of the procedure
   */
  public abstract void initDecodeProcedure(Content<?> content);

  /**
   * Sets the content for the first link of the process when encoding. In addition, the method
   * creates all linkes of the process and thus initializes them
   *
   * @param content : Content for the first step of the procedure
   */
  public abstract void initEncodeProcedure(Content<?> content);
}
