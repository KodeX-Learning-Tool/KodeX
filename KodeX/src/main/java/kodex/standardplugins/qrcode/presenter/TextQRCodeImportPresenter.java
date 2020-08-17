package kodex.standardplugins.qrcode.presenter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.FilenameUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.InvalidImportException;
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
  
  /** the maximum amount of alpha numeric characters a QR-code can contain. (177*177) */
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
  public void handleDecodeImport() throws InvalidImportException {
    // supported extensions
    ArrayList<String> extensions = new ArrayList<>();
    extensions.add("*.png");
    extensions.add("*.jpg");

    // extensions filter for the FileChooser
    ArrayList<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.all"), "*.*"));
    extensionFilters.add(new ExtensionFilter(I18N.get("files.image"), extensions));
    
    qrcode = importFile(false, extensionFilters);
    if (qrcode != null) {
      if (!extensions.contains("*." + FilenameUtils.getExtension(qrcode.getName()))) {
        importAlert(qrcode, "image");
        return;
      }
      
      if (validateDecodeImport()) {
        procedureLayoutPresenter.switchToChainPresenter(false);
      } else {       
        throw new InvalidImportException(AlertType.ERROR, I18N.get("alert.title.error"),
            I18N.get("alert.import.invalid"), "File content not valid");
      }
    }
  }

  @Override
  public void handleEncodeImport() throws InvalidImportException {
    // supported extensions
    ArrayList<String> extensions = new ArrayList<>();
    extensions.add("*.txt");
    
    // extensions filter for the FileChooser
    ArrayList<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.all"), "*.*"));
    extensionFilters.add(new ExtensionFilter(I18N.get("files.text"), extensions));
    
    File file = importFile(true, extensionFilters);

    if (file != null) {
      
      if (!extensions.contains("*." + FilenameUtils.getExtension(file.getName()))) {
        importAlert(file, "text");
        return;
      }
      
      // reads the string from the file
      try {
        string = Files.readString(file.toPath());
      } catch (IOException e) {
        throw new InvalidImportException(AlertType.ERROR, I18N.get("alert.title.error"),
            I18N.get("alert.import.invalid"), "File not valid");
      }
      if (string != null && validateEncodeImport()) {
        procedureLayoutPresenter.switchToChainPresenter(true);
      } else {
        throw new InvalidImportException(AlertType.ERROR, I18N.get("alert.title.error"),
            I18N.get("alert.import.invalid"), "File content not valid");
      }
    }

  }

  /**
   * Open a FileChooser to import a file.
   *
   * @param isEncoding whether the file is used for encoding or decoding
   * @return the chosen file
   */
  private File importFile(boolean encoding, ArrayList<ExtensionFilter> extensionFilters) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(extensionFilters);
    String propertyName;
    
    if (encoding) {
      propertyName = "importexample.filechooser.encode.title";
    } else {
      propertyName = "importexample.filechooser.decode.title";
    }
    
    fileChooser.titleProperty().bind(I18N.createStringBinding(propertyName));
    
    return PresenterManager.showOpenFileChooser(fileChooser);
  }
  
  /**
   * Shows an alert window concerning file extensions.
   *
   * @param givenFileExtension the given file
   * @param expectedFileType the expected file type
   * @throws InvalidImportException if the import is invalid
   */
  private void importAlert(File file, String expectedFileType) throws InvalidImportException {
    throw new InvalidImportException(AlertType.ERROR, I18N.get("alert.title.error"),
        I18N.get("alert.import.invalid"),
        "The extension ." + FilenameUtils.getExtension(file.getName()) 
        +  " does not belong to a supported " + expectedFileType + " file type.");
  }

  @Override
  public boolean validateDecodeImport() {
    ChainLinkPresenter clp = plugin.getChainTail();
    QRCode content = new QRCode();

    if (content.isValid(qrcode)) {
      header = new HashMap<>();
      content.setHeader(header);
      clp.setContent(content);
      return true;
    }
    return false;
  }

  @Override
  public boolean validateEncodeImport() {
    ChainLinkPresenter clp = plugin.getChainHead();
    CharacterString content = new CharacterString();
    
    if (!string.isEmpty() && string.length() <= MAX_CHAR_ALPHANUMERICAL
        && content.isValid(string)) {
      content.setString(string);
      header = new HashMap<>();
      content.setHeader(header);
      clp.setContent(content);
      return true;
    }
    return false;
  }
}
