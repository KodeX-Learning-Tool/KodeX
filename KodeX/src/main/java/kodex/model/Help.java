package kodex.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * This class manages the FAQ. A fixed set of questions is stored locally 
 * in KodeX.Help_DE or KodeX.Help_EN, which can then be read using this class.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public class Help {

	/* List of all questions */
    private List<String> questions = new LinkedList<String>();

    /* List of all answers*/
    private List<String> answers = new LinkedList<String>();

    /* Informations about FAQ */
    private List<String> info = new LinkedList<String>();

    /* current language */
    private Locale locale;
    
    /* instance of property file */
    private Properties prop = new Properties();
    
    /* nessesary to read the property file */
    private InputStream input = null;


    /**
     * Sets the language in which the entries for the FAQ should be loaded
     * @param locale : selected Language
     */
    public Help(Locale locale) {
        this.locale = locale;
        String url = "src/main/resources/Help/Help/Help_";
    	input = getClass().getClassLoader().getResourceAsStream(url + this.locale + ".properties");
    	try {
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Error during reading File");
		}
    }

    /**
     * loads questions from selected property file
     */
    public void loadQuestions() {
        for (int i = 1; i < prop.size(); i++) {
        	questions.add(prop.getProperty("question" + i));
        }
    }

    /**
     * loads answers from selected property file
     */
    public void loadAnswers() {
        for (int i = 1; i < prop.size(); i++) {
        	answers.add(prop.getProperty("answer" + i));
        }
    }

    /**
     * loads informations about selected property file
     */
    public void loadInfo() {
    	info.add(prop.getProperty("info"));
    }

    /**
     * returns list of all questions
     * @return list with Strings (questions)
     */
    public List<String> getQuestions() {
        return questions;
    }

    /**
     * returns list of all answers
     * @return list with Strings (answers)
     */
    public List<String> getAnswers() {
    	return answers;
    }

    /**
     * returns list of all information about FAQ
     * @return informations about FAQ
     */
    public List<String> getInfo() {
        return info;
    }

}