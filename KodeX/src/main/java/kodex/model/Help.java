package kodex.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kodex.presenter.PresenterManager;

/**
 * This class manages the FAQ. A fixed set of questions is stored locally in KodeX.Help_DE or
 * KodeX.Help_EN, which can then be read using this class.
 *
 * @author Patrick Spiesberger
 * @version 1.0
 */
public class Help {

  /* List of all questions */
  private List<String> questions = new LinkedList<String>();

  /* List of all answers*/
  private List<String> answers = new LinkedList<String>();

  /* Informations about FAQ */
  private List<String> info = new LinkedList<String>();


  /* instance of property file */
  private Properties prop = new Properties();

  /* nessesary to read the property file */
  private InputStream input = null;

  /**
   * Sets the language in which the entries for the FAQ should be loaded.
   *
   * @param locale : selected Language
   */
  public Help(Locale locale) {
    String url = "help/Help_" + locale + ".properties";
    input = getClass().getResourceAsStream(url);
    try {
      prop.load(input);
    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.load.failed"));
      alert.setContentText("Couldn't load help page property files.");
      
      PresenterManager.showAlertDialog(alert);
    }

    loadQuestions();
    loadAnswers();
    loadInfo();
  }

  /**
   * returns list of all answers.
   *
   * @return list with Strings (answers)
   */
  public List<String> getAnswers() {
    return answers;
  }

  /**
   * Returns list of all information about FAQ.
   *
   * @return informations about FAQ
   */
  public List<String> getInfo() {
    return info;
  }

  /**
   * returns list of all questions.
   *
   * @return list with Strings (questions)
   */
  public List<String> getQuestions() {
    return questions;
  }

  /** loads answers from selected property file. */
  public void loadAnswers() {
    for (int i = 1; i < prop.size() / 2 + 1; i++) {
      if (prop.getProperty("answer" + i) != null) {
        answers.add(prop.getProperty("answer" + i));
      }
    }
  }

  /** loads informations about selected property file. */
  public void loadInfo() {
    info.add(prop.getProperty("info"));
  }

  /** loads questions from selected property file. */
  public void loadQuestions() {
    for (int i = 1; i < prop.size() / 2 + 1; i++) {
      if (prop.getProperty("answer" + i) != null) {
        questions.add(prop.getProperty("question" + i));
      }
    }
  }
}
