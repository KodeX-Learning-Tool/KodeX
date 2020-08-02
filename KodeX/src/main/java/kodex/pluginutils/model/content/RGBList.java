package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import kodex.model.I18N;
import kodex.presenter.PresenterManager;

/**
 * This class holds data in LinkedList format. An RGBList consists of colors. Extending
 * AbstractList, it adds validation and exporting capabilities to Java's List.
 */
public class RGBList extends AbstractList<Color> {

  /** Creates a new RGBList. */
  public RGBList() {
    super.list = new LinkedList<Color>();
  }

  @Override
  public boolean isValid(Object input) {
    RGBList object;

    Alert alert = new Alert(AlertType.ERROR);
    alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
    alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
    
    if (input == null) {
      alert.setContentText("Input is empty");
      PresenterManager.showAlertDialog(alert);
      return false;
    }

    try {
      object = ((RGBList) input);
      if (object.size() > MAX_LIST_LENGTH) {
        alert.setContentText("File is too large");
        PresenterManager.showAlertDialog(alert);
        return false;
      }
    } catch (ClassCastException e) {
      alert.setContentText("Input is of wrong type");
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
      //the unchecked warning is suppressed since it has no negative effects
      //it's caused by the lambda expression used in writing the header in combination with
      //this class extending a generic superclass. There appears to be no way of avoiding this.
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
