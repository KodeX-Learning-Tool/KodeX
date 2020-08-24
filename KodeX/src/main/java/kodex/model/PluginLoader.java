package kodex.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.apache.commons.io.FileUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.LoadingException;
import kodex.plugininterface.Plugin;
import kodex.plugininterface.ProcedurePlugin;
import kodex.presenter.PresenterManager;

/**
 * This class is the central class of the plugin mechanism. It allows the user
 * to add new plugins, remove old ones and to activate or deactivate plugins. As
 * a singleton, this class allows the user to access it from anywhere and return
 * the list of activated plugins on request.
 *
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * @version 1.0
 */
public class PluginLoader {

  /* current instance of PluginLoader */
  private static PluginLoader instance;

  /**
   * Returns current instance of PluginLoader.
   *
   * @return
   */
  public static PluginLoader getInstance() {
    
    if (instance == null) {
      instance = new PluginLoader();
    }
    return instance;
  }

  /* List of all plugins */
  private ObservableList<Plugin> allPlugins = FXCollections.observableArrayList();

  /* List of all enabled plugins */
  private ObservableList<Plugin> enabledPlugins = FXCollections.observableArrayList();

  /* List of all procedure plugins */
  private ObservableList<ProcedurePlugin> allProcedurePlugins = FXCollections.observableArrayList();

  /* List of all enabled procedure plugins */
  private ObservableList<ProcedurePlugin> enabledProcedurePlugins = FXCollections
      .observableArrayList();

  /* ServiceLoader which loads all implementations of the Pluginable class */
  private ServiceLoader<Plugin> pluginLoader;

  /* ServiceLoader which loads all implementations of the ProcedurePlugin class */
  private ServiceLoader<ProcedurePlugin> procedureLoader;

  private static final String PLUGIN_DIRECTORY = "plugins";

  private static final String INTERNAL_PLUGIN_DIRECTORY = "/kodex/model/" + PLUGIN_DIRECTORY;

  private static final String ENABLED_PLUGIN_LIST = "enabled_plugins.txt";

  private static final String PROTECTED_PLUGIN_LIST = "protected_plugins.txt";

  private static final String ENABLED_PLUGINS_PATH = PLUGIN_DIRECTORY + "/" + ENABLED_PLUGIN_LIST;

  private static File enabledPluginsFile;

  /** The plugins folder. */
  private File pluginsDir;
  
  /** The list of default plugin names. */
  private List<String> defaultPluginNameList = new ArrayList<>();

