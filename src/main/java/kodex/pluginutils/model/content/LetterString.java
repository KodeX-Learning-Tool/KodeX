package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;

public class LetterString extends AbstractString {

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

    this.data = input;

    return true;
  }

  @Override
  public void export(File file) {
    try {
      FileWriter writer = new FileWriter(file);

      //header
      writer.write("HEADER\n");
      if (header != null) {
        HashMap<String, Object> map = (HashMap<String, Object>) header;
        map.forEach((key, value) -> { 
          try {
            writer.write(key + " " + value + "\n");
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
      }

      //content
      writer.write("CONTENT\n");
      writer.write(data);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
