package kodex.standardplugins.qrcode.presenter;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import kodex.model.I18N;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This class is responsible for managing the import of a QR-Code or Text-file. 
 */
public class QRCodeImportPresenter extends ImportPresenter {

  /** Instantiates a new QRCode import presenter. */
  public QRCodeImportPresenter(ProcedurePlugin plugin) {
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
    // TODO Auto-generated method stub

  }

  @Override
  public void handleEncodeImport() {
    // TODO Auto-generated method stub

  }

  /**
   * Open a FileChooser to import a file.
   *
   * @param isEncoding whether the file is used for encoding or decoding
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
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean validateEncodeImport() {
    // TODO Auto-generated method stub
    return false;
  }
}
