package kodex.pluginutils.presenter.chainlink;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.LetterString;
import kodex.pluginutils.presenter.chainlink.TupleStringPresenter.TupleText;

public class LetterStringPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Buchstabenkette";
  
  private List<Text> textStrings;

  private int id;
  
  private Text oldMark;

  public LetterStringPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    this.content = new LetterString();
  }

  @Override
  public AnchorPane getView() {

    AnchorPane ap = new AnchorPane();
    
    String letterString = ((LetterString) content).getLetterString();
    
    if (letterString.isEmpty()) {
      return ap;
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
        text.setOnMouseClicked(e -> {
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
    text.setOnMouseClicked(e -> {
      this.id = index;
      toggleMark(text);
      handleMark();
    });
    textStrings.add(text);
    
    TextFlow tf = new TextFlow();
    
    tf.setPrefWidth(400);
    tf.setStyle("-fx-font-size: 18;");
    
    tf.getChildren().addAll(textStrings);

    ap.getChildren().add(tf);
    return ap;
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
    // TODO Auto-generated method stub
    
  }
}
