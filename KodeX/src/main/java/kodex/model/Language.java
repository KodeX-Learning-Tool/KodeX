package kodex.model;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class provides the values for text in the GUI in the selected language.
 * A presenter calls getMessage with the desired message and gets the result
 * back in the correct language. Because it is needed everywhere, this class is
 * a singleton.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public class Language {

	/* current instance of language */
	private static Language instance;

	/* list with all avaliable languages */
	private static List<Locale> languages = new ArrayList<>();

	/* locale of current language */
	private Locale language;

	/* File with current language */
	private File currentLanguageFile;

	/* bundle of files with current language */
	private static ResourceBundle rb;

	/**
	 * Constructor of the Language class. However, since this class is a singleton,
	 * only one instance can be created
	 */
	private Language(Locale language) {
		ClassLoader loader = null;
		
		try {
			loader = new URLClassLoader(new URL[] { new File(getClass().getResource("languages").getPath()).toURI().toURL() });
		} catch (MalformedURLException e) {
			System.out.println("Path can not be loaded");
		}
		if (loader != null) {
			rb = ResourceBundle.getBundle("Languages", language, loader);
		} else {
			rb = ResourceBundle.getBundle("Languages", Locale.GERMAN);
		}
		this.language = language;
		
		languages.add(language);
	}

	/**
	 * Provides the singleton instance of this class. The presenter can request the
	 * desired message directly from this
	 * 
	 * @return instance of this class
	 */
	public static Language getInstance() {
		if (instance == null) {
			instance = new Language(new Locale("DE"));
		}
		return instance;
	}

	/**
	 * Sets the singleton instance of this class.
	 * 
	 * @param locale : corresponding language
	 */
	public static void setInstance(Locale locale) {
		instance = new Language(locale);
	}

	/**
	 * returns a list of al available languages
	 * 
	 * @return list of type Locale
	 */
	public List<Locale> getLanguageList() {
		return languages;
	}

	/**
	 * This method is called with the desired message in order to receive it in the
	 * selected language
	 * 
	 * @param message : Message that is required in the corresponding language
	 * @return message in the current language
	 */
	public String getMessage(String message) {
		if (instance == null) {
			instance = new Language(new Locale("DE"));
		}
		return rb.getString(message);
	}

	/**
	 * Returns information about current language
	 * 
	 * @return : locale of current language (e.g. Deutsch = DE)
	 */
	public Locale getLanguageInfo() {
		if (language == null) {
			instance = new Language(new Locale("DE"));
		}
		return language;
	}

	/**
	 * refreshes the list of current language
	 */
	public void refreshList() {
		File file = new File("src/main/resources/Languages");
		URL[] urls = null;

		String[] files = file.list();

		if (files.length > 0) {
			File[] flist = file.listFiles(new FileFilter() {
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
			String fileName = "";
			String[] parts;
			for (URL url : urls) {
				try {
					fileName = Paths.get(new URI(url.toString()).getPath()).getFileName().toString();
				} catch (URISyntaxException e) {
					System.out.println("Error during reading files");
					return;
				}
				parts = fileName.split("_");
				if (parts.length == 2) {
					languages.add(new Locale("parts[1]"));
				} else {
					System.out.print("Please check name of File");
				}
			}
		}
	}

}