package edu.kit.scc.git.kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import edu.kit.scc.git.kodex.model.I18N;
import edu.kit.scc.git.kodex.presenter.PresenterManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class holds data in Matrix format. An DecMatrix consists of a 2D array [rows][cols]
 * containing elements of the type Integer.
 * 
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class DecMatrix extends AbstractMatrix<Integer> {

  /**
   * Creates a new DecMatrix with the given dimensions.
   *
   * @param width The matrix's width
   * @param height The matrix's height
   * @throws IllegalArgumentException If either argument is less than or equal to 0
   */
  public DecMatrix(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException();
    }
    super.matrix = new Integer[height][width];
  }


  @Override
  public boolean isValid(Object input) {
    if (input == null) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("File is empty");
      PresenterManager.showAlertDialog(alert);
    }
    
    int value = (Integer) input;
    
    if (value >= 0 && value <= 256) {
      return true;
    }
    
    Alert alert = new Alert(AlertType.ERROR);
    alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
    alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
    alert.setContentText("This is not a byte");
    PresenterManager.showAlertDialog(alert);
    return false;
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
      String row = "";
      for (int y = 0; y < getHeight(); y++) {
        row = "";
        for (int x = 0; x < getWidth(); x++) {
          row += get(x, y).toString().substring(0, 3) + " ";
        }
        row = row.substring(0, row.length() - 1);

        if (y != getHeight() - 1)  {
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
