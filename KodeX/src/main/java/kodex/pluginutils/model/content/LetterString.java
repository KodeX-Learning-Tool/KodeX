package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;

public class LetterString extends AbstractString {

  private String letterString;

  /**
   * Gets the letter string.
   *
   * @return the letterString
   */
  public String getLetterString() {
    return letterString;
  }

  @Override
  public boolean isValid(String input) throws InvalidInputException {
    if (input == null) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Content validation input is empty");
    }

    if (!input.chars().allMatch(Character::isLetter)) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Input contains characters other than letters");
    }

    this.letterString = input;

    return true;
  }

  @Override
  public void export(File file) {
    try {
      FileWriter writer = new FileWriter(file);

      //content
      writer.write(letterString);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
