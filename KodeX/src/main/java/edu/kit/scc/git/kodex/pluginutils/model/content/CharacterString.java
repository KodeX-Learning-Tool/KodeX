package edu.kit.scc.git.kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.kit.scc.git.kodex.model.I18N;
import edu.kit.scc.git.kodex.presenter.PresenterManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class holds data in string format. A CharacterString consists of characters. 
 * Extending AbstractString, it adds validation and exporting capabilities to Java's
 * String.
 * 
 * @author Yannick Neubert
 * @author Raimon Gramlich
 * @author Parick Spiesberger
 * 
 * @version 2.0
 */
public class CharacterString extends AbstractString {

  /** Instantiates a new CharacterString. */
  public CharacterString() {
    super.data = "";
  }

  /** Creates a new CharacterString and sets it's value.
   * 
   * @param str The String to be used as data 
   */
  public CharacterString(String str) {
    super.data = str;
  }

  @Override
  public boolean isValid(String input) {
    if (input == null) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("Input is empty");
      PresenterManager.showAlertDialog(alert);
      return false;
    }
    return true;
  }

  @Override
  public void export(File file) {
    try {
      FileWriter writer = new FileWriter(file);

      //header
      writer.write("HEADER\n");
      header.forEach((key, value) -> { 
        try {
          writer.write(key + " " + value + "\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      //content
      writer.write("CONTENT\n");
      writer.write(data);
      writer.close();
    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR);
      
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("Something went wrong creating this file");
      PresenterManager.showAlertDialog(alert);
    }
  }
  
}
