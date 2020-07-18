package kodex.model;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ServiceLoader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kodex.plugininterface.Pluginable;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This class is the central class of the plugin mechanism.
 * It allows the user to add new plugins, remove old ones 
 * and to activate or deactivate plugins. 
 * As a singleton, this class allows the user to access it 
 * from anywhere and return the list of activated plugins on request.
 * 
 * @author Patrick Spiesberger
 *
 * @version 1.0properties
 * 
 */
public class PluginLoader {

	/* current instance of PluginLoader  */
    private static PluginLoader instance = new PluginLoader();

    /* List of all plugins */
    private ObservableList<Pluginable> allPlugins = FXCollections.observableArrayList();

    /* List of all enabled plugins */
    private ObservableList<Pluginable> enabledPlugins = FXCollections.observableArrayList();
    
    /* List of all enabled procedure plugins */
    private ObservableList<ProcedurePlugin> enabledProcedurePlugins = FXCollections.observableArrayList();
    
    /* List of all procedure plugins */
    private ObservableList<ProcedurePlugin> allProcedurePlugins = FXCollections.observableArrayList();
    
    private ServiceLoader<Pluginable> pluginLoader;
    
    private ServiceLoader<ProcedurePlugin> procedureLoader;

    /**
     * Constructor of PluginLoader class
     */
    private PluginLoader() {
    	load();
    }

    /**
     * Retruns current instance of PluginLoader
     * @return
     */
    public static PluginLoader getInstance() {
        return instance;
    }

    /**
     * loads only internal plugins
     */
    public void load() {
    	pluginLoader = ServiceLoader.load(Pluginable.class);
    	procedureLoader = ServiceLoader.load(ProcedurePlugin.class);
    	
    	for (Pluginable plugin : pluginLoader) {
    		allPlugins.add(plugin);
    	}
    	
    	for (ProcedurePlugin plugin : procedureLoader) {
    		allProcedurePlugins.add(plugin);
    	}
    }

    /**
     * loads external plugins
     * 
     * @param file : location of plugins
     */
    public void loadExternalPlugin(File file) {
    	URL[] urls = null;
    	
        if (file.isDirectory()) { //file is a folder
        	String[] files = file.list();
        	
        	if (files.length > 0) {
        		File[] flist = file.listFiles(new FileFilter() {
                    public boolean accept(File file) {return file.getPath().toLowerCase().endsWith(".jar");}
                }); //only load .jar files
                
        		urls = new URL[flist.length];
                
                for (int i = 0; i < flist.length; i++) {
                	try {
                		urls[i] = flist[i].toURI().toURL();
                	} catch (MalformedURLException e) {
                		throw new Error("Malformed URL");
                	}
                }
        	} else {
            	System.out.println("No external Plugins to be loaded!");
            }
        	
        } else {
        	// if file is no directory
        	urls = new URL[1];
        	try {
				urls[0] = file.toURI().toURL();
			} catch (MalformedURLException e) {
				throw new Error("Malformed URL");
			}
        }
        
        URLClassLoader urlLoader = new URLClassLoader(urls);
        
    	pluginLoader = ServiceLoader.load(Pluginable.class, urlLoader);
    	procedureLoader = ServiceLoader.load(ProcedurePlugin.class, urlLoader);
    	
    	for (Pluginable plugin : pluginLoader) {
    		allPlugins.add(plugin);
    	}
    	
    	for (ProcedurePlugin plugin : procedureLoader) {
    		allProcedurePlugins.add(plugin);
    	}	
    }
    
    /**
     * Removes plugin from list of all plugin
     * 
     * @param plugin : plugin which should be removed
     * 
     * Note: is added again when the program starts
     * If you want to delete it, you have to remove it from the plugin folder
     */
    public void removePlugin(Pluginable plugin) {
        deactivatePlugin(plugin);
        allPlugins.remove(plugin);
    }

    /**
     * Adds a plugin to the list of activated plugins
     * 
     * @param plugin : plugin which should be added
     */
    public void activatePlugin(Pluginable plugin) {
        if (!enabledPlugins.contains(plugin)) {
        	enabledPlugins.add(plugin);
        }
        
        for (ProcedurePlugin p: allProcedurePlugins) {
        	if (p.getPluginName().get().equals(plugin.getPluginName().get()) && !enabledProcedurePlugins.contains(p)) {
        		enabledProcedurePlugins.add(p);
        	}
        }
    }

    /**
     * Removes plugin from list of enabled plugins
     * 
     * @param plugin : plugin which should be removed
     */
    public void deactivatePlugin(Pluginable plugin) {
        enabledPlugins.remove(plugin);
        
        for (ProcedurePlugin p: allProcedurePlugins) {
        	if (p.getPluginName().get().equals(plugin.getPluginName().get())) {
        		enabledProcedurePlugins.remove(p);
        	}
        }
    }

    /**
     * Returns list of all plugins
     * 
     * @return list of all plugins
     */
    public ObservableList<Pluginable> getPlugins() {
        return allPlugins;
    }

    /**
     * Returns list of all enabled plugins
     * 
     * @return list of all enabled plugins
     */
    public ObservableList<Pluginable> getEnabledPlugins() {
        return enabledPlugins;
    }

    /**
     * Returns list of all enabled procedure plugins
     * 
     * @return list of all enabled procedure plugins
     */
    
    public ObservableList<ProcedurePlugin> getEnabledProcedurePlugins() {
        return enabledProcedurePlugins;
    }

}