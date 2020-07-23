package kodex.standardplugins.greyscaleimageprocedure;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.content.AbstractImage;
import kodex.pluginutils.model.content.AbstractMatrix;
import kodex.pluginutils.model.content.AbstractString;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.BlackWhiteImage;
import kodex.pluginutils.model.content.CharacterString;
import kodex.pluginutils.model.content.RGBMatrix;
import kodex.pluginutils.model.steps.ColorImageToRGBMatrix;
import kodex.pluginutils.model.steps.RGBByteListToBinaryString;
import kodex.pluginutils.model.steps.RGBListToRGBByteList;
import kodex.pluginutils.model.steps.RGBMatrixToRGBList;
import kodex.pluginutils.model.steps.TextToBinaryString;
import kodex.pluginutils.presenter.chainlink.BinaryStringChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.ColorImageChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.RGBByteListChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.RGBListChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.RGBMatrixChainLinkPresenter;
import kodex.standardplugins.greyscaleimageprocedure.presenter.GreyScaleImageImportPresenter;

/**
 * This class is responsible for the administration of the specific
 * procedure "greyscale image to binary sequence". 
 * This class holds a list of ChainLinks as attributes, i.e. the
 * different steps of this coding chain.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public class GreyScaleImageProcedurePlugin extends ProcedurePlugin {

    /* steps of this coding chain */
    private ChainLinkPresenter[] chainLinks; // [2..*]

    
    /**
     * Constructor of class BWImageProecedure. Sets all chainLinks
     */
    public GreyScaleImageProcedurePlugin() {
    	chainLinks = new ChainLinkPresenter[5];
    	
    	ColorImageToRGBMatrix colorImageToRGBMatrix = new ColorImageToRGBMatrix();
    	RGBMatrixToRGBList rgbMatrixToRGBList = new RGBMatrixToRGBList();
    	RGBListToRGBByteList rgbListToRGBByteList = new RGBListToRGBByteList();
    	RGBByteListToBinaryString rgbByteListToBinaryString = new RGBByteListToBinaryString();
    	
    	
    	chainLinks[0] = new ColorImageChainLinkPresenter(null, null, colorImageToRGBMatrix);
    	chainLinks[1] = new RGBMatrixChainLinkPresenter(chainLinks[0], colorImageToRGBMatrix, rgbMatrixToRGBList);
    	chainLinks[2] = new RGBListChainLinkPresenter(chainLinks[1], rgbMatrixToRGBList, rgbListToRGBByteList);
    	chainLinks[3] = new RGBByteListChainLinkPresenter(chainLinks[2], rgbListToRGBByteList, rgbByteListToBinaryString);
    	chainLinks[4] = new BinaryStringChainLinkPresenter(chainLinks[3], rgbByteListToBinaryString, null);
    	
    	
    	// set next for chain links
    	for (int i = 0; i < chainLinks.length - 1; i++) {
        	chainLinks[i].setNext(chainLinks[i+1]);
    	}
    }

	@Override
	public ChainLinkPresenter getChainHead() {
		return chainLinks[0];
	}

	@Override
	public void initEncodeProcedure(Content content) {
		chainLinks[0].updateChain();
	}

	@Override
	public void initDecodeProcedure(Content content) {
		chainLinks[chainLinks.length-1].updateChain();
	}

	@Override
	public ProcedureInformation createProcedureInformation() {
		return new GreyScaleProcedureInformation();
	}

	@Override
	public ImportPresenter createImportPresenter() {
		return new GreyScaleImageImportPresenter(this);
	}

    @Override
    public StringProperty pluginNameProperty() {
        return new SimpleStringProperty("Graustufen Bild");
    }

    @Override
    public StringProperty pluginDescriptionProperty() {
        return new SimpleStringProperty("Kodierungsverfahren");
    }
}