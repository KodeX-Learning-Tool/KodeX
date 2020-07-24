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
import kodex.pluginutils.model.content.BinaryString;
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
		ColorImage content = (ColorImage) plugin.getChainHead().getContent();
        
        if (content.isValid(writableImage)) {
            plugin.getChainHead().updateChain();
            return true;
        }
        return false;
	}

	@Override
	public boolean validateDecodeImport() {
		ChainLinkPresenter clp = plugin.getChainHead();
		
		while (clp.getNext() != null) {
			clp = clp.getNext();
		}
		
        BinaryString content = (BinaryString) clp.getContent();
        
        if (content.isValid(binaryString)) {
            clp.updateChain();
            return true;
        }
        return false;
	}

	@Override
	public void handleEncodeImport() {
		File file = importFile("Kodieren");
		
		if (file != null) {
		    //Creating an image 			
		    Image image = new Image(file.toURI().toString()); 
		    int width = (int)image.getWidth(); 
		    int height = (int)image.getHeight(); 
		        
		    //Creating a writable image 
		   writableImage = new WritableImage(width, height); 
		         
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
		          writer.setColor(x, y, color);              
		       }
		    }	
			
			if (validateEncodeImport()) {				
				procedureLayoutPresenter.switchToChainPresenter();
			} else {
	            System.err.println("File content not valid.");
	        }
		}

	}

	@Override
	public void handleDecodeImport() {
		File file = importFile("Dekodieren");
		
		if (file != null) {
			// reads the string from the file
			try {
				binaryString = Files.readString(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (validateDecodeImport()) {
				procedureLayoutPresenter.switchToChainPresenter();
			} else {
	            System.err.println("File content not valid.");
	        }
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