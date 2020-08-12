package kodex.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DefaultSettingsTest {
  
  private static DefaultSettings settings;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    settings = DefaultSettings.getInstance();
  }

  @Test
  void testGetInstance() {
    assertTrue(DefaultSettings.getInstance() != null);
  }

  @Test
  void testReset() {
    settings.reset();
    assertTrue(DefaultSettings.getPort() == 1337 
        && settings.getSavedLanguage().equals(new Locale("DE"))
        && settings.getDefaultPath().equals(System.getProperty("user.home")));
  }

  @Test
  void testSetDefaultPath() {
    settings.setDefaultPath("Test/Kodex/Plugin");
    assertTrue(settings.getDefaultPath().equals("Test/Kodex/Plugin"));
  }
  
  @Test
  void testSetNullPath() {
    settings.setDefaultPath(null);
    assertTrue(settings.getDefaultPath().equals(System.getProperty("user.home")));
  }

  @Test
  void testSetPort() {
    settings.setPort(12345);
    assertTrue(DefaultSettings.getPort() == 12345);
  }

  @Test
  @Disabled //Exception caused by Alert - manually tested 
  void testSetUnvalidPort() {
    settings.setPort(12345);
    settings.setPort(123456);
    assertTrue(DefaultSettings.getPort() == 12345);
  }
  
  @Test
  void testSetSavedLanguage() {
    settings.setSavedLanguage(new Locale("EN"));
    assertTrue(settings.getSavedLanguage().equals(new Locale("EN")));
  }
  
  @Test
  void testSetNotAvaliableLanguage() {
    settings.setSavedLanguage(new Locale("FR"));
    assertTrue(settings.getSavedLanguage().equals(new Locale("FR")));
  }
  
  @Test
  void testSetInvalidLanguage() {
    settings.setSavedLanguage(new Locale("ABC"));
    assertTrue(settings.getSavedLanguage().equals(new Locale("ABC")));
  }
  
  @Test
  void testSetNoLanguage() {
    settings.setSavedLanguage(new Locale(""));
    assertTrue(settings.getSavedLanguage().equals(new Locale("")));
  }

}
