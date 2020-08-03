package edu.kit.scc.git.kodex.standardplugins.greyscaleimageprocedure;

import edu.kit.scc.git.kodex.plugininterface.ChainLinkPresenter;
import edu.kit.scc.git.kodex.plugininterface.Content;
import edu.kit.scc.git.kodex.plugininterface.ImportPresenter;
import edu.kit.scc.git.kodex.plugininterface.ProcedureInformation;
import edu.kit.scc.git.kodex.plugininterface.ProcedurePlugin;
import edu.kit.scc.git.kodex.pluginutils.model.steps.ByteListToBinaryString;
import edu.kit.scc.git.kodex.pluginutils.model.steps.DecMatrixToByteList;
import edu.kit.scc.git.kodex.pluginutils.model.steps.GreyScaleImageToDecMatrix;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.BinaryStringPresenter;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.ByteListChainLinkPresenter;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.ColorImageChainLinkPresenter;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.DecMatrixChainLinkPresenter;
import edu.kit.scc.git.kodex.standardplugins.greyscaleimageprocedure.presenter.GreyScaleImageImportPresenter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is responsible for the administration of the specific procedure
 * "greyscale image to binary sequence". This class holds a list of ChainLinks
 * as attributes, i.e. the different steps of this coding chain.
 *
 * @author Patrick Spiesberger
 * @version 1.0
 */
public class GreyScaleImageProcedurePlugin extends ProcedurePlugin {

  /* steps of this coding chain */
  private ChainLinkPresenter[] chainLinks; // [2..*]

  /** Constructor of class GreyScaleImageProecedurePlugin. Sets all chainLinks */
  public GreyScaleImageProcedurePlugin() {

  }
  
  /**
   * Initialize the procedure plugin.
   */
  private void initialize() {
    chainLinks = new ChainLinkPresenter[4];

    GreyScaleImageToDecMatrix greyScaleImageToDecMatrix = new GreyScaleImageToDecMatrix();
    DecMatrixToByteList decMatrixToByteList = new DecMatrixToByteList();
    ByteListToBinaryString byteListToBinaryString = new ByteListToBinaryString();

    chainLinks[0] = new ColorImageChainLinkPresenter(null, null, greyScaleImageToDecMatrix);
    chainLinks[1] = new DecMatrixChainLinkPresenter(chainLinks[0], 
        greyScaleImageToDecMatrix, decMatrixToByteList);
    chainLinks[2] = new ByteListChainLinkPresenter(chainLinks[1], 
        decMatrixToByteList, byteListToBinaryString);
    chainLinks[3] = new BinaryStringPresenter(chainLinks[2], byteListToBinaryString, null);

    // set next for chain links
    for (int i = 0; i < chainLinks.length - 1; i++) {
      chainLinks[i].setNext(chainLinks[i + 1]);
    }
  }
  
  @Override
  public ImportPresenter createImportPresenter() {
    initialize();
    return new GreyScaleImageImportPresenter(this);
  }

  @Override
  public ProcedureInformation createProcedureInformation() {
    return new GreyScaleProcedureInformation();
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
    return new SimpleStringProperty("Graustufen Bild");
  }
}
