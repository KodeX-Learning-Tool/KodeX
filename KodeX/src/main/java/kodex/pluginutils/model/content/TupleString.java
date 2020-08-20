package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringJoiner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kodex.model.I18N;
import kodex.model.Tuple;
import kodex.plugininterface.Content;
import kodex.presenter.PresenterManager;

public class TupleString extends Content<String> {

  private Tuple<String, Integer>[] tuples;

  /**
   * Get the contained tuples.
   *
   * @return the tuples
   */
  public Tuple<String, Integer>[] getTuples() {
    return tuples;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean isValid(String input) {

    String[] inputStrings = input.split(" ");
    
    tuples = new Tuple[inputStrings.length];

    for (int i = 0; i < inputStrings.length; i++) {

      String[] tupleParts = inputStrings[i].split(":");
      


      if (tupleParts.length != 2) {
        showAlert("Import is not a tupel");
        return false;
      }

      if (!isValidLetter(tupleParts[0])) {
        showAlert("invalid letter at position 0");
        return false;
      }

      if (!isValidNumber(tupleParts[1])) {
        showAlert("invalid letter at position 1");
        return false;
      }

      tuples[i] = new Tuple<>(tupleParts[0], Integer.parseInt(tupleParts[1]));
    }

    return true;
  }
  
  /**
   * Show an alert for invalid input.
   *
   * @param contentText the content text
   */
  private void showAlert(String contentText) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
    alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
    alert.setContentText(contentText);
    PresenterManager.showAlertDialog(alert);
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

  /**
   * Set the contained tuples array.
   *
   * @param tuples the tuples to set
   */
  public void setTuples(Tuple<String, Integer>[] tuples) {
    this.tuples = tuples;
  }

  @Override
  public void export(File file) {
    try {
      FileWriter writer = new FileWriter(file);
      
      StringJoiner content = new StringJoiner(" ");
      
      Arrays.asList(tuples).forEach(t -> content.add(t.toString()));
      
      //content
      writer.write(content.toString());
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    
    if (!(obj instanceof TupleString)) {
      return false;
    }
    
    TupleString other = (TupleString) obj;
    
    return Arrays.equals(tuples, other.getTuples());
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((tuples == null) ? 0 : Arrays.hashCode(tuples));
    return result;
  }
}
