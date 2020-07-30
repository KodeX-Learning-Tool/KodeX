package kodex.standardplugins.qrcode.presenter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.content.CharacterString;

/**
 * This class imports an image for encoding or a binary string for decoding. Afterwards it prepares
 * the view for the chain view.
 *
 * @author Raimon Gramlich
 * @version 1.0
 */
public class QRCodeImportPresenter extends ImportPresenter {

  /** The import button for encoding. */
  @FXML
  private Button encodeImportButton;

  /** The import button for decoding. */
  @FXML
  private Button decodeImportButton;

  /** The image which is imported for decoding. */
  private WritableImage qrcode;

  /** The string which is imported for encoding. */
  private String string;

  /**
   * Instantiates a new color image import presenter.
   *
   * @param plugin the procedure plugin reference
   */
  public QRCodeImportPresenter(ProcedurePlugin plugin) {
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
    File file = importFile();

    if (file != null) {
      // Creating an image
      Image image = new Image(file.toURI().toString());
      int width = (int) image.getWidth();
      int height = (int) image.getHeight();

      if (width == 0 || height == 0) {
        //TODO error handling
        System.out.println("mistakes were made");
        return;
      }
      
      // Creating a writable image
      qrcode = new WritableImage(width, height);

      // Reading color from the loaded image
      PixelReader pixelReader = qrcode.getPixelReader();

      // getting the pixel writer
      PixelWriter writer = qrcode.getPixelWriter();

      // Reading the color of the image
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          // Retrieving the color of the pixel of the loaded image
          Color color = pixelReader.getColor(x, y);

          // Setting the color to the writable image
          writer.setColor(x, y, color);
        }
      }
      
      if (validateDecodeImport()) {
        procedureLayoutPresenter.switchToChainPresenter(false);
      } else {
        System.err.println("File content not valid.");
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
        e.printStackTrace();
      }
      if (validateEncodeImport()) {
        procedureLayoutPresenter.switchToChainPresenter(true);
      } else {
        System.err.println("File content not valid.");
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
    //    ChainLinkPresenter clp = plugin.getChainHead();
    //
    //    while (clp.getNext() != null) {
    //      clp = clp.getNext();
    //    }
    //
    //    BinaryString content = (BinaryString) clp.getContent();
    //
    //    if (content.isValid(binaryString)) {
    //      clp.updateChain();
    //      return true;
    //    }
    return false;
  }

  @Override
  public boolean validateEncodeImport() {
    ChainLinkPresenter clp = plugin.getChainHead();
    CharacterString content = new CharacterString();
    
    if (content.isValid(string)) {
      content.setString(string);
      clp.setContent(content);
      return true;
    }
    return false;
  }
}
