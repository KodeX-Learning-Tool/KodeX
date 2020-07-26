package kodex.pluginutils.presenter.chainlink;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.TupleString;

/**
 * This class provides a chain link presenter for an array of string tuples thta should be
 * represented as a string.
 *
 * @author Leonhard Kraft
 */
public class TupleStringPresenter extends ChainLinkPresenter {

  public TupleStringPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    this.content = new TupleString();
  }

  @Override
  public AnchorPane getView() {

    AnchorPane ap = new AnchorPane();
    Label displaytext = new Label();

    List<String> tupleStrings = new LinkedList<>();
    Arrays.stream(((TupleString) content).getTuples()).forEach(t -> tupleStrings.add(t.toString()));
    ;

    displaytext.setText(String.join(" ", tupleStrings));
    ap.getChildren().add(displaytext);
    return ap;
  }

  @Override
  protected void mark(int id) {
    // TODO Auto-generated method stub

  }
}
