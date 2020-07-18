package kodex.standardplugins.colorimageprocedure;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ProcedurePlugin;
import kodex.standardplugins.colorimageprocedure.presenter.ColorImageImportPresenter;

/**
 * 
 */
public class ColorImageProcedurePlugin extends ProcedurePlugin {	
	
	private final StringProperty pluginName;
	
	private final StringProperty pluginDescription;

    /**
     * Default constructor
     */
    public ColorImageProcedurePlugin() {
    	this.pluginName = new SimpleStringProperty("Farbbildverfahrensplugin");

    	this.pluginDescription = new SimpleStringProperty("Das Kodierungsverfahren Bild-zu-Bitfolge.");
    }

    /**
     * 
     */
    private ChainLinkPresenter[] chainLinks; // [2..*]


    /**
     * @return
     */
    public ChainLinkPresenter getChainHead() {
        // TODO implement here
        return null;
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

	@Override
	public StringProperty getPluginName() {
		return pluginName;
	}

	@Override
	public StringProperty getPluginDescription() {
		return pluginDescription;
	}
}