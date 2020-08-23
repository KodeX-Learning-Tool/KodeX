package kodex.standardplugins.bwimageprocedure.presenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.commons.io.FilenameUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.exceptions.AlertWindowException;
import kodex.model.I18N;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.InvalidImportException;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.BlackWhiteImage;
import kodex.presenter.PresenterManager;

/**
 * This class is responsible for managing the import of the black-and-white image or a binary
 * sequence.
 *
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * @version 1.0
 */
public class BWImageImportPresenter extends ImportPresenter {
  
  /** The import button for encoding. */
  @FXML
  private Button encodeImportButton;
  
  /** The import button for decoding. */
  @FXML
  private Button decodeImportButton;

  /** The image which is imported for encoding. */
  private WritableImage img;

  /** The binary string which is imported for decoding. */
  private String binaryString;
  
  /**
   * The header to the binaryString containing information about what it encodes.
   */
  private HashMap<String, Object> header;

  private static final String WIDTH_KEY = "width";

  private static final String HEIGHT_KEY = "height";
  
  /** The Constant ERROR_PROPERTY_KEY. */
  private static final String ERROR_PROPERTY_KEY = "alert.title.error";
  
  /** The Constant INVALID_IMPORT_PROPERTY_KEY. */
  private static final String INVALID_IMPORT_PROPERTY_KEY = "alert.import.invalid";
  
  /** The Constant INVALID_CONTENT_PROPERTY_KEY. */
  private static final String INVALID_CONTENT_PROPERTY_KEY = "alert.content.invalid";
  

  public BWImageImportPresenter(ProcedurePlugin plugin) {
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
      FXMLLoader loader = new FXMLLoader(getClass().getResource("bwimport.fxml"));
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
    extensions.add("*.txt");
    
    // extensions filter for the FileChooser
    ArrayList<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.all"), "*.*"));
    extensionFilters.add(new ExtensionFilter(I18N.get("files.text"), extensions));
    
    File file = importFile(false, extensionFilters);

    if (file != null) {
      if (!extensions.contains("*." + FilenameUtils.getExtension(file.getName()))) {
        importAlert(file, "text");
        return;
      }
      
      parseTextFile(file);
      
      if (validateDecodeImport()) {
        procedureLayoutPresenter.switchToChainPresenter(false);
      }
    }
  }

  @Override
  public void handleEncodeImport() throws InvalidImportException {
    // supported extensions
    ArrayList<String> extensions = new ArrayList<>();
    extensions.add("*.png");
    extensions.add("*.jpg");
    
    // extensions filter for the FileChooser
    ArrayList<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.all"), "*.*"));
    extensionFilters.add(new ExtensionFilter(I18N.get("files.image"), extensions));
    
    File file = importFile(false, extensionFilters);

    if (file != null) {
      if (!extensions.contains("*." + FilenameUtils.getExtension(file.getName()))) {
        importAlert(file, "image");
        return;
      }
      
      // Creating an image
      Image image = new Image(file.toURI().toString());
      int width = (int) image.getWidth();
      int height = (int) image.getHeight();
      
      if (width <= 0 || height <= 0) {
        throw new InvalidImportException(AlertType.ERROR, I18N.get(ERROR_PROPERTY_KEY),
            I18N.get(INVALID_IMPORT_PROPERTY_KEY),
            "The content has dimensions less or equal to 0.");
      }

      // Creating a writable image
      img = new WritableImage(width, height);

      // Reading color from the loaded image
      PixelReader pixelReader = image.getPixelReader();

      // getting the pixel writer
      PixelWriter writer = img.getPixelWriter();

      // Reading the color of the image
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          // Retrieving the color of the pixel of the loaded image
          Color color = pixelReader.getColor(x, y);

          // Setting the color to the writable image
          writer.setColor(x, y, color);
        }
      }

      if (validateEncodeImport()) {
        procedureLayoutPresenter.switchToChainPresenter(true);
      }
    }
  }
  
  /**
   * Shows an alert window concerning file extensions.
   *
   * @param givenFileExtension the given file
   * @param expectedFileType the expected file type
   * @throws InvalidImportException if the import is invalid
   */
  private void importAlert(File file, String expectedFileType) throws InvalidImportException {
    throw new InvalidImportException(AlertType.ERROR, I18N.get(ERROR_PROPERTY_KEY),
        I18N.get(INVALID_IMPORT_PROPERTY_KEY),
        "The extension ." + FilenameUtils.getExtension(file.getName()) 
        +  " does not belong to a supported " + expectedFileType + " file type.");
  }
  
  @Override
  public boolean validateDecodeImport() {
    BinaryString content = (BinaryString) plugin.getChainTail().getContent();

    try {
      if (content.isValid(binaryString)) {
        content.setString(binaryString);
        content.setHeader(header);
        plugin.initDecodeProcedure(content);
        return true;
      }
    } catch (AlertWindowException e) {
      PresenterManager.showAlertDialog(e.getType(), e.getTitle(), e.getHeader(), e.getContent());
    }

    return false;
  }

  @Override
  public boolean validateEncodeImport() {
    BlackWhiteImage content = (BlackWhiteImage) plugin.getChainHead().getContent();

    try {
      if (content.isValid(img)) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(WIDTH_KEY, img.getWidth());
        map.put(HEIGHT_KEY, img.getHeight());
        content.setHeader(map);
        plugin.initEncodeProcedure(content);
        return true;
      }
    } catch (AlertWindowException e) {
      PresenterManager.showAlertDialog(e.getType(), e.getTitle(), e.getHeader(), e.getContent());
    }

    return false;
  }
  
  private void parseTextFile(File file) throws InvalidImportException {
    try (Scanner in = new Scanner(file)) {
      
      //header
      header = new HashMap<>();
      in.next("HEADER");
      in.next(WIDTH_KEY);
      int width = in.nextInt();
      header.put(WIDTH_KEY, width);
      in.next("unit-length");
      int unitLength = in.nextInt();
      header.put("unit-length", unitLength);
      in.next(HEIGHT_KEY);
      int height = in.nextInt();
      header.put(HEIGHT_KEY, height);
      
      //content
      in.next("CONTENT");
      in.nextLine();
      binaryString = in.nextLine();

    } catch (InputMismatchException e) {
      throw new InvalidImportException(AlertType.ERROR, I18N.get(ERROR_PROPERTY_KEY),
          I18N.get(INVALID_CONTENT_PROPERTY_KEY),
          "The file doesn't have a valid format. Check if the header or content has been damaged.");
    } catch (FileNotFoundException e1) {
      throw new InvalidImportException(AlertType.ERROR, I18N.get(ERROR_PROPERTY_KEY),
          I18N.get(INVALID_CONTENT_PROPERTY_KEY),
          "The content could not be parsed because the program couldn't find the file.");
    }
  }
}
