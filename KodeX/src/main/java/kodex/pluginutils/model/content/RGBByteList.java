package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kodex.model.I18N;
import kodex.presenter.PresenterManager;

/**
 * This class holds data in LinkedList format. An RGBByteList consists of RGB triplets in exactly
 * that order in binary. Extending AbstractList, it adds validation and exporting capabilities to
 * Java's List.
 */
public class RGBByteList extends AbstractList<String> {

  /** Creates a new RGBByteList. */
  public RGBByteList() {
    super.list = new LinkedList<String>();
  }

  @Override
  public boolean isValid(Object input) { 
    RGBByteList object;
    
    Alert alert = new Alert(AlertType.ERROR);
    alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
    alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));

    if (input == null) {
      alert.setContentText("Input is empty");
      PresenterManager.showAlertDialog(alert);
      return false;
    }

    try {
      object = ((RGBByteList) input);
    } catch (ClassCastException e) {
      alert.setContentText("Import is of wrong type");
      PresenterManager.showAlertDialog(alert);
      return false;
    }

    if (object.size() % 3 != 0) {
      alert.setContentText("Invalid import, import does not excludingly contain rgb triplets");
      PresenterManager.showAlertDialog(alert);
      return false;
    }

    for (int i = 0; i < object.size(); i++) {
      String rgb = object.get(i);
      if (rgb.length() != 8) {
        alert.setContentText("Invalid import, import does not excludingly contain rgb values");
        PresenterManager.showAlertDialog(alert);
        return false;
      }

      for (int j = 0; j < rgb.length(); j++) {
        if (rgb.charAt(i) != '0' && rgb.charAt(i) != '1') {
          alert.setContentText("Invalid import, import does not excludingly contain rgb values");
          PresenterManager.showAlertDialog(alert);
          return false;
        }
      }
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
      //the unchecked warning is suppressed since it has no negative effects
      //it's caused by the lambda expression used in writing the header in combination with
      //this class extending a generic superclass. There appears to be no way of avoiding this.
      HashMap<String, Object> map = (HashMap<String, Object>) header;
      map.forEach((key, value) -> { 
        try {
          writer.write(key + " " + value + "\n");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      });

      //content
      writer.write("CONTENT\n");
      for (int y = 0; y < size() / 3; y++) {

        String row = get(y * 3).substring(0, 8);
        row += " " + get(y * 3 + 1).substring(0, 8);
        row += " " + get(y * 3 + 2).substring(0, 8);

        if (y != size() - 1) {
          writer.write(row + "\n");
        } else {
          writer.write(row);
        }
      }

      writer.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
