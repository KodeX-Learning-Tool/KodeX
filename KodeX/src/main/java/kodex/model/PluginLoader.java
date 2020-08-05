package kodex.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kodex.plugininterface.Pluginable;
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
  private static PluginLoader instance = new PluginLoader();

  /**
   * Retruns current instance of PluginLoader.
   *
   * @return
   */
  public static PluginLoader getInstance() {
    return instance;
  }

  /* List of all plugins */
  private ObservableList<Pluginable> allPlugins = FXCollections.observableArrayList();

  /* List of all enabled plugins */
  private ObservableList<Pluginable> enabledPlugins = FXCollections.observableArrayList();

  /* List of all procedure plugins */
  private ObservableList<ProcedurePlugin> allProcedurePlugins = FXCollections.observableArrayList();

  /* List of all enabled procedure plugins */
  private ObservableList<ProcedurePlugin> enabledProcedurePlugins = FXCollections
      .observableArrayList();

  /* ServiceLoader which loads all implementations of the Pluginable class */
  private ServiceLoader<Pluginable> pluginLoader;

  /* ServiceLoader which loads all implementations of the ProcedurePlugin class */
  private ServiceLoader<ProcedurePlugin> procedureLoader;

  private static final String PLUGIN_DIRECTORY = "plugins";

  private static final String INTERNAL_PLUGIN_DIRECTORY = "/kodex/model/" + PLUGIN_DIRECTORY;

  private static final String ENABLED_PLUGIN_LIST = "enabled_plugins.txt";

  private static final String PROTECTED_PLUGIN_LIST = "protected_plugins.txt";

  private static final String ENABLED_PLUGINS_PATH = PLUGIN_DIRECTORY + "/" + ENABLED_PLUGIN_LIST;

  private static File enabledPluginsFile;

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

    File pluginsDir;
    try {
      pluginsDir = new File(getParentPath() + fileSeparator + (PLUGIN_DIRECTORY));

      if (!pluginsDir.exists() && pluginsDir.mkdir()) {
        System.out.println("Created plugins folder.");
      }
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
      try {
        InputStream input = PluginLoader.class
            .getResourceAsStream(INTERNAL_PLUGIN_DIRECTORY + "/" + ENABLED_PLUGIN_LIST);

        // write the default enabled-plugins list to the file

        FileOutputStream fileOut = new FileOutputStream(enabledPluginsFile);
        input.transferTo(fileOut);
      } catch (FileNotFoundException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }

    load();
  }

  /**
   * Adds a plugin to the list of activated plugins.
   *
   * @param plugin : plugin which should be added
   */
  public void activatePlugin(Pluginable plugin) {
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
  public void deactivatePlugin(Pluginable plugin) {
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
  public ObservableList<Pluginable> getEnabledPlugins() {
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
  public ObservableList<Pluginable> getPlugins() {
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
  public void load() {
    pluginLoader = ServiceLoader.load(Pluginable.class);
    procedureLoader = ServiceLoader.load(ProcedurePlugin.class);

    // add loaded plugins to the respective lists
    for (Pluginable plugin : pluginLoader) {
      allPlugins.add(plugin);
    }

    for (ProcedurePlugin plugin : procedureLoader) {
      allProcedurePlugins.add(plugin);
    }

    // enables the plugins according to the enabled_plugins.txt and protected_plugins.txt
    loadProtectedPluginList();
    loadEnabledPluginList();
  }

  /**
   * Loads external plugins.
   *
   * @param file : location of plugins
   */
  public void loadExternalPlugin(File file) {
    URL[] urls = null;

    if (file.isDirectory()) { // file is a folder
      String[] files = file.list();

      if (files.length > 0) {
        File[] flist = file.listFiles(new FileFilter() {
          @Override
          public boolean accept(File file) {
            return file.getPath().toLowerCase().endsWith(".jar");
          }
        }); // only load .jar files

        urls = new URL[flist.length];

        for (int i = 0; i < flist.length; i++) {
          try {
            urls[i] = flist[i].toURI().toURL();
          } catch (MalformedURLException e) {
            throw new Error("Malformed URL");
          }
        }
      } else {
        Alert alert = new Alert(AlertType.WARNING);
        alert.titleProperty().bind(I18N.createStringBinding("alert.title.warning"));
        alert.headerTextProperty().bind(I18N.createStringBinding("alert.load.failed"));
        alert.setContentText("No plugins which can be loaded.");
        PresenterManager.showAlertDialog(alert);
      }

    } else {
      // if file is no directory
      urls = new URL[1];
      try {
        urls[0] = file.toURI().toURL();
      } catch (MalformedURLException e) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
        alert.headerTextProperty().bind(I18N.createStringBinding("alert.load.failed"));
        alert.setContentText("Plugin could not be loaded.");
        PresenterManager.showAlertDialog(alert);
      }
    }

    URLClassLoader urlLoader = new URLClassLoader(urls);

    pluginLoader = ServiceLoader.load(Pluginable.class, urlLoader);
    procedureLoader = ServiceLoader.load(ProcedurePlugin.class, urlLoader);

    boolean addedPlugin = false;
    
    for (Pluginable plugin : pluginLoader) {
      if (!allPlugins.contains(plugin)) {
        allPlugins.add(plugin);
        addedPlugin = true;
      }
    }
    
    // the plugin was already loaded
    if (!addedPlugin) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.warning"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.add.failed"));
      alert.setContentText("Plugin with the same name is already loaded.");
      PresenterManager.showAlertDialog(alert);
    }
    
    for (ProcedurePlugin plugin : procedureLoader) {
      if (!allProcedurePlugins.contains(plugin)) {
        allProcedurePlugins.add(plugin);
      }
    }
  }

  /**
   * Removes plugin from list of all plugin.
   *
   * @param plugin : plugin which should be removed
   */
  // Note: is added again when the program starts If you want to delete it, you
  // have to remove it
  // from the plugin folder
  public void removePlugin(Pluginable plugin) {
    deactivatePlugin(plugin);
    allPlugins.remove(plugin);
    enabledPlugins.remove(plugin);

    for (ProcedurePlugin p : allProcedurePlugins) {
      if (p.pluginNameProperty().get().equals(plugin.pluginNameProperty().get())
          && !enabledProcedurePlugins.contains(p)) {
        allProcedurePlugins.remove(p);
        enabledProcedurePlugins.remove(p);
        removeFromPluginList(p);
      }
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
        for (Pluginable plugin : allPlugins) {
          if (plugin.pluginNameProperty().get().equals(line)) {
            plugin.activatedProperty().set(true);
            activatePlugin(plugin);
          }
        }
      }

    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.load.failed"));
      alert.setContentText("Couldn't read enabled_plugins.txt.");
      PresenterManager.showAlertDialog(alert);
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
        for (Pluginable plugin : allPlugins) {
          if (plugin.pluginNameProperty().get().equals(line)) {
            plugin.activatedProperty().set(true);
            activatePlugin(plugin);
            defaultPluginNameList.add(line);
          }
        }
      }
    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.load.failed"));
      alert.setContentText("Couldn't read protected_plugins.txt.");
      PresenterManager.showAlertDialog(alert);
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
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.titleProperty().bind(I18N.createStringBinding("alert.load.failed"));
      alert.setContentText("Couldn't read enabled_plugins.txt.");
      PresenterManager.showAlertDialog(alert);
    }

    return pluginList;
  }

  /**
   * Adds the plugin to the enabled plugin list.
   *
   * @param plugin the plugin to be activated
   */
  private void addToPluginList(Pluginable plugin) {
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
  private void removeFromPluginList(Pluginable plugin) {
    List<String> pluginList = readPluginList();
    String pluginName = plugin.pluginNameProperty().get();

    pluginList.remove(pluginName);
    writeToPluginList(pluginList);
  }
}
