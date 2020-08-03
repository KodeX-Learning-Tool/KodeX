package edu.kit.scc.git.kodex.presenter;

import edu.kit.scc.git.kodex.plugininterface.ProcedurePlugin;

/**
 * This class is a factory to create and return instances of main presenters.
 *
 * @author Leonhard Kraft
 * @author Yannick Neubert
 * @version 1.0
 */
public class PresenterFactory {

  /** The PresenterManager, that manages the current presenter and the root stage. */
  private PresenterManager presenterManager;

  /**
   * Creates a new PresenterFactory with a reference to the PresenterManager.
   *
   * @param presenterManager The reference to the PresenterManger, to hand it over to the created
   *     Presenters.
   */
  public PresenterFactory(PresenterManager presenterManager) {
    this.presenterManager = presenterManager;
  }

  /**
   * This Method creates and returns an Instance of a HelpPresenter.
   *
   * @return A new Instance of a HelpPresenter.
   */
  public HelpPresenter createHelpPresenter() {
    return new HelpPresenter(this.presenterManager);
  }

  /**
   * This Method creates and returns an Instance of a IndexPagePresenter.
   *
   * @return A new Instance of a IndexPagePresenter.
   */
  public IndexPagePresenter createIndexPagePresenter() {
    return new IndexPagePresenter(this.presenterManager, this);
  }

  /**
   * This Method creates and returns an Instance of a NetworkPresenter.
   *
   * @return A new Instance of a NetworkPresenter.
   */
  public NetworkPresenter createNetworkPresenter() {
    return new NetworkPresenter(this.presenterManager);
  }

  /**
   * This Method creates and returns an Instance of a PluginsMenuPresenter.
   *
   * @return A new Instance of a PluginMenuPresenter.
   */
  public PluginMenuPresenter createPluginMenuPresenter() {
    return new PluginMenuPresenter(this.presenterManager);
  }

  /**
   * This Method creates and returns an Instance of a ProcedureLayoutPresenter. To create the
   * instance the ProcedurePlugin that should be displayed is needed.
   *
   * @param procedurePlugin The active procedure, that has been chosen.
   * @return A new Instance of a ProcedureLayoutPresenter.
   */
  public ProcedureLayoutPresenter createProcedureLayoutPresenter(ProcedurePlugin procedurePlugin) {
    return new ProcedureLayoutPresenter(this.presenterManager, procedurePlugin);
  }

  /**
   * This Method creates and returns an Instance of a SettingsPresenter.
   *
   * @return A new Instance of a SettingsPresenter.
   */
  public SettingsPresenter createSettingsPresenter() {
    return new SettingsPresenter(this.presenterManager);
  }

  /**
   * This Method creates and returns an Instance of a SideMenuPresenter.
   *
   * @return A new Instance of a SideMenuPresenter.
   */
  public SideMenuPresenter createSideMenuPresenter() {
    return new SideMenuPresenter(this.presenterManager, this);
  }
}
