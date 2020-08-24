package kodex.standardplugins.rle.presenter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import org.apache.commons.io.FilenameUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.exceptions.AlertWindowException;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.content.LetterString;
import kodex.pluginutils.model.content.TupleString;
import kodex.presenter.PresenterManager;

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
    // supported extensions
    ArrayList<String> extensions = new ArrayList<>();
    extensions.add("*.txt");
    
    // extensions filter for the FileChooser
    ArrayList<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.all"), "*.*"));
    extensionFilters.add(new ExtensionFilter(I18N.get("files.text"), extensions));   
    
    File file = importFile(false, extensionFilters);

    if (file == null) {
      return;
    }
    
    if (!extensions.contains("*." + FilenameUtils.getExtension(file.getName()))) {
      importExtensionAlert(file, "text");
      return;
    }

    try {
      tupleString = Files.readString(file.toPath());
    } catch (IOException e) {
      importAlert(file);
      e.printStackTrace();
      return;
    }
    
    if (tupleString.startsWith("HEADER\nCONTENT\n")) {
      tupleString = tupleString.substring(15);
    }
    
    if (validateDecodeImport()) {
      procedureLayoutPresenter.switchToChainPresenter(false);
    } 
  }

  @Override
  public void handleEncodeImport() {
    // supported extensions
    ArrayList<String> extensions = new ArrayList<>();
    extensions.add("*.txt");
    
    // extensions filter for the FileChooser
    ArrayList<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.all"), "*.*"));
    extensionFilters.add(new ExtensionFilter(I18N.get("files.text"), extensions));    
    
    File file = importFile(true, extensionFilters);

    if (file == null) {
      return;
    }
    
    // check file type
    if (!extensions.contains("*." + FilenameUtils.getExtension(file.getName()))) {
      importExtensionAlert(file, "text");
      return;
    }
    
    try {
      letterString = Files.readString(file.toPath());
    } catch (IOException e) {
      importAlert(file);
      e.printStackTrace();
      return;
    }
    
    if (letterString.startsWith("HEADER\nCONTENT\n")) {
      letterString = letterString.substring(15);
    }
    
    if (validateEncodeImport()) {
      procedureLayoutPresenter.switchToChainPresenter(true);
    } 
  }
  
  /**
   * Shows an alert window concerning file extensions.
   *
   * @param givenFileExtension the given file
   * @param expectedFileType the expected file type
   */
  private void importExtensionAlert(File file, String expectedFileType) {
    PresenterManager.showAlertDialog(AlertType.ERROR, I18N.get("alert.title.error"),
        I18N.get("alert.import.invalid"),
        "The extension ." + FilenameUtils.getExtension(file.getName()) 
        +  " does not belong to a supported " + expectedFileType + " file type.");
  }
  
  private void importAlert(File file) {    
    PresenterManager.showAlertDialog(AlertType.ERROR, I18N.get("alert.title.error"),
        I18N.get("alert.import.invalid"), "The chosen file (" + file.getName() 
        + ") is not a text file with valid content.");
  }

  @Override
  public boolean validateDecodeImport() {
    ChainLinkPresenter clp = plugin.getChainHead();

    while (clp.getNext() != null) {
      clp = clp.getNext();
    }

    TupleString content = (TupleString) clp.getContent();

    try {
      if (content.isValid(tupleString)) {
        clp.updateChain();
        return true;
      }
    } catch (AlertWindowException e) {
      PresenterManager.showAlertDialog(e.getType(), e.getTitle(), e.getHeader(), e.getContent());
    }

    return false;
  }

  @Override
  public boolean validateEncodeImport() {

    LetterString content = (LetterString) plugin.getChainHead().getContent();

    try {
      if (content.isValid(letterString)) {
        plugin.getChainHead().updateChain();
        return true;
      }
    } catch (AlertWindowException e) {
      PresenterManager.showAlertDialog(e.getType(), e.getTitle(), e.getHeader(), e.getContent());
    }

    return false;
  }
}
