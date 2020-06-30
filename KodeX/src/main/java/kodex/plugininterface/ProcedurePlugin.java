package kodex.plugininterface;

/**
 * Diese Klasse bildet die Kommunikationsschnittstelle zwischen einem Plugin und dem
 * Framework. Alle Klassen, die diese Klasse erweitern, werden vom PluginLoader mit Hilfe
 * des ServiceLoaders geladen und als Verfahrensplugin angesehen. Somit kommuniziert das
 * Framework initial mit einer generalisierung dieser Klasse.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public abstract class ProcedurePlugin implements Pluginable {

    /**
     * Konstruktor der Klasse ProcedurePlugin
     */
    public ProcedurePlugin() {}

    /**
     * Liefert die Startstufe des Verfahrens zurück, wobei die Stufen 
     * als doppelt verkettete Liste intern gespeichert werden und 
     * dementsprechend miteinander verbunden sind
     * @return Presenter für die erste Stufe der Verfahrens
     */
    public abstract ChainLinkPresenter getChainHead();

    /**
     * Setzt beim kodieren den Content für die erste Stufe des Verfahrens.
     * Zudem erstellt die Methode alle Stufen des Verfahrens und
     * initialisiert diese somit
     * @param content : Content für die erste Stufe des Verfahrens
     */
    public abstract void initEncodeProcedure(Content content);

    /**
     * Setzt beim dekodieren den Content für die erste Stufe des Verfahrens.
     * Zudem erstellt die Methode alle Stufen des Verfahrens und
     * initialisiert diese somit
     * @param content : Content für die erste Stufe des Verfahrens
     */
    public abstract void initDecodeProcedure(Content content);

    /**
     * Erzeugt eine neue ProcedureInformation Instanz und gibt diese zurück
     * @return Erzeugte Instanz von ProcedureInformation
     */
    public abstract ProcedureInformation createProcedureInformation();

    /**
     * Erzeugt eine neue ImportPresenter Instanz und gibt diese zurück
     * @return Erzeugte Instanz von ImportPresenter
     */
    public abstract ImportPresenter createImportPresenter();

}