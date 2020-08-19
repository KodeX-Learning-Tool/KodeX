package kodex.standardplugins.bwimageprocedure;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.steps.BWImageToMatrix;
import kodex.pluginutils.model.steps.BitListToBinaryString;
import kodex.pluginutils.model.steps.MatrixToBitList;
import kodex.pluginutils.presenter.chainlink.BWImageChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.BWMatrixChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.BinaryStringPresenter;
import kodex.pluginutils.presenter.chainlink.BitListChainLinkPresenter;
import kodex.standardplugins.bwimageprocedure.presenter.BWImageImportPresenter;

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
        new BWImageChainLinkPresenter(null, null, bwImageToMatrix);
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
    chainLinks[chainLinks.length - 1].setContent(content);
  }

  @Override
  public void initEncodeProcedure(Content<?> content) {
    chainLinks[0].setContent(content);
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
