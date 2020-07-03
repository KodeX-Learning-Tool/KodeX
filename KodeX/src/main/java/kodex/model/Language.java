package kodex.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Properties;


/**
 * Diese Klasse stellt die Werte für Text in der GUI in der ausgewählten Sprache bereit.
 * Dabei ruft ein Presenter getMessage mit der gewünschten Message auf und kriegt das
 * Ergebnis in der richtigen Sprache zurück. Da sie überall benötigt wird ist
 * diese Klasse ein Singleton.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public class Language {

    /* Aktuelle Instanz der Sprache */
    private static Language instance;

    /* Liste mit allen verfügbaren Sprachen */
    private List<Locale> languages;

    /* Datei, welche aktuell gewählte Sprache beinhaltet */
    private File currentLanguageFile;
    
    /* Instanz der Property Datei */
    private Properties prop = new Properties();
    
    /* Stream, zum einlesen der Datei */
    private InputStream input = null;

    /**
     * Konstruktor der Klasse Language. Da diese Klasse jedoch
     * ein Singleton ist, kann nur eine Instanz erzeugt werden
     */
    private Language(Locale language) {
    	currentLanguageFile = new File("Language_" + language + ".properties");
    	input = getClass().getClassLoader().getResourceAsStream(currentLanguageFile.toString());
    	try {
			prop.load(input);
			instance = this;
		} catch (IOException e) {
			System.out.println("Language can not be selected");
		}
    }

    /**
     * Stellt die Singleton Instanz dieser Klasse parat. Von dieser kann dann 
     * der Presenter direkt die gewünschte Message anfordern
     * 
     * @return Die Singleton Instanz dieser Klasse
     */
    public static Language getInstance() {
        return instance;
    }

    /**
     * Gibt eine Liste aller vorhandenen Sprachen zurück
     * @return Liste mit allen verfügbaren Sprachen
     */
    public List<Locale> getLanguageList() {
        return languages;
    }

    /**
     * Diese Methode wird mit der gewünschten Message aufgerufen, um diese
     * in der ausgewählten Sprache zu erhalten
     * @param message : Nachricht, welche in der entsprechenden Sprache gefordert ist
     * @return Nachricht in der aktuellen Sprache
     */
    public String getMessage(String message) {
        return prop.getProperty(message);
    }

    /**
     * Gibt Informationen zu dieser Sprache zurück
     * @return : Abkürzung der aktuellen Sprache
     */
    public String getLanguageInfo() {
        return prop.getProperty("Language") + " : " + prop.getProperty("Domain");
    }

    /**
     * Aktualisiert die Liste der vorhandenen Sprachen
     */
    public void refreshList() {
        // TODO implement here
    }

}