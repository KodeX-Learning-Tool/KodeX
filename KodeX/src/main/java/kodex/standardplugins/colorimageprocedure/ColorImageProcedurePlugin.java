package kodex.standardplugins.colorimageprocedure;


import javafx.beans.property.SimpleStringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.steps.ColorImageToRGBMatrix;
import kodex.pluginutils.model.steps.RGBByteListToBinaryString;
import kodex.pluginutils.model.steps.RGBListToRGBByteList;
import kodex.pluginutils.model.steps.RGBMatrixToRGBList;
import kodex.pluginutils.presenter.chainlink.BinaryStringChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.ColorImageChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.RGBByteListChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.RGBListChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.RGBMatrixChainLinkPresenter;
import kodex.standardplugins.colorimageprocedure.presenter.ColorImageImportPresenter;

/**
 * This class is the entry point to the color image procedure plugin.
 * 
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class ColorImageProcedurePlugin extends ProcedurePlugin {
	
    /**
     * 
     */
    private ChainLinkPresenter[] chainLinks; // [2..*]

	
    /**
     * Creates a new instance of the ColorImageProcedurePlugin.
     */
    public ColorImageProcedurePlugin() {
    	this.pluginName = new SimpleStringProperty("Farbbildverfahrensplugin");
    	this.pluginDescription = new SimpleStringProperty("Das Kodierungsverfahren Bild-zu-Bitfolge.");
    	
    	this.chainLinks = new ChainLinkPresenter[5];
    	
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

    /**
     * @return
     */
    public ChainLinkPresenter getChainHead() {
        return chainLinks[0];
    }

    /**
     * @param content
     */
    public void initEncodeProcedure(Content content) {
    	chainLinks[0].setContent(content);
    }

    /**
     * @param content
     */
    public void initDecodeProcedure(Content content) {
        chainLinks[chainLinks.length-1].setContent(content);
    }

    /**
     * @return
     */
    public ColorImageImportPresenter createImportPresenter() {
        return new ColorImageImportPresenter(this);
    }

    /**
     * @return
     */
    public ColorImageProcedureInformation createProcedureInformation() {
        return new ColorImageProcedureInformation();
    }
}