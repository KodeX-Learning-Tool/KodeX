package kodex.presenter;

import kodex.plugininterface.ProcedurePlugin;

/**
 * Diese Klasse erstellt und gibt Instanzen von Haupt-Presentern zurück.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class PresenterFactory {

    /**
     * Der PresenterManager, der die Haupt-Presenter verwaltet
     */
    private PresenterManager presenterManager;

    
    
    /**
     * Erstellt eine neue PresenterFactory mit einer Referenz zu dem PresenterManager.
     * @param pm : Die Referenz zum PresenterManger, um sie den erstellten Presentern übergeben zu können.
     */
    public void PresenterFactory(PresenterManager pm) {
        // TODO implement here
    }

    /**
     * Die Methode erstellt und gibt eine Instanz vom SideMenuPresenter zurück.
     * @return Eine neue Instanz vom SideMenuPresenter.
     */
    public SideMenuPresenter createSideMenuPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * Die Methode erstellt und gibt eine Instanz vom IndexPagePresenter zurück.
     * @return Eine neue Instanz vom IndexPagePresenter.
     */
    public IndexPagePresenter createIndexPagePresenter() {
        // TODO implement here
        return null;
    }

    /**
     * Die Methode erstellt und gibt eine Instanz vom NetworkPresenter zurück.
     * @return Eine neue Instanz vom NetworkPresenter.
     */
    public NetworkPresenter createNetworkPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * Die Methode erstellt und gibt eine Instanz vom SettingsPresenter zurück.
     * @return Eine neue Instanz vom SettingsPresenter.
     */
    public SettingsPresenter createSettingsPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * Die Methode erstellt und gibt eine Instanz vom PluginsMenuPresenter zurück.
     * @return Eine neue Instanz vom PluginMenuPresenter.
     */
    public PluginMenuPresenter createPluginMenuPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * Die Methode erstellt und gibt eine Instanz vom HelpPresenter zurück.
     * @return Eine neue Instanz vom HelpPresenter.
     */
    public HelpPresenter createHelpPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * Die Methode erstellt und gibt eine Instanz vom ProcedureLayoutPresenter zurück.
     * Dieser Instanz muss auch das anzuzeigende ProcedurePlugin übergeben werden.
     * @param pp : Das aktive Kodierungsverfahren, welches ausgewählt wurde.
     * @return Eine neue Instanz vom ProcedureLayoutPresenter.
     */
    public ProcedureLayoutPresenter createProcedureLayoutPresenter(ProcedurePlugin pp) {
        // TODO implement here
        return null;
    }

}