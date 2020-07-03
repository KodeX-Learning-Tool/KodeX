package kodex.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * Diese Klasse verwaltet das FAQ. Dabei ist ein fester Fragensatz lokal in 
 * KodeX.Help_DE beziehungsweise KodeX.Help_EN gespeichert, der dann 
 * mithilfe dieser Klasse ausgelesen werden kann.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public class Help {

	/* Liste mit allen geladenen Fragen */
    private List<String> questions;

    /* Liste mit allen geladenen Antworten */
    private List<String> answers;

    /* Lädt Informationen über das FAQ */
    private String info;

    /* Speichert die aktuelle Sprache */
    private Locale locale;
    
    /* Instanz der Property Datei */
    private Properties prop = new Properties();
    
    /* Stream, zum einlesen der Datei */
    private InputStream input = null;


    /**
     * Setzt die Sprache, in welcher dann die Einträge für das FAQ geladen werden sollen
     * @param locale : ausgewählte Sprache
     */
    public Help(Locale locale) {
        this.locale = locale;
    	input = getClass().getClassLoader().getResourceAsStream("Help_" + this.locale + ".properties");
    	try {
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Error during reading File");
		}
    }

    /**
     * Lädt die Fragen des FAQ zu der gegebenen Sprache
     */
    public void loadQuestions() {
        for (int i = 1; i < prop.size(); i++) {
        	questions.add(prop.getProperty("question" + i));
        }
    }

    /**
     * Lädt die Antworten des FAQ zu der gegebenen Sprache
     */
    public void loadAnswers() {
        for (int i = 1; i < prop.size(); i++) {
        	answers.add(prop.getProperty("answer" + i));
        }
    }

    /**
     * Lädt die Info des FAQ zu der gegebenen Sprache
     */
    public void loadInfo() {
    	info = prop.getProperty(info);
    }

    /**
     * Gibt eine Liste aller Fragen zurück
     * @return Liste mit allen Fragen
     */
    public List<String> getQuestions() {
        return questions;
    }

    /**
     * Gibt eine Liste aller Antworten zurück
     * @return Liste mit allen Antworten
     */
    public List<String> getAnswers() {
    	return answers;
    }

    /**
     * Gibt Informationen über das  FAQ zurück
     * @return Text mit Informationen
     */
    public String getInfo() {
        return info;
    }

}