package kodex.pluginutils.presenter.chainlink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.TupleString;

/**
 * This class provides a chain link presenter for an array of string tuples that should be
 * represented as a string.
 *
 * @author Leonhard Kraft
 */
public class TupleStringPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Tupelkette";
  
  private int id;
  
  private Text oldMark;

  private ArrayList<TupleText> tupeTexts;
  
  class TupleText extends Text {
    
    private int id;
    
    public TupleText(int id, String text) {
      super(text);
      this.id = id;
    }
  }
  
  @Override
  protected int calculateID() {
    return id;
  }

  public TupleStringPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    this.content = new TupleString();
  }

  @Override
  public AnchorPane getView() {

    TextFlow tf = new TextFlow();
    
    tf.setPrefWidth(400);
    tf.setStyle("-fx-font-size: 18;");
    
    List<String> tupleStrings = new LinkedList<>();
    Arrays.stream(((TupleString) content).getTuples()).forEach(t -> tupleStrings.add(t.toString()));
    
    this.tupeTexts = new ArrayList<>();
    
    for (int i = 0; i < tupleStrings.size(); i++) {
      TupleText text = new TupleText(i, tupleStrings.get(i) + " ");
      final int index = i;
      
      text.setOnMouseClicked(e -> {
        this.id = index;
        toggleMark(text);
        handleMark();
      });
      
      tupeTexts.add(text);
      tf.getChildren().add(text);
    }
    
    AnchorPane ap = new AnchorPane();
    ap.getChildren().add(tf);
    return ap;
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }
  
  private void toggleMark(TupleText text) {
    if (oldMark != null) {
      oldMark.setStyle("-fx-fill: black;");
    }
    text.setStyle("-fx-fill: red;");
    this.oldMark = text;
  }

  @Override
  protected void mark(int id) {

    toggleMark(tupeTexts.get(id));
  }

  @Override
  public void updateView() {
    // TODO Auto-generated method stub
    
  }
}
