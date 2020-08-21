package kodex.pluginutils.presenter.chainlink;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.LetterString;
import kodex.pluginutils.presenter.edit.LetterStringEditPresenter;

public class LetterStringPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Buchstabenkette";

  private List<Text> textStrings;

  private int id;

  private Text oldMark;

  private TextFlow tf;
  
  /**
   * Instantiates a new letter string presenter.
   *
   * @param previous     the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep     the next step
   */
  public LetterStringPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    this.content = new LetterString();
    this.chainLinkEditPresenter = new LetterStringEditPresenter(this);
  }

  @Override
  public AnchorPane getView() {

    String letterString = ((LetterString) content).getLetterString();

    updateTextFlow(letterString);
    
    AnchorPane ap = new AnchorPane();
    ap.getChildren().add(tf);
    
    return ap;
  }
  
  private void updateTextFlow(String letterString) {
    
    if (tf == null) {
      tf = new TextFlow();
      tf.setPrefWidth(400);
      tf.setStyle("-fx-font-size: 18;");
    }
    
    refreshViewList(letterString);
    
    tf.getChildren().clear();
    tf.getChildren().addAll(textStrings);
  }
  
  private void refreshViewList(String letterString) {
    
    if (letterString.isEmpty()) {
      return;
    }

    textStrings = new ArrayList<>();

    char currentChar = letterString.charAt(0);
    char newChar;
    int count = 1;

    int id = 0;

    for (int i = 1; i < letterString.length(); i++) {

      newChar = letterString.charAt(i);

      if (newChar != currentChar) {

        final int index = id;

        Text text = new Text(Character.toString(currentChar).repeat(count));
        text.setOnMouseClicked(
            e -> {
              this.id = index;
              toggleMark(text);
              handleMark();
            });
        textStrings.add(text);

        currentChar = newChar;
        count = 1;

        id++;

      } else {
        count++;
      }
    }
    final int index = id;

    Text text = new Text(Character.toString(currentChar).repeat(count));
    text.setOnMouseClicked(
        e -> {
          this.id = index;
          toggleMark(text);
          handleMark();
        });
    textStrings.add(text);
    
  }

  private void toggleMark(Text text) {
    if (oldMark != null) {
      oldMark.setStyle("-fx-fill: black;");
    }
    text.setStyle("-fx-fill: red;");
    this.oldMark = text;
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {
    toggleMark(textStrings.get(id));
  }

  @Override
  protected int calculateID() {
    return id;
  }

  @Override
  public void updateView() {
    updateTextFlow(((LetterString) content).getLetterString());
  }

  @Override
  public List<ExtensionFilter> getExtensionsFilter() {
    List<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.text"), "*.txt"));
    return extensionFilters;
  }
}
