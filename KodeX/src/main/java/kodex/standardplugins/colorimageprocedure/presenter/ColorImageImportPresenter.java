package kodex.standardplugins.colorimageprocedure.presenter;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This class imports an image for encoding or a binary string for decoding.
 * Afterwards it prepares the view for the chain view.
 * 
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class ColorImageImportPresenter extends ImportPresenter {
	
	/** The image which is imported for encoding. */
	private Image image;
	
	/** The binary string which is imported for decoding. */
	private String binaryString;
	
	
	/**
	 * Instantiates a new color image import presenter.
	 *
	 * @param plugin the procedure plugin reference
	 */
	public ColorImageImportPresenter(ProcedurePlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean validateEncodeImport() {
		return plugin.getChainHead().getContent().isValid(image);
	}

	@Override
	public boolean validateDecodeImport() {
		ChainLinkPresenter clp = plugin.getChainHead();
		
		while (clp.getNext() != null) {
			clp = clp.getNext();
		}	
		
		return clp.getContent().isValid(binaryString);
	}

	@Override
	public void handleEncodeImport() {
		File file = importFile("Kodieren");
		
		image = new Image(file.getPath());
		
		validateEncodeImport();
	}

	@Override
	public void handleDecodeImport() {
		File file = importFile("Dekodieren");
		
		validateDecodeImport();
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
	
	
	/**
	 * Open a FileChooser to import a file.
	 *
	 * @param type the type (i.e. Decode/Encode)
	 * @return the chosen file
	 */
	private File importFile(String type) {		
		FileChooser fc = new FileChooser();
		fc.setTitle("Datei zum " + type + " auswählen.");
		return fc.showOpenDialog(null);
	}
}