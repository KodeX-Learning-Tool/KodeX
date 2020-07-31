package kodex.standardplugins.colorimageprocedure;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.steps.ColorImageToRGBMatrix;
import kodex.pluginutils.model.steps.RGBByteListToBinaryString;
import kodex.pluginutils.model.steps.RGBListToRGBByteList;
import kodex.pluginutils.model.steps.RGBMatrixToRGBList;
import kodex.pluginutils.presenter.chainlink.BinaryStringPresenter;
import kodex.pluginutils.presenter.chainlink.ColorImageChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.RGBByteListChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.RGBListChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.RGBMatrixChainLinkPresenter;
import kodex.standardplugins.colorimageprocedure.presenter.ColorImageImportPresenter;

/**
 * This class is the entry point to the color image procedure plugin.
 *
 * @author Raimon Gramlich
 * @version 1.0
 */
public class ColorImageProcedurePlugin extends ProcedurePlugin {

  private ChainLinkPresenter[] chainLinks; // [2..*]

  /** Creates a new instance of the ColorImageProcedurePlugin. */
  public ColorImageProcedurePlugin() {
    this.chainLinks = new ChainLinkPresenter[5];

    ColorImageToRGBMatrix colorImageToRGBMatrix = new ColorImageToRGBMatrix();
    chainLinks[0] = new ColorImageChainLinkPresenter(null, null, colorImageToRGBMatrix);
    
    RGBMatrixToRGBList rgbMatrixToRGBList = new RGBMatrixToRGBList();
    chainLinks[1] = 
        new RGBMatrixChainLinkPresenter(chainLinks[0], colorImageToRGBMatrix, rgbMatrixToRGBList);
    
    RGBListToRGBByteList rgbListToRGBByteList = new RGBListToRGBByteList();
    chainLinks[2] =
        new RGBListChainLinkPresenter(chainLinks[1], rgbMatrixToRGBList, rgbListToRGBByteList);
    
    RGBByteListToBinaryString rgbByteListToBinaryString = new RGBByteListToBinaryString();
    chainLinks[3] = new RGBByteListChainLinkPresenter(chainLinks[2], 
        rgbListToRGBByteList, rgbByteListToBinaryString);
    
    chainLinks[4] = new BinaryStringPresenter(chainLinks[3], rgbByteListToBinaryString, null);

    // set next for chain links
    for (int i = 0; i < chainLinks.length - 1; i++) {
      chainLinks[i].setNext(chainLinks[i + 1]);
    }
  }

  @Override
  public ColorImageImportPresenter createImportPresenter() {
    return new ColorImageImportPresenter(this);
  }

  @Override
  public ColorImageProcedureInformation createProcedureInformation() {
    return new ColorImageProcedureInformation();
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
    return new SimpleStringProperty("Das Kodierungsverfahren Bild-zu-Bitfolge.");
  }

  @Override
  public StringProperty pluginNameProperty() {
    return new SimpleStringProperty("Farbbildverfahrensplugin");
  }
}
