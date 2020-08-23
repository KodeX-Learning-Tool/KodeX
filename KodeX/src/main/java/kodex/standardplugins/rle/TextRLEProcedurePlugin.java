package kodex.standardplugins.rle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.presenter.chainlink.LetterStringPresenter;
import kodex.pluginutils.presenter.chainlink.TupleStringPresenter;
import kodex.presenter.PresenterManager;
import kodex.standardplugins.rle.model.steps.RLEStep;
import kodex.standardplugins.rle.presenter.RLEImportPresenter;

/**
 * This class is a plugin for the run length encoding procedure.
 * 
 * @author Leonhard Kraft
 * @version 1.0
 */
public class TextRLEProcedurePlugin extends ProcedurePlugin {

  /** Creates a new instance of the TextRLEProcedurePlugin. */
  public TextRLEProcedurePlugin() {

  }
  
  /**
   * Initialize the procedure plugin.
   */
  public void initializeProcedure() {
    this.chainLinks = new ChainLinkPresenter[2];

    RLEStep rleStep = new RLEStep();

    chainLinks[0] = new LetterStringPresenter(null, null, rleStep);
    chainLinks[1] = new TupleStringPresenter(chainLinks[0], rleStep, null);

    // set next for chain links
    for (int i = 0; i < chainLinks.length - 1; i++) {
      chainLinks[i].setNext(chainLinks[i + 1]);
    }
  }

  @Override
  public ImportPresenter createImportPresenter() {
    return new RLEImportPresenter(this);
  }

  @Override
  public ProcedureInformation createProcedureInformation() {
    return new TextRLEProcedureInformation();
  }

  @Override
  public StringProperty pluginDescriptionProperty() {
    return new SimpleStringProperty("Komprimierungsverfahren");
  }

  @Override
  public StringProperty pluginNameProperty() {
    return new SimpleStringProperty("Lauflängencodierung");
  }
}
