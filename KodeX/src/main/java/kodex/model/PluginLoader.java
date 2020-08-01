package kodex.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kodex.plugininterface.Pluginable;
import kodex.plugininterface.ProcedurePlugin;

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
  private ObservableList<ProcedurePlugin> enabledProcedurePlugins = FXCollections.observableArrayList();

  /* ServiceLoader which loads all implementations of the Pluginable class */
  private ServiceLoader<Pluginable> pluginLoader;

  /* ServiceLoader which loads all implementations of the ProcedurePlugin class */
  private ServiceLoader<ProcedurePlugin> procedureLoader;

  /** The Constant PROTECTED_SYMBOL which is a prefix for default plugins. */
  private static final String PROTECTED_SYMBOL = "#";

  /**
   * The Constant ESCAPE_CHARACTER used if a non default-plugin uses the protected
   * symbol.
   */
  private static final String ESCAPE_CHARACTER = "/";

  /** The path to enabled_plugins.txt. */
  private Path pluginListPath = new File(this.getClass().getResource("plugins").getPath() + "enabled_plugins.txt")
      .toPath();

  /** The list of default plugin names. */
  private List<String> defaultPluginNameList = new ArrayList<>();

  /** Constructor of PluginLoader class. */
  private PluginLoader() {
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

    // enables the plugins according to the enabled_plugins.txt
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
      if (!getPlugins().contains(plugin)) {
        allPlugins.add(plugin);
      }
    }

    for (ProcedurePlugin plugin : procedureLoader) {
      if (!getPlugins().contains(plugin)) {
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
    try (BufferedReader reader = Files.newBufferedReader(pluginListPath)) {
      String line;

      while ((line = reader.readLine()) != null) {
        // add the default plugins to a separate list and remove escape character if
        // necessary
        if (line.startsWith(PROTECTED_SYMBOL)) {
          line = line.replaceFirst(PROTECTED_SYMBOL, "");
          defaultPluginNameList.add(line);
        } else if (line.startsWith(ESCAPE_CHARACTER.concat(PROTECTED_SYMBOL))) {
          line = line.replaceFirst(ESCAPE_CHARACTER.concat(PROTECTED_SYMBOL), PROTECTED_SYMBOL);
        }

        // activates each plugin in the list
        for (Pluginable plugin : allPlugins) {
          if (plugin.pluginNameProperty().get().equals(line)) {
            plugin.activatedProperty().set(true);
            activatePlugin(plugin);
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Write to the plugin list text file.
   *
   * @param pluginList the plugin list to be written
   */
  private void writeToPluginList(List<String> pluginList) {
    try (BufferedWriter writer = Files.newBufferedWriter(pluginListPath)) {
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
      pluginList = Files.readAllLines(pluginListPath);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return pluginList;
  }

  private void addToPluginList(Pluginable plugin) {
    // adds the plugin to the enabled_plugins.txt
    List<String> pluginList = readPluginList();
    String pluginName = plugin.pluginNameProperty().get();

    // add escape character if necessary
    if (!defaultPluginNameList.contains(pluginName) && pluginName.startsWith(PROTECTED_SYMBOL)) {
      pluginName = ESCAPE_CHARACTER.concat(pluginName);
    }

    // check if list already contains entry
    if (!pluginList.contains(pluginName) && !defaultPluginNameList.contains(pluginName)) {
      pluginList.add(pluginName);
    }

    writeToPluginList(pluginList);
  }

  /**
   * Delete the plugin entry from the plugin list text file.
   *
   * @param plugin the plugin to be deleted
   */
  private void removeFromPluginList(Pluginable plugin) {
    List<String> pluginList = readPluginList();
    String pluginName = plugin.pluginNameProperty().get();
    if (pluginName.startsWith(PROTECTED_SYMBOL)) {
      pluginName = ESCAPE_CHARACTER.concat(pluginName);
    }
    pluginList.remove(pluginName);
    writeToPluginList(pluginList);
  }
}
