package edu.kit.scc.git.kodex.standardplugins.bwimageprocedure;

import edu.kit.scc.git.kodex.plugininterface.ChainLinkPresenter;
import edu.kit.scc.git.kodex.plugininterface.Content;
import edu.kit.scc.git.kodex.plugininterface.ImportPresenter;
import edu.kit.scc.git.kodex.plugininterface.ProcedureInformation;
import edu.kit.scc.git.kodex.plugininterface.ProcedurePlugin;
import edu.kit.scc.git.kodex.pluginutils.model.steps.BWImageToMatrix;
import edu.kit.scc.git.kodex.pluginutils.model.steps.BitListToBinaryString;
import edu.kit.scc.git.kodex.pluginutils.model.steps.MatrixToBitList;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.BWMatrixChainLinkPresenter;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.BinaryStringPresenter;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.BitListChainLinkPresenter;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.ColorImageChainLinkPresenter;
import edu.kit.scc.git.kodex.standardplugins.bwimageprocedure.presenter.BWImageImportPresenter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is responsible for the administration of the specific procedure
 * "black and white image to binary sequence". This class holds a list of
 * ChainLinks as attributes, i.e. the different steps of this coding chain.
 *
 * @author Patrick Spiesberger
 * @version 1.0
 */
public class BWImageProcedurePlugin extends ProcedurePlugin {

  /* steps of this coding chain */
  private ChainLinkPresenter[] chainLinks; // [2..*]

  /** Constructor of class BWImageProecedure. Sets all chainLinks */
  public BWImageProcedurePlugin() {

  }
  
  /**
   * Initialize the procedure plugin.
   */
  private void initialize() {
    chainLinks = new ChainLinkPresenter[4];
    BWImageToMatrix bwImageToMatrix = new BWImageToMatrix();
    MatrixToBitList matrixToBitList = new MatrixToBitList();
    BitListToBinaryString bitListToBinaryString = new BitListToBinaryString();

    chainLinks[0] =
        new ColorImageChainLinkPresenter(null, null, bwImageToMatrix);
    chainLinks[1] = 
        new BWMatrixChainLinkPresenter(chainLinks[0], bwImageToMatrix, matrixToBitList);
    chainLinks[2] =
        new BitListChainLinkPresenter(chainLinks[1], matrixToBitList, bitListToBinaryString);
    chainLinks[3] = 
        new BinaryStringPresenter(chainLinks[2], bitListToBinaryString, null);

    // set next for chain links
    for (int i = 0; i < chainLinks.length - 1; i++) {
      chainLinks[i].setNext(chainLinks[i + 1]);
    }
  }
  
  @Override
  public ImportPresenter createImportPresenter() {
    initialize();
    return new BWImageImportPresenter(this);
  }

  @Override
  public ProcedureInformation createProcedureInformation() {
    return new BWImageProcedureInformation();
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
    return new SimpleStringProperty("Kodierungsverfahren");
  }

  @Override
  public StringProperty pluginNameProperty() {
    return new SimpleStringProperty("Schwarz & Weiß Bild");
  }
}
