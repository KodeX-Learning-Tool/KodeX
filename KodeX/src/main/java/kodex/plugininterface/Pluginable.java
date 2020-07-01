package kodex.plugininterface;

/**
 * Dieses Interface muss von jedem Plugin implementiert werden und
 * beinhaltet Informationen über das Plugin
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public interface Pluginable {

    /**
     * Gibt den Namen des Plugins zurück
     * @return Name des Plugins
     * Hinweis: Unbedingt darauf achten, welche Symbole in einem String erlaubt sind
     */
    public String getPluginName();

    /**
     * Gibt die Kategorie des Plugins zurück
     * @return Kategorie des Plugins 
     * Hinweis: Die Kategorie ist auf einen String begrenzt
     */
    public String getPluginDescription();

}