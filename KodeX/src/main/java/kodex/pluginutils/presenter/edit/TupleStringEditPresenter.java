package kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import kodex.model.Tuple;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.TupleString;

public class TupleStringEditPresenter extends ChainLinkEditPresenter {

  private TextArea tupleStringArea;
  private TextFormatter<String> tupleFormatter;
  private AnchorPane view;
  private TupleString content;

  /**
   * Instantiates a new tuple string edit presenter.
   *
   * @param chainLinkPresenter the chain link presenter
   */
  public TupleStringEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);

    // only allows letters followed by ":" and a number
    UnaryOperator<TextFormatter.Change> filter =
        change -> {
          String[] tupleParts = change.getControlNewText().split(":");

          if (tupleParts.length != 2
              || !isValidLetter(tupleParts[0])
              || !isValidNumber(tupleParts[1])) {

            return null;
          }

          return change;
        };

    tupleStringArea = new TextArea();
    tupleStringArea.setWrapText(true);
    tupleFormatter = new TextFormatter<>(filter);
    tupleStringArea.setTextFormatter(tupleFormatter);

    view = new AnchorPane(tupleStringArea);

    content = (TupleString) chainLinkPresenter.getContent();
  }

  /**
   * Checks if the given string is a valid letter.
   *
   * @param input The input string.
   * @return True if the string is a valid letter.
   */
  private boolean isValidLetter(String input) {

    char[] charArray = input.toCharArray();

    if (charArray.length != 1) {
      return false;
    }

    char firstElement = charArray[0];

    return Character.isLetter(firstElement);
  }

  /**
   * Checks if the given string is a valid integer.
   *
   * @param input The input string.
   * @return True if the string is a valid integer.
   */
  private boolean isValidNumber(String input) {
    return input.matches("^[1-9]\\d*$");
  }

  @Override
  public AnchorPane getView() {

    updateMarkedElement();

    return view;
  }

  @Override
  public void handleSubmit() {
    
    String[] tupleParts = tupleStringArea.getText().split(":");
    
    content.getTuples()[markID] = new Tuple(tupleParts[0], Integer.parseInt(tupleParts[1]));
    
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {
    tupleStringArea.setText(content.getTuples()[markID].toString());
  }
}
