package kodex.plugininterface;

import java.io.File;
import java.util.Map;

/**
 * Diese Klasse stellt das Interface für Content dar. Sie verwaltet den Inhalt einer Stufe sowie
 * den dazu gehörigen Header. Zudem enthält sie die Funktionalität, ihren Content, mit einem
 * gegebene File, zu exportieren und mit einer allgemein gültigen Identifikationsnummer anzusprechen.
 * Jeder Content muss dieses Interface implementieren.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 * 
 */
public interface Content {

    /**
     * Setzt den Header für diesen Content
     * @param header : Header des Contents als Map
     */
    public void setHeader(Map header);
    
    
    /**
     * Gibt den Wert des Headers dieses Contents zu einem gegebenen Key zurück
     * @param key : Der Schlüssel, um auf Elemente des Header zugreifen zu können
     * @return Der Wert, welcher mit Hilfe des Keys identifiziert wird, aus dem Header
     */
    public String getHeaderValue(String key);

    /**
     * Exportiert den Inhalt dieses Contents and das übergebenen Ort
     * @param file : Der Ort an welches der Inhhalt dieses Contents geschreiben werden soll
     */
    public void export(File file);

    /**
     * Gibt zurück ob die Eingabe für diesen Content valide ist
     * @param input : Die Eingabe für diesen Content
     * Hinweis: Muss von Klassen die dieses Interface implementieren genauer definiert werden
     * @return true, falls die Eingabe für diesen Content valide, ansonsten false
     */
    public Boolean isValid(Object input);

}