package kodex.presenter;

import java.util.*;

/**
 * 
 */
public class PresenterFactory {

    /**
     * Default constructor
     */
    public PresenterFactory() {
    }

    /**
     * 
     */
    private PresenterManager presenterManager;

    /**
     * @param pm
     */
    public void PresenterFactory(PresenterManager pm) {
        // TODO implement here
    }

    /**
     * @return
     */
    public SideMenuPresenter createSideMenuPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public IndexPagePresenter createIndexPagePresenter() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public NetworkPresenter createNetworkPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public SettingsPresenter createSettingsPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public PluginMenuPresenter createPluginMenuPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public HelpPresenter createHelpPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * @param pp 
     * @return
     */
    public ProcedureLayoutPresenter createProcedureLayoutPresenter(ProcedurePlugin pp) {
        // TODO implement here
        return null;
    }

}