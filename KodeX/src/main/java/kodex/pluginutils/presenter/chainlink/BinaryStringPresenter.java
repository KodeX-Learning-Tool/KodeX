package kodex.pluginutils.presenter.chainlink;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.presenter.edit.BinaryStringEditPresenter;
import kodex.pluginutils.presenter.header.BinaryStringHeaderPresenter;

/**
 * The Class BinaryStringChainLinkPresenter manages the view for the binary string.
 *
 * @author Raimon Gramlich
 */
public class BinaryStringPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Binärkette";
  
  /** The prefix before the marked part. */
  private Text prefix;
  
  /** The marked text. */
  private Text markedText;
  
  /** The suffix after the marked part. */
  private Text suffix;

  /** The whole binary string. */
  private String binaryString;

  /** The Constant NOT_MARKED. */
  private static final int NOT_MARKED = -1;
  
  /** The last marked ID. */
  private int lastElementMarked = NOT_MARKED;

  /**
   * Instantiates a new binary string presenter.
   *
   * @param previous the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep the next step
   */
  public BinaryStringPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    content = new BinaryString();
    chainLinkEditPresenter = new BinaryStringEditPresenter(this);
    chainLinkHeaderPresenter = new BinaryStringHeaderPresenter(this.getContent());
    
    prefix = new Text();
    markedText = new Text();
    markedText.setFill(Color.RED);
    suffix = new Text();
  }

  @Override
  public AnchorPane getView() {    
    TextFlow binaryTextFlow = new TextFlow();
    
    prefix.setText(((BinaryString) (content)).getString());
    
    binaryTextFlow.getChildren().addAll(prefix, markedText, suffix);
    binaryTextFlow.setMaxWidth(400);    
    
    return new AnchorPane(binaryTextFlow);
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {
    lastElementMarked = id;
    int unitLength = (int) content.getHeader().get("unit-length");
    
    prefix.setText(binaryString.substring(0, Math.max(0, unitLength * id)));
    markedText.setText(binaryString.substring(unitLength * id, unitLength * id + unitLength));    
    suffix.setText(binaryString.substring(
        Math.min(unitLength * (id + 1), binaryString.length()), binaryString.length()));
    
    chainLinkEditPresenter.setMarkID(id);
  }

  @Override
  public void updateView() {
    binaryString = ((BinaryString) (content)).getString();
    
    // remarks the view
    if (lastElementMarked !=  NOT_MARKED) {
      mark(lastElementMarked);
    }
  }
}