  /**
   * Gets the current parent directory of the running jar.
   *
   * @return the parent path
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  private static String getParentPath() throws UnsupportedEncodingException {
    URL url = PluginLoader.class.getProtectionDomain().getCodeSource().getLocation();
    String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
    return new File(jarPath).getParentFile().getPath();
  }

  /** Constructor of PluginLoader class. */
  private PluginLoader() {
    String fileSeparator = System.getProperty("file.separator");

    
    try {
      pluginsDir = new File(getParentPath() + fileSeparator + (PLUGIN_DIRECTORY));

      checkPluginsDir();
      
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    try {
      enabledPluginsFile = new File(getParentPath() + fileSeparator + ENABLED_PLUGINS_PATH);
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (!enabledPluginsFile.exists()) {
      copyInternalPluginListToFolder();
    }

    loadInternalPlugins();
    loadExternalPlugins();
    
    // enables the plugins according to enabled_plugins.txt
    loadEnabledPluginList();
  }

  /**
   * Adds a plugin to the list of activated plugins.
   *
   * @param plugin : plugin which should be added
   */
  public void activatePlugin(Plugin plugin) {
    plugin.activatedProperty().set(true);
    if (!enabledPlugins.contains(plugin)) {
      enabledPlugins.add(plugin);
      addToPluginList(plugin);
    }

    // add the equivalent procedure plugin if it exists
    for (ProcedurePlugin p : allProcedurePlugins) {
      if (p.pluginNameProperty().get().equals(plugin.pluginNameProperty().get())
          && !enabledProcedurePlugins.contains(p)) {
        enabledProcedurePlugins.add(p);
      }
    }
  }

  /**
   * Removes plugin from list of enabled plugins.
   *
   * @param plugin : plugin which should be removed
   */
  public void deactivatePlugin(Plugin plugin) {
    plugin.activatedProperty().set(false);
    enabledPlugins.remove(plugin);

    // removes the plugin from enabled_plugins.txt
    removeFromPluginList(plugin);

    // removes the equivalent procedure plugin if it exists
    for (ProcedurePlugin p : allProcedurePlugins) {
      if (p.pluginNameProperty().get().equals(plugin.pluginNameProperty().get())) {
        enabledProcedurePlugins.remove(p);
      }
    }
  }

  /**
   * Returns list of all enabled plugins.
   *
   * @return ObservableList of all enabled plugins
   */
  public ObservableList<Plugin> getEnabledPlugins() {
    return enabledPlugins;
  }

  /**
   * Returns list of all enabled procedure plugins.
   *
   * @return ObservableList of all enabled procedure plugins
   */
  public ObservableList<ProcedurePlugin> getEnabledProcedurePlugins() {
    return enabledProcedurePlugins;
  }

  /**
   * Returns list of all plugins.
   *
   * @return ObservableList of all plugins
   */
  public ObservableList<Plugin> getPlugins() {
    return allPlugins;
  }

  /**
   * Gets the default plugin names.
   *
   * @return the names of the default plugins
   */
  public List<String> getDefaultPluginNames() {
    return defaultPluginNameList;
  }

  /** Loads only internal plugins. */
  public void loadInternalPlugins() {
    pluginLoader = ServiceLoader.load(Plugin.class);
    procedureLoader = ServiceLoader.load(ProcedurePlugin.class);

    // add loaded plugins to the respective lists
    for (Plugin plugin : pluginLoader) {
      allPlugins.add(plugin);
    }

    for (ProcedurePlugin plugin : procedureLoader) {
      allProcedurePlugins.add(plugin);
    }

    // enables the plugins according to protected_plugins.txt
    loadProtectedPluginList();
  }
  
  /**
   * Import an external plugin.
   *
   * @param externalPlugin : the external plugin to be imported
   * @throws LoadingException Thrown when new plugin couldn't be copied into plugins folder.
   */
  public void importPlugin(File externalPlugin) throws LoadingException {
    // copy to plugin folder
    try {
      FileUtils.copyFileToDirectory(externalPlugin, pluginsDir);

      loadExternalPlugins();
    } catch (IOException e) {
      
      throw new LoadingException(
          AlertType.ERROR,
          I18N.get("alert.title.error"),
          I18N.get("alert.copy.failed"),
          "Couldn't copy plugin to plugins folder.",
          e);
    }
  }
  
  private boolean checkPluginsDir() {
    
    if (!pluginsDir.exists()) {
      if (pluginsDir.mkdir()) {
        return true;
      } else {

        PresenterManager.showAlertDialog(
            AlertType.ERROR,
            I18N.get("alert.title.error"),
            I18N.get("alert.load.failed"),
            "Plugin folder doesn't exists and failed to be created."
            + "Check if the location is protected.");

        return false;
      }
    }
    
    if (!pluginsDir.isDirectory()) {
      
      PresenterManager.showAlertDialog(AlertType.WARNING, I18N.get("alert.title.error"),
          I18N.get("alert.load.failed"), "Check if the plugin folder exists.");
      
      return false;
    }
    
    return true;
  }

  /**
   * Loads external plugins in the plugins folder.
   */
  public void loadExternalPlugins() {
    
    URL[] urls = null;
    
    if (!checkPluginsDir()) {
      return;
    }

    // load all plugins from the plugin folder

    // only load .jar files
    File[] flist = pluginsDir.listFiles(file -> file.getPath().toLowerCase().endsWith(".jar"));

    if (flist.length > 0) {
      
      urls = new URL[flist.length];

      for (int i = 0; i < flist.length; i++) {
        
        try {
          urls[i] = flist[i].toURI().toURL();
          
        } catch (MalformedURLException e) {
          
          throw new Error("Malformed URL");
        }
      }
    } else {
      //no plugins in folder
      return;
    }

    URLClassLoader urlLoader = new URLClassLoader(urls);

    pluginLoader = ServiceLoader.load(Plugin.class, urlLoader);
    procedureLoader = ServiceLoader.load(ProcedurePlugin.class, urlLoader);
    
    boolean addedPlugin = false;

    for (Plugin plugin : pluginLoader) {
      
      if (!allPlugins.contains(plugin)) {
        
        allPlugins.add(plugin);
        addedPlugin = true;
      }
    }
    
    // the plugin was already loaded
    if (!addedPlugin) {
      
      PresenterManager.showAlertDialog(AlertType.WARNING, I18N.get("alert.title.warning"),
          I18N.get("alert.load.failed"), "Either the file is not a valid plugin " 
              + "or a plugin with the same name is already loaded.");
      
      return;
    }
    
    for (ProcedurePlugin plugin : procedureLoader) {
      
      if (!allProcedurePlugins.contains(plugin)) {
        
        allProcedurePlugins.add(plugin);
      }
    }
    
    // closes the class loader to free the files up for deleting
    try {
      urlLoader.close();
      
    } catch (IOException e) {
      
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * Removes plugin from list of all plugin.
   *
   * @param plugin : plugin which should be removed
   * @throws LoadingException Thrown when the plugin couldn't be removed.
   */
  // Note: is added again when the program starts If you want to delete it, you
  // have to remove it
  // from the plugin folder
  public void removePlugin(Plugin plugin) throws LoadingException {
    
    deactivatePlugin(plugin);
    
    allPlugins.remove(plugin);
    enabledPlugins.remove(plugin);
    
    String pluginName = plugin.pluginNameProperty().get();
    
    for (ProcedurePlugin p : allProcedurePlugins) {
      
      if (p.pluginNameProperty().get().equals(pluginName)
          && !enabledProcedurePlugins.contains(p)) {
        
        // allProcedurePlugins.remove(p);
        enabledProcedurePlugins.remove(p);
        removeFromPluginList(p);
      }
    }
        
    try {
      
      if (!Files.deleteIfExists(Paths.get(pluginsDir.getPath(), pluginName + ".jar"))) {
        
        throw new LoadingException(
            AlertType.ERROR,
            I18N.get("alert.title.error"),
            I18N.get("alert.delete.failed"),
            "Couldn't delete " + pluginName 
                + ".jar. Make sure that the name"
                + "of the .jar file matches the internal plugin name.");
      }
    } catch (IOException e) {
      
      throw new LoadingException(
          AlertType.ERROR,
          I18N.get("alert.title.error"),
          I18N.get("alert.delete.failed"),
          "Couldn't access " + pluginName + ".jar");
    }
  }

  /**
   * Loads the list of enabled plugins and activates them. It also adds the
   * default plugins to the default plugin list.
   */
  private void loadEnabledPluginList() {
    
    try (BufferedReader reader = Files
        .newBufferedReader(Paths.get(getParentPath(), ENABLED_PLUGINS_PATH))) {
      
      String line;

      while ((line = reader.readLine()) != null) {
        
        // add the default plugins to a separate list and remove escape character if
        // necessary
        // activates each plugin in the list
        for (Plugin plugin : allPlugins) {
          
          if (plugin.pluginNameProperty().get().equals(line)) {
            
            activatePlugin(plugin);
          }
        }
      }

    } catch (IOException e) {
      
      PresenterManager.showAlertDialog(AlertType.ERROR, I18N.get("alert.title.error"),
          I18N.get("alert.load.failed"), "Couldn't read enabled_plugins.txt.");
    }
  }

  /**
   * Loads the list of protected plugins and activates them. These default plugins
   * also get added to the default plugin list.
   */
  private void loadProtectedPluginList() {
    
    try (InputStreamReader in = new InputStreamReader(PluginLoader.class
            .getResourceAsStream(INTERNAL_PLUGIN_DIRECTORY + "/" + PROTECTED_PLUGIN_LIST), "UTF-8");
        BufferedReader reader = new BufferedReader(in)) {
      
      String line;

      while ((line = reader.readLine()) != null) {
        
        for (Plugin plugin : allPlugins) {
          
          if (plugin.pluginNameProperty().get().equals(line)) {
            
            plugin.activatedProperty().set(true);
            activatePlugin(plugin);
            defaultPluginNameList.add(line);
          }
        }
      }
    } catch (IOException e) {
      
      PresenterManager.showAlertDialog(AlertType.ERROR, I18N.get("alert.title.error"),
          I18N.get("alert.load.failed"), "Couldn't read protected_plugins.txt.");
    }
  }

  /**
   * Write to the plugin list text file.
   *
   * @param pluginList the plugin list to be written
   */
  private void writeToPluginList(List<String> pluginList) {
    
    try (BufferedWriter writer = Files
        .newBufferedWriter(Paths.get(getParentPath(), ENABLED_PLUGINS_PATH))) {
      
      for (String pluginName : pluginList) {
        writer.write(pluginName);
        writer.newLine();
      }
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the plugin list text file.
   *
   * @return the list of enabled plugins
   */
  private List<String> readPluginList() {
    
    List<String> pluginList = new ArrayList<>();

    try {
      pluginList = Files.readAllLines(Paths.get(getParentPath(), ENABLED_PLUGINS_PATH));
      
    } catch (IOException e) {
      
      PresenterManager.showAlertDialog(AlertType.ERROR, I18N.get("alert.title.error"),
          I18N.get("alert.load.failed"), "Couldn't read enabled_plugins.txt.");
    }

    return pluginList;
  }

  /**
   * Adds the plugin to the enabled plugin list.
   *
   * @param plugin the plugin to be activated
   */
  private void addToPluginList(Plugin plugin) {
    
    // adds the plugin to the enabled_plugins.txt
    List<String> pluginList = readPluginList();
    String pluginName = plugin.pluginNameProperty().get();

    // check if list already contains entry
    if (!pluginList.contains(pluginName) && !defaultPluginNameList.contains(pluginName)) {
      pluginList.add(pluginName);
    }

    writeToPluginList(pluginList);
  }

  /**
   * Delete the plugin entry from the enabled plugin list text file.
   *
   * @param plugin the plugin to be deleted
   */
  private void removeFromPluginList(Plugin plugin) {
    List<String> pluginList = readPluginList();
    String pluginName = plugin.pluginNameProperty().get();

    pluginList.remove(pluginName);
    writeToPluginList(pluginList);
  }
  
  /**
   * Resets active plugins so that only the default plugins are active.
   */
  public void resetActivePlugins() {
    
    if (!enabledPluginsFile.exists()) {
      
      copyInternalPluginListToFolder();
    } else {
      
      if (enabledPluginsFile.delete()) {
        
        copyInternalPluginListToFolder();
      } else {
        
        PresenterManager.showAlertDialog(AlertType.ERROR, I18N.get("alert.title.error"),
            I18N.get("alert.load.failed"), "Couldn't reset the active plugins "
                + "because the program failed deleting enabled_plugins.txt.");
        return;
      }
    }
    
    // deactivates all non-default plugins
    for (Plugin plugin : allPlugins) {
      if (!defaultPluginNameList.contains(plugin.pluginNameProperty().get())) {
        deactivatePlugin(plugin);
      }
    }
  }
  
  /**
   * Copy internal plugin list to the plugins folder.
   */
  private void copyInternalPluginListToFolder() {
    
    try (InputStream input = PluginLoader.class.getResourceAsStream(INTERNAL_PLUGIN_DIRECTORY
        + "/" + ENABLED_PLUGIN_LIST);
        
        FileOutputStream fileOut = new FileOutputStream(enabledPluginsFile)) {

      // write the default enabled-plugins list to the file
      input.transferTo(fileOut);
      
    } catch (FileNotFoundException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
  }
}
