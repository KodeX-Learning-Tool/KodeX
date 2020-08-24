package kodex.pluginutils.presenter.edit;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import kodex.exceptions.AlertWindowException;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;
import kodex.model.Tuple;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.TupleString;

public class TupleStringEditPresenter extends ChainLinkEditPresenter {

  private static final int MAX_NUM = 2000;
  private TextArea tupleStringArea;
  private AnchorPane view;
  private TupleString content;

  /**
   * Instantiates a new tuple string edit presenter.
   *
   * @param chainLinkPresenter the chain link presenter
   */
  public TupleStringEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);

    tupleStringArea = new TextArea();
    tupleStringArea.setWrapText(true);

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
  public void handleSubmit() throws AlertWindowException {
    
    String[] tupleParts = tupleStringArea.getText().split(":");
    
    if (tupleParts.length != 2
        || !isValidLetter(tupleParts[0])
        || !isValidNumber(tupleParts[1])) {

      throw new InvalidInputException(
          AlertType.ERROR,
          I18N.get("alert.title.error"),
          I18N.get("alert.input.invalid"),
          "The entered text doesn't have the right format (someLetter:someNumber)!");
    }
    
    //only use number smaller then the given max
    if (Integer.parseInt(tupleParts[1]) > MAX_NUM) {
      
      throw new InvalidInputException(
          AlertType.ERROR,
          I18N.get("alert.title.error"),
          I18N.get("alert.input.invalid"),
          "Please only use numbers smaller or equal to 2000, to prevent lagging.");
    }
    
    content.getTuples()[markID] = 
        new Tuple<String, Integer>(tupleParts[0], Integer.parseInt(tupleParts[1]));
    
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {
    tupleStringArea.setText(content.getTuples()[markID].toString());
  }
}
