package kodex.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HelpTest {
  
  private static Help help;
  
  @BeforeAll
  static void init() {
    help = new Help(new Locale("DE"));
  }

  @Test
  void testHelp() {
    assertTrue(help instanceof Help);
  }

  @Test
  void testGetAnswers() {
    assertTrue(help.getAnswers().size() == 4 
        && help.getAnswers().get(3) instanceof String);
  }

  @Test
  void testGetInfo() {
    assertTrue(help.getInfo().size() == 3 && help.getInfo().get(2) instanceof String);
  }

  @Test
  void testGetQuestions() {
    assertTrue(help.getQuestions().size() == 4 
        && help.getQuestions().get(3) instanceof String);
  }
  
  @Test
  void testGetSameAmount() {
    assertTrue(help.getQuestions().size() == help.getAnswers().size());
  }
  
  @Test
  void testHelpEN() {
    Help helpEN = new Help(new Locale("EN"));
    assertTrue(helpEN instanceof Help);
  }

  @Test
  void testGetAnswersEN() {
    Help helpEN = new Help(new Locale("EN"));
    assertTrue(helpEN.getAnswers().size() == 4 
        && helpEN.getAnswers().get(3) instanceof String);
  }

  @Test
  void testGetInfoEN() {
    Help helpEN = new Help(new Locale("EN"));
    assertTrue(helpEN.getInfo().size() == 3 
        && helpEN.getInfo().get(2) instanceof String);
  }

  @Test
  void testGetQuestionsEN() {
    Help helpEN = new Help(new Locale("EN"));
    assertTrue(helpEN.getQuestions().size() == 4 
        && helpEN.getQuestions().get(3) instanceof String);
  }
  
  @Test
  void testGetSameAmountEN() {
    Help helpEN = new Help(new Locale("EN"));
    assertTrue(helpEN.getQuestions().size() == helpEN.getAnswers().size());
  }
  
  @Test
  void testHelpInvalide() {
    Help helpError = new Help(new Locale("ABC"));
    fail();
  }

}
