package kodex.standardplugins.qrcode.presenter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.content.CharacterString;
import kodex.pluginutils.model.content.QRCode;
import kodex.presenter.PresenterManager;

/**
 * This class imports an image for encoding or a string for decoding. Afterwards it prepares
 * the view for the chain view.
 *
 * @author Yannick Neubert
 * @version 1.0
 */
public class TextQRCodeImportPresenter extends ImportPresenter {

  /** The import button for encoding. */
  @FXML
  private Button encodeImportButton;

  /** The import button for decoding. */
  @FXML
  private Button decodeImportButton;

  /** The image which is imported for decoding. */
  private File qrcode;

  /** The string which is imported for encoding. */
  private String string;
  
  /** 
   * The header to the QRCode containing meta-information like error correction level. 
   * This feature is not yet implemented, it only serves as a guideline.
   */
  private HashMap<String, Object> header;
  
  private static final int MAX_CHAR_ALPHANUMERICAL = 4296;

  /**
   * Instantiates a new color image import presenter.
   *
   * @param plugin the procedure plugin reference
   */
  public TextQRCodeImportPresenter(ProcedurePlugin plugin) {
    super(plugin);
  }

  @FXML
  private void initialize() {
    // language support   
    encodeImportButton.textProperty()
        .bind(I18N.createStringBinding("importexample.encode.importbutton"));
    decodeImportButton.textProperty()
        .bind(I18N.createStringBinding("importexample.decode.importbutton"));
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
    qrcode = importFile();
    if (qrcode != null) {
      if (validateDecodeImport()) {
        procedureLayoutPresenter.switchToChainPresenter(false);
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        
        alert.titleProperty().bind(I18N.createStringBinding("alert.error.title"));
        alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
        alert.setContentText("File content not valid");
        PresenterManager.showAlertDialog(alert);
      }
    }
  }

  @Override
  public void handleEncodeImport() {
    File file = importFile();

    if (file != null) {
      // reads the string from the file
      try {
        string = Files.readString(file.toPath());
      } catch (IOException e) {
        Alert alert = new Alert(AlertType.ERROR);
        
        alert.titleProperty().bind(I18N.createStringBinding("alert.error.title"));
        alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
        alert.setContentText("File not valid");
        PresenterManager.showAlertDialog(alert);
      }
      if (string != null && validateEncodeImport()) {
        procedureLayoutPresenter.switchToChainPresenter(true);
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        
        alert.titleProperty().bind(I18N.createStringBinding("alert.error.title"));
        alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
        alert.setContentText("File content not valid");
        PresenterManager.showAlertDialog(alert);
      }
    }

  }

  /**
   * Open a FileChooser to import a file.
   *
   * @param isEncoding whether the file is used for encoding or decoding
   * @return the chosen file
   */
  private File importFile() {
    FileChooser fc = new FileChooser();
    return fc.showOpenDialog(null);
  }

  @Override
  public boolean validateDecodeImport() {
    ChainLinkPresenter clp = plugin.getChainTail();
    QRCode content = new QRCode();

    if (content.isValid(qrcode)) {
      HashMap<String, Object> map = new HashMap<>();
      content.setHeader(map);
      clp.setContent(content);
      return true;
    }
    return false;
  }

  @Override
  public boolean validateEncodeImport() {
    ChainLinkPresenter clp = plugin.getChainHead();
    CharacterString content = new CharacterString();
    
    if (string.length() <= MAX_CHAR_ALPHANUMERICAL
        && content.isValid(string)) {
      content.setString(string);
      HashMap<String, Object> map = new HashMap<>();
      content.setHeader(map);
      clp.setContent(content);
      return true;
    }
    return false;
  }
}
