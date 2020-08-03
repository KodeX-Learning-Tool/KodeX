package edu.kit.scc.git.kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import edu.kit.scc.git.kodex.model.I18N;
import edu.kit.scc.git.kodex.presenter.PresenterManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class holds data in LinkedList format. An List consists of Strings.
 * Extending AbstractList, it adds validation and exporting capabilities to
 * Javas List.
 * 
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class ByteList extends AbstractList<String> {

  /** Creates a new RGBList. */
  public ByteList() {
    super.list = new LinkedList<String>();
  }

  @Override
  public boolean isValid(Object input) {    
    if (input == null) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("Input is empty");
      PresenterManager.showAlertDialog(alert);
      return false;
    }
    String byteValue = (String) input;
    
    if (byteValue.length() != 8) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("This is not a byte");
      PresenterManager.showAlertDialog(alert);
      return false;
    }
    if (!byteValue.matches("[01]+")) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("This is not a byte");
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
      @SuppressWarnings("unchecked")
      HashMap<String, Object> map = (HashMap<String, Object>) header;
      map.forEach((key, value) -> { 
        try {
          writer.write(key + " " + value + "\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      //content
      writer.write("CONTENT\n");
      for (int y = 0; y < size(); y++) {

        String row = get(y).toString().substring(0, 8);

        if (y != size() - 1)  {
          writer.write(row + "\n");
        } else {
          writer.write(row);
        }
      }

      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
}
