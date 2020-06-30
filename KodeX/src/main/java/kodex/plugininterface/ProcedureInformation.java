package kodex.plugininterface;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;

/**
 * Diese abstrakte Klasse gibt vor, welche Informationen über ein Verfahren bekannt sein
 * sollten und verwaltet diese. 
 * Jedes Plugin muss diese Klasse erweitern. Jedoch muss die Beschreibung, die Labels und
 * das Icon nicht implementiert werden (Standard Werte).
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public abstract class ProcedureInformation {

    /**
     * Konstruktor der Klasse ProcedureInformatio
     */
    public ProcedureInformation() {}
    

    /* 
     * Standard Bild, welches einfügt wird, wenn kein anderes vorhanden
     */
    protected Image defaultIcon;

    /**
     * Gibt den Namen des Verfahrens zurück
     * @return Name des Verfahrens
     */
    public abstract String getName();

    /**
     * Gibt das Icon, welches für die Startseite verwendet werden soll zurück
     * @return Icon für die Startseite
     */
    public Image getIcon() {
        return defaultIcon; //Standard Anzeigebild
    }

    /**
     * Gibt eine Liste der Labels des Verfahrens zurück. An erster Stelle in der Liste
     * muss die Klassenstufe stehen, ab welcher Klassenstufe das Verfahren geeignet ist
     * @return Liste der Labels
     */
    public List<String> getLabels() {
        List<String> labels = new LinkedList<>();
        labels.add("7"); //Standard Klassenstufe
        return labels;
    }

    /**
     * Gibt eine Kurzbeschreibung des Verfahrens zurück
     * @return Beschreibung des Verfahrens
     * Hinweis: Die Beschreibung ist auf einen String begrenzt
     */
    public String getDescription() {
        return ""; //Standard Beschreibung
        //TODO: Language.getMessage("noDescription"); 
    }

}