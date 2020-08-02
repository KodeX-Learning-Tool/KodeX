package kodex.standardplugins.greyscaleimageprocedure.presenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import kodex.model.I18N;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.ColorImage;
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
    File file = importFile(false);

    if (file == null) {
      return;
    }

    parseTextFile(file);
    if (validateDecodeImport()) {
      procedureLayoutPresenter.switchToChainPresenter(false);
    } else {
      System.err.println("File content not valid.");
    }
  }

  @Override
  public void handleEncodeImport() {
    File file = importFile(true);

    if (file == null) {
      return;
    }
    img = convertToFxImage(new Image(file.toPath().toUri().toString()));

    if (validateEncodeImport()) {
      procedureLayoutPresenter.switchToChainPresenter(true);
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.error.title"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("Invalid file (" + file.getName() +  "). Please try annother!");
      PresenterManager.showAlertDialog(alert);
    }
  }

  /**
   * Open a FileChooser to import a file.
   *
   * @param type the type (i.e. Decode/Encode)
   * @return the chosen file
   */
  private File importFile(Boolean encoding) {
    FileChooser fc = new FileChooser();
    String propertyName;

    if (Boolean.TRUE.equals(encoding)) {
      propertyName = "importexample.filechooser.encode.title";
    } else {
      propertyName = "importexample.filechooser.decode.title";
    }

    fc.titleProperty().bind(I18N.createStringBinding(propertyName));

    return fc.showOpenDialog(null);
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
    ColorImage content = (ColorImage) plugin.getChainHead().getContent();

    if (content.isValid(img)) {
      plugin.getChainHead().updateChain();
      return true;
    }
    return false;
  }

  /** Converts an image to a writable image. */
  private static WritableImage convertToFxImage(Image image) {
    WritableImage wr = null;
    if (image != null) {
      wr = new WritableImage((int) image.getWidth(), (int) image.getHeight());
      PixelWriter pw = wr.getPixelWriter();
      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          pw.setArgb(x, y, image.getPixelReader().getArgb(x, y));
        }
      }
    }
    return wr;
  }

  private void parseTextFile(File file) {
    try (Scanner in = new Scanner(file)) {

      // header
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

      // content
      in.next("CONTENT");
      in.nextLine();
      binaryString = in.nextLine();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
