package kodex.standardplugins.colorimageprocedure;


import javafx.beans.property.SimpleStringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ProcedurePlugin;
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
        // TODO implement here
    }

    /**
     * @param content
     */
    public void initDecodeProcedure(Content content) {
        // TODO implement here
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