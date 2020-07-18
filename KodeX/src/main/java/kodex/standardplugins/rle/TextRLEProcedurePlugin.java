package kodex.standardplugins.rle;


import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;

/**
 * 
 */
public class TextRLEProcedurePlugin extends ProcedurePlugin {

    /**
     * Default constructor
     */
    public TextRLEProcedurePlugin() {
    }

    /**
     * 
     */
    private ChainLinkPresenter[] chainLinks; //[2..*]

	@Override
	public StringProperty getPluginName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringProperty getPluginDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChainLinkPresenter getChainHead() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initEncodeProcedure(Content content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initDecodeProcedure(Content content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProcedureInformation createProcedureInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImportPresenter createImportPresenter() {
		// TODO Auto-generated method stub
		return null;
	}
    



}