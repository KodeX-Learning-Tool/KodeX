package kodex.standardplugins.colorimageprocedure.presenter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * 
 */
public class ColorImageImportPresenter extends ImportPresenter {

	AnchorPane importview; 

	public ColorImageImportPresenter(ProcedurePlugin plugin) {
		super(plugin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validateEncodeImport() {
		return plugin.getChainHead().getContent().isValid(input);
	}

	@Override
	public boolean validateDecodeImport() {
		ChainLinkPresenter clp = plugin.getChainHead();
		
		while (clp.getNext() != null) {
			clp = clp.getNext();
		}	
		
		return clp.getContent().isValid(input);;
	}

	@Override
	public void handleEncodeImport() {
		File file = importFile("Kodieren");
		
		Image image = new Image(file.getPath());
		
		validateEncodeImport();
	}

	@Override
	public void handleDecodeImport() {
		File file = importFile("Dekodieren");
		
		String binaryString = new Scanner(file).next();
		
		validateDecodeImport();
	}

	@Override
	public AnchorPane getView() {
		
		// loads the template
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("importexample.fxml"));
            loader.setController(this);
            importview = loader.load();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
		
		return importview;
	}
	
	
	private File importFile(String msg) {		
		FileChooser fc = new FileChooser();
		fc.setTitle("Datei zum " + msg + " auswählen.");
		return fc.showOpenDialog(null);
	}


}