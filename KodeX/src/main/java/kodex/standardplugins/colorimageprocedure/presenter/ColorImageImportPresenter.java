package kodex.standardplugins.colorimageprocedure.presenter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.content.ColorImage;

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
	private WritableImage writableImage;
	
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
		return plugin.getChainHead().getContent().isValid(writableImage);
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
		
	    //Creating an image 
	    Image image = new Image(file.getPath()); 
	    int width = (int)image.getWidth(); 
	    int height = (int)image.getHeight(); 
	        
	    //Creating a writable image 
	    WritableImage writableImage = new WritableImage(width, height); 
	         
	    //Reading color from the loaded image 
	    PixelReader pixelReader = image.getPixelReader();
	    
	    //getting the pixel writer 
	    PixelWriter writer = writableImage.getPixelWriter();           
	      
	    //Reading the color of the image 
	    for(int y = 0; y < height; y++) { 
	       for(int x = 0; x < width; x++) { 
	          //Retrieving the color of the pixel of the loaded image   
	          Color color = pixelReader.getColor(x, y); 
	              
	          //Setting the color to the writable image 
	          writer.setColor(x, y, color.darker());              
	       }
	    }	
		
		if (validateEncodeImport()) {
			plugin.initEncodeProcedure(new ColorImage(writableImage));
		}

	}

	@Override
	public void handleDecodeImport() {
		File file = importFile("Dekodieren");
		
		try {
			binaryString = Files.readString(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (validateDecodeImport()) {
			
		}
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