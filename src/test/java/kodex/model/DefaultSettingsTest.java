package kodex.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import kodex.exceptions.InvalidInputException;
import kodex.exceptions.LoadingException;

class DefaultSettingsTest {
  
  private static DefaultSettings settings;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    settings = DefaultSettings.getInstance();
  }
  
  @AfterAll
  static void cleanUp() throws Exception {
    settings.reset();
  }
  
  @Test
  void testGetInstance() throws LoadingException, InvalidInputException {
    assertTrue(DefaultSettings.getInstance() != null);
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
  void testSetPort() throws InvalidInputException {
    settings.setPort(12345);
    assertTrue(DefaultSettings.getPort() == 12345);
  }

  @Test
  void testSetUnvalidPort() throws InvalidInputException {
    settings.setPort(12345);
    Exception exception = assertThrows(InvalidInputException.class, () -> settings.setPort(123456));
    String expectedMessage = "Port is not valid";
    String actualMessage = exception.getMessage();
    
    assertTrue(actualMessage.contains(expectedMessage) && DefaultSettings.getPort() == 12345);
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
