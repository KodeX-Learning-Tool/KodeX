package kodex.model;

import java.util.*;

/**
 * 
 */
public class PluginLoader {

    /**
     * Default constructor
     */
    public PluginLoader() {
    }

    /**
     * 
     */
    private static PluginLoader instance;

    /**
     * 
     */
    private List<Pluginable> allPlugins;

    /**
     * 
     */
    private List<Pluginable> enabledPlugins;



    /**
     * 
     */
    private void PluginLoader() {
        // TODO implement here
    }

    /**
     * @return
     */
    public static PluginLoader getInstance() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void load() {
        // TODO implement here
    }

    /**
     * @param file
     */
    public void addPlugin(File file) {
        // TODO implement here
    }

    /**
     * @param plugin
     */
    public void removePlugin(Pluginable plugin) {
        // TODO implement here
    }

    /**
     * @param plugin
     */
    public void activatePlugin(Pluginable plugin) {
        // TODO implement here
    }

    /**
     * @param plugin
     */
    public void deactivatePlugin(Pluginable plugin) {
        // TODO implement here
    }

    /**
     * @return
     */
    public List<Pluginable> getPlugins() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<Pluginable> getEnabledPlugins() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<ProcedurePlugin> getEnabledProcedurePlugins() {
        // TODO implement here
        return null;
    }

}