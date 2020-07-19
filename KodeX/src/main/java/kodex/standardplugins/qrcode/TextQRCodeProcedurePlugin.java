package kodex.standardplugins.qrcode;


import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;

/**
 * 
 */
public class TextQRCodeProcedurePlugin extends ProcedurePlugin {

    /**
     * Default constructor
     */
    public TextQRCodeProcedurePlugin() {
    }

    /**
     * 
     */
    private ChainLinkPresenter[] chainLinks; // [2..*]

    @Override
    public StringProperty pluginNameProperty() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StringProperty pluginDescriptionProperty() {
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
        return new TextQRCodeProcedureInformation();
    }

    @Override
    public ImportPresenter createImportPresenter() {
        // TODO Auto-generated method stub
        return null;
    }

}