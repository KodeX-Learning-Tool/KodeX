package kodex.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DefaultSettingsTest {
  private static DefaultSettings instance;
  private static int port;
  private static String path;
  private static Locale locale;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    instance = DefaultSettings.getInstance();
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {
    instance.setPort(port);
    instance.setDefaultPath(path);
    instance.setSavedLanguage(locale);
  }

  @Test
  void testPort() {
    port = DefaultSettings.getPort();
    Assertions.assertThrows(ExceptionInInitializerError.class, () -> instance.setPort(133769420));
  }

  @Test
  void testDefaultPath() {
    path = instance.getDefaultPath();
    instance.setDefaultPath(null);
    assertEquals(path, instance.getDefaultPath());
    instance.setDefaultPath(path + "/Desktop");
    assertEquals(path + "/Desktop", instance.getDefaultPath());
    
  }

  @Test
  void testSavedLanguage() {
    locale = instance.getSavedLanguage();
    instance.setSavedLanguage(null);
    instance.setSavedLanguage(new Locale(""));
    assertEquals(new Locale(""), instance.getSavedLanguage());
    
  }

  @Test
  void testReset() {
    instance.reset();
    instance.setPort(1337);
    instance.setDefaultPath(path + "/Desk");
    instance.setSavedLanguage(new Locale("en"));
  }

}
