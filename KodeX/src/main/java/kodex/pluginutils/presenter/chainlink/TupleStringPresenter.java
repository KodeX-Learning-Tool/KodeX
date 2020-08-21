package kodex.pluginutils.presenter.chainlink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.TupleString;
import kodex.pluginutils.presenter.edit.TupleStringEditPresenter;

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

  private ArrayList<Text> tupeTexts;

  private TextFlow tf;
  
  @Override
  protected int calculateID() {
    return id;
  }
  
  /**
   * Instantiates a new tuple string presenter.
   *
   * @param previous     the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep     the next step
   */
  public TupleStringPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    this.content = new TupleString();
    this.chainLinkEditPresenter = new TupleStringEditPresenter(this);
  }

  @Override
  public AnchorPane getView() {

    updateTextFlow();
    
    AnchorPane ap = new AnchorPane();
    ap.getChildren().add(tf);
    return ap;
  }
  
  private void updateTextFlow() {
    
    if (tf == null) {
      tf = new TextFlow();
      tf.setPrefWidth(400);
      tf.setStyle("-fx-font-size: 18;");
    }
    
    refreshViewList();
    
    tf.getChildren().clear();
    tf.getChildren().addAll(tupeTexts);
  }
  
  private void refreshViewList() {
    
    List<String> tupleStrings = new LinkedList<>();
    Arrays.stream(((TupleString) content).getTuples()).forEach(t -> tupleStrings.add(t.toString()));
    
    tupeTexts = new ArrayList<>();
    
    for (int i = 0; i < tupleStrings.size(); i++) {
      Text text = new Text(tupleStrings.get(i) + " ");
      final int index = i;
      
      text.setOnMouseClicked(e -> {
        this.id = index;
        toggleMark(text);
        handleMark();
      });
      
      tupeTexts.add(text);
      tf.getChildren().add(text);
    }
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }
  
  private void toggleMark(Text text) {
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
    updateTextFlow();
  }
  
  @Override
  public List<ExtensionFilter> getExtensionsFilter() {
    List<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.text"), "*.txt"));
    return extensionFilters;
  }
}
