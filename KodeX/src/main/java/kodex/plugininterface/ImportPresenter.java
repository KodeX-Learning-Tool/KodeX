package kodex.plugininterface;

import javafx.scene.layout.AnchorPane;
import kodex.presenter.IPresenter;
import kodex.presenter.ProcedureLayoutPresenter;

/**
 * This class is responsible for entering a procedure. It manages the corresponding view with which
 * the method is entered and with which a distinction is made between coding and decoding. It also
 * contains the functionality with which the entry is made (e.g. import file or enter text).
 * Furthermore, the class is responsible for verifying the input, creating the content for the
 * procedure input from it and transferring it to the procedure and thus initializing it. A process
 * importer must expand this class.
 *
 * @author Patrick Spiesberger
 * @version 1.0
 */
public abstract class ImportPresenter implements IPresenter {

  /* Specific plugin the import presenter is created for */
  protected ProcedurePlugin plugin;

  /** The reference to the procedure layout presenter. */
  protected ProcedureLayoutPresenter procedureLayoutPresenter;

  /**
   * Constructor in which the ProcedurePlugin is passed in order to be able to initialize the
   * procedure if input is valid.
   *
   * @param plugin : Plugin that uses this importer
   */
  public ImportPresenter(ProcedurePlugin plugin) {
    this.plugin = plugin;
  }

  /**
   * Returns the view for importing the content.
   *
   * @return View in the form of an AnchorPane in which the content can be imported
   */
  @Override
  public abstract AnchorPane getView();

  /** Called if input for decoding is loaded and handles it. */
  public abstract void handleDecodeImport();

  /** Called if input for encoding is loaded and handles it. */
  public abstract void handleEncodeImport();

  /**
   * Sets the LayoutPresenter for this import in order to initiate a switch to the ChainPresenter if
   * the entry was successful.
   *
   * @param plp : LayoutPresenter to be set
   */
  public void setLayoutPresenter(ProcedureLayoutPresenter plp) {
    procedureLayoutPresenter = plp;
  }

  /**
   * Validates the entry of a method that is to be decoded.
   *
   * @return true, if input valid, otherwise false
   */
  public abstract boolean validateDecodeImport();

  /**
   * Validates the entry of a method that is to be encoded.
   *
   * @return true, if input valid, otherwise false
   */
  public abstract boolean validateEncodeImport();
}
