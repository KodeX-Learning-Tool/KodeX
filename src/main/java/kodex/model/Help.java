package kodex.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.LoadingException;

/**
 * This class manages the FAQ. A fixed set of questions is stored locally in KodeX.Help_DE or
 * KodeX.Help_EN, which can then be read using this class.
 *
 * @author Yannick Neubert
 * @author Patrick Spiesberger
 * @version 2.0
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
   * @throws LoadingException Thrown when help property file could not be loaded.
   */
  public Help(Locale locale) throws LoadingException {
    String url = "help/Help_" + locale + ".properties";
    input = getClass().getResourceAsStream(url);
    try {
      prop.load(input);
    } catch (IOException e) {
      
      throw new LoadingException(
          AlertType.ERROR,
          I18N.get("alert.title.error"),
          I18N.get("alert.load.failed"),
          "Couldn't load help page property files.");
    }
    
    load();
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
  
  /** loads the entries in the property file. */
  private void load() {
    prop.entrySet().stream()
        .sorted((k1, k2) -> k1.getKey().toString().compareTo(k2.getKey().toString()))
        .forEach(k -> {
          String type = k.getKey().toString().substring(0, k.getKey().toString().length() - 1);
          switch (type) {
            case "question": questions.add(k.getValue().toString()); 
              break;
            case "answer": answers.add(k.getValue().toString()); 
              break;
            case "info": info.add(k.getValue().toString() + "\n");  
              break;
            default: break;
          }
        });
  }
  
}
