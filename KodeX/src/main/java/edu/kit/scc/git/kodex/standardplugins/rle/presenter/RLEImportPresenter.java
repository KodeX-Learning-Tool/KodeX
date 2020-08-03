package edu.kit.scc.git.kodex.standardplugins.rle.presenter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import edu.kit.scc.git.kodex.model.I18N;
import edu.kit.scc.git.kodex.plugininterface.ChainLinkPresenter;
import edu.kit.scc.git.kodex.plugininterface.ImportPresenter;
import edu.kit.scc.git.kodex.plugininterface.ProcedurePlugin;
import edu.kit.scc.git.kodex.pluginutils.model.content.LetterString;
import edu.kit.scc.git.kodex.pluginutils.model.content.TupleString;
import edu.kit.scc.git.kodex.presenter.PresenterManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * This class is the presenter for the import of the run length encoding procedure (RLE).
 * 
 * @author Leonhard Kraft
 * @version 1.0
 */
public class RLEImportPresenter extends ImportPresenter {

  private String letterString;
  private String tupleString;

  /**
   * Instantiates a new rle import presenter.
   *
   * @param plugin the procedure plugin reference
   */
  public RLEImportPresenter(ProcedurePlugin plugin) {
    super(plugin);
  }

  @Override
  public AnchorPane getView() {

    AnchorPane importView = new AnchorPane();

    // loads the template
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("importexample.fxml"));
      loader.setController(this);
      importView = loader.load();
    } catch (IOException exc) {
      exc.printStackTrace();
    }

    return importView;
  }

  @Override
  public void handleDecodeImport() {

    File file = importFile("Dekodieren");

    if (file == null) {
      return;
    }

    try {
      tupleString = Files.readString(file.toPath());
    } catch (IOException e) {
      importAlert(file);
      e.printStackTrace();
      return;
    }

    if (validateDecodeImport()) {
      // TODO notify that we want to decode?
      procedureLayoutPresenter.switchToChainPresenter(false);
    } else {
      
      importAlert(file);
      
      System.err.println("File content not valid.");
    }
  }

  @Override
  public void handleEncodeImport() {

    File file = importFile("Kodieren");

    if (file == null) {
      return;
    }

    try {
      letterString = Files.readString(file.toPath());
    } catch (IOException e) {
      importAlert(file);
      e.printStackTrace();
      return;
    }

    if (validateEncodeImport()) {

      procedureLayoutPresenter.switchToChainPresenter(true);
    } else {
      
      importAlert(file);
      
      System.err.println("File content not valid.");
    }
  }
  
  private void importAlert(File file) {
    
    Alert alert = new Alert(AlertType.ERROR);
    alert.titleProperty().bind(I18N.createStringBinding("Import error"));
    alert.headerTextProperty().bind(I18N.createStringBinding("Import is not valid."));
    alert.setContentText("The chosen file ("
        + file.getName() +  ") is not a text file with valid content.");
    PresenterManager.showAlertDialog(alert);
  }

  /**
   * Open a FileChooser to import a file.
   *
   * @param type the type (i.e. Decode/Encode)
   * @return the chosen file
   */
  private File importFile(String type) {
    FileChooser fc = new FileChooser();
    fc.setTitle("Datei zum " + type + " auswählen.");
    return PresenterManager.showOpenFileChooser(fc);
  }

  @Override
  public boolean validateDecodeImport() {
    ChainLinkPresenter clp = plugin.getChainHead();

    while (clp.getNext() != null) {
      clp = clp.getNext();
    }

    TupleString content = (TupleString) clp.getContent();

    if (content.isValid(tupleString)) {
      clp.updateChain();
      return true;
    }
    return false;
  }

  @Override
  public boolean validateEncodeImport() {

    LetterString content = (LetterString) plugin.getChainHead().getContent();

    if (content.isValid(letterString)) {
      plugin.getChainHead().updateChain();
      return true;
    }
    return false;
  }
}
