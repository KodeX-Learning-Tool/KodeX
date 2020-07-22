package kodex.standardplugins.bwimageprocedure.presenter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.BlackWhiteImage;

/**
 * This class is responsible for managing the import of the black-and-white
 * image or a binary sequence.
 * 
 * @author Patrick Spiesberger
 * @version 1.0
 *
 */
public class BWImageImportPresenter extends ImportPresenter {

	private WritableImage img;
	private String binaryChain;

	public BWImageImportPresenter(ProcedurePlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean validateEncodeImport() {
		BlackWhiteImage content = (BlackWhiteImage) plugin.getChainHead().getContent();

		if (content.isValid(img)) {
			plugin.getChainHead().updateChain();
			return true;
		}
		return false;
	}

	@Override
	public boolean validateDecodeImport() {
		BinaryString content = (BinaryString) plugin.getChainHead().getContent();

		if (content.isValid(binaryChain)) {
			plugin.getChainHead().updateChain();
			return true;
		}
		return false;
	}

	@Override
	public void handleEncodeImport() {
		File file = importFile("Kodieren");

		if (file == null) {
			return;
		}
		img = convertToFxImage(new Image(file.toPath().toUri().toString()));

		if (validateEncodeImport()) {
			procedureLayoutPresenter.switchToChainPresenter();
		} else {
			System.err.println("File content not valid.");
		}
	}

	@Override
	public void handleDecodeImport() {
		File file = importFile("Dekodieren");

		if (file == null) {
			return;
		}

		try {
			binaryChain = Files.readString(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		if (validateDecodeImport()) {
			procedureLayoutPresenter.switchToChainPresenter();
		} else {
			System.err.println("File content not valid.");
		}

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

	/**
	 * Converts an image to a writable image
	 */
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

	/**
	 * Open a FileChooser to import a file.
	 *
	 * @param type the type (i.e. Decode/Encode)
	 * @return the chosen file
	 */
	private File importFile(String type) {
		FileChooser fc = new FileChooser();
		System.out.println("Test");
		fc.setTitle("Datei zum " + type + " auswählen.");
		return fc.showOpenDialog(null);
	}
}