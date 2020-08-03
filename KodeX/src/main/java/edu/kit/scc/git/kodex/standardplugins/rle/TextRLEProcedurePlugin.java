package edu.kit.scc.git.kodex.standardplugins.rle;

import edu.kit.scc.git.kodex.plugininterface.ChainLinkPresenter;
import edu.kit.scc.git.kodex.plugininterface.Content;
import edu.kit.scc.git.kodex.plugininterface.ImportPresenter;
import edu.kit.scc.git.kodex.plugininterface.ProcedureInformation;
import edu.kit.scc.git.kodex.plugininterface.ProcedurePlugin;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.LetterStringPresenter;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.TupleStringPresenter;
import edu.kit.scc.git.kodex.standardplugins.rle.model.steps.RLEStep;
import edu.kit.scc.git.kodex.standardplugins.rle.presenter.RLEImportPresenter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is a plugin for the run length encoding procedure.
 * 
 * @author Leonhard Kraft
 * @version 1.0
 */
public class TextRLEProcedurePlugin extends ProcedurePlugin {

  private ChainLinkPresenter[] chainLinks; // [2..*]

  /** Creates a new instance of the TextRLEProcedurePlugin. */
  public TextRLEProcedurePlugin() {

  }
  
  /**
   * Initialize the procedure plugin.
   */
  private void initialize() {
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
    initialize();
    return new RLEImportPresenter(this);
  }

  @Override
  public ProcedureInformation createProcedureInformation() {
    return new TextRLEProcedureInformation();
  }

  @Override
  public ChainLinkPresenter getChainHead() {
    return chainLinks[0];
  }

  @Override
  public ChainLinkPresenter getChainTail() {
    return chainLinks[chainLinks.length - 1];
  }

  @Override
  public void initDecodeProcedure(Content<?> content) {
    chainLinks[chainLinks.length - 1].updateChain();
  }

  @Override
  public void initEncodeProcedure(Content<?> content) {
    chainLinks[0].updateChain();
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
