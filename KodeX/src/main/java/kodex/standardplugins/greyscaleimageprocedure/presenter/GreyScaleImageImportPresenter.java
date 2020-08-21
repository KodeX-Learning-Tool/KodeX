package kodex.standardplugins.greyscaleimageprocedure.presenter;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.I18N;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.GreyScaleImage;
import kodex.presenter.PresenterManager;

/**
 * This class is responsible for managing the import of the greyscale image or a
 * binary sequence.
 *
 * @author Patrick Spiesberger
 * @version 1.0
 */
public class GreyScaleImageImportPresenter extends ImportPresenter {

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

  /** The Constant MAXIMUM_OPACITY_VALUE as defined in the JavaFX Color class. */
  private static final double MAXIMUM_OPACITY_VALUE = 1;

  /** The Constant MINIMUM_OPACITY_VALUE as defined in the JavaFX Color class. */
  private static final double MINIMUM_OPACITY_VALUE = 0;


  public GreyScaleImageImportPresenter(ProcedurePlugin plugin) {
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
      FXMLLoader loader = new FXMLLoader(getClass().getResource("greyscaleimport.fxml"));
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

    if (file != null) {
      if (!extensions.contains("*." + FilenameUtils.getExtension(file.getName()))) {
        importAlert(file, "text");
        return;
      }

      if (!parseTextFile(file)) {
        return;
      }
      if (validateDecodeImport()) {
        procedureLayoutPresenter.switchToChainPresenter(false);
      }
    }
  }

  @Override
  public void handleEncodeImport() {
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
        Alert alert = new Alert(AlertType.ERROR);
        alert.titleProperty().bind(I18N.createStringBinding(ERROR_PROPERTY_KEY));
        alert.headerTextProperty().bind(I18N.createStringBinding(INVALID_IMPORT_PROPERTY_KEY));
        alert.setContentText("The content has dimensions less or equal to 0.");
        PresenterManager.showAlertDialog(alert);
        return;
      }

      // Creating a writable image
      img = new WritableImage(width, height);

      // Reading color from the loaded image
      PixelReader pixelReader = image.getPixelReader();

      // getting the pixel writer
      PixelWriter writer = img.getPixelWriter();

      boolean containedAlpha = false;

      // Reading the color of the image
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          // Retrieving the color of the pixel of the loaded image
          Color color = pixelReader.getColor(x, y);

          // remove alpha (opacity values) because this procedure uses only RGB values
          if (color.getOpacity() == MINIMUM_OPACITY_VALUE) {
            color = Color.WHITE;
            containedAlpha = true;
          } else if (color.getOpacity() != MAXIMUM_OPACITY_VALUE) {
            // adjust the RGB values since the background is white
            color = new Color(
                color.getRed() * color.getOpacity() + MAXIMUM_OPACITY_VALUE - color.getOpacity(),
                color.getGreen() * color.getOpacity() + MAXIMUM_OPACITY_VALUE - color.getOpacity(),
                color.getBlue() * color.getOpacity() + MAXIMUM_OPACITY_VALUE - color.getOpacity(),
                MAXIMUM_OPACITY_VALUE);
            containedAlpha = true;
          }
          
          // Setting the color to the writable image
          writer.setColor(x, y, color);
        }
      }

      if (validateEncodeImport()) {
        if (containedAlpha) {
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.titleProperty().bind(I18N.createStringBinding("alert.title.information"));
          alert.headerTextProperty().bind(I18N.createStringBinding("alert.import.image"));
          alert.setContentText("The imported Image contained alpha values. Colors of pixel with "
              + "alpha values were converted since this procedure only uses RGB values.");
          PresenterManager.showAlertDialog(alert);
        }

        procedureLayoutPresenter.switchToChainPresenter(true);
      }
    }
  }
  
  /**
   * Shows an alert window concerning file extensions.
   *
   * @param givenFileExtension the given file
   * @param expectedFileType the expected file type
   */
  private void importAlert(File file, String expectedFileType) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.titleProperty().bind(I18N.createStringBinding(ERROR_PROPERTY_KEY));
    alert.headerTextProperty().bind(I18N.createStringBinding(INVALID_IMPORT_PROPERTY_KEY));
    alert.setContentText("The extension ." + FilenameUtils.getExtension(file.getName()) 
        +  " does not belong to a supported " + expectedFileType + " file type.");
    PresenterManager.showAlertDialog(alert);
  }
  
  @Override
  public boolean validateDecodeImport() {
    BinaryString content = (BinaryString) plugin.getChainTail().getContent();

    if (content.isValid(binaryString)) {
      content.setString(binaryString);
      content.setHeader(header);
      plugin.getChainTail().setContent(content);
      return true;
    }
    return false;
  }

  @Override
  public boolean validateEncodeImport() {
    GreyScaleImage content = (GreyScaleImage) plugin.getChainHead().getContent();

    if (content.isValid(img)) {
      HashMap<String, Object> map = new HashMap<>();
      map.put(WIDTH_KEY, img.getWidth());
      map.put(HEIGHT_KEY, img.getHeight());
      
      content.setHeader(map);
      plugin.getChainHead().updateChain();
      return true;
    }
    return false;
  }
  
  private boolean parseTextFile(File file) {
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
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding(ERROR_PROPERTY_KEY));
      alert.headerTextProperty().bind(I18N.createStringBinding(INVALID_CONTENT_PROPERTY_KEY));
      alert.setContentText(
          "The file doesn't have a valid format. Check if the header or content has been damaged.");
      PresenterManager.showAlertDialog(alert);
      
      return false;
    } catch (FileNotFoundException e1) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding(ERROR_PROPERTY_KEY));
      alert.headerTextProperty().bind(I18N.createStringBinding(INVALID_IMPORT_PROPERTY_KEY));
      alert.setContentText(
          "The content could not be parsed because the program couldn't find the file.");
      PresenterManager.showAlertDialog(alert);
      
      return false;
    } 
    
    return true;
  }
}
