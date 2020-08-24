package kodex.standardplugins.rle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.exceptions.AlertWindowException;
import kodex.model.Tuple;
import kodex.pluginutils.model.content.LetterString;
import kodex.pluginutils.model.content.TupleString;
import kodex.pluginutils.presenter.chainlink.LetterStringPresenter;
import kodex.pluginutils.presenter.chainlink.TupleStringPresenter;
import kodex.presenter.PresenterManager;
import kodex.presenter.ProcedureLayoutPresenter;
import kodex.standardplugins.bwimageprocedure.BWImageProcedureInformation;
import kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin;
import kodex.standardplugins.rle.presenter.RLEImportPresenter;

/**
 * This test class is responsible for the TextRLEProcedurePlugin class.
 * 
 * @author Raimon Gramlich
 */
@ExtendWith(ApplicationExtension.class)
class TextRLEProcedurePluginTest {
  
  private TextRLEProcedurePlugin textRLEProcedurePlugin;
  private PresenterManager pm;
  private ProcedureLayoutPresenter procedureLayoutPresenter;
  
  @Start
  void setUp(Stage stage) throws Exception {
    pm = Mockito.mock(PresenterManager.class);
    
    textRLEProcedurePlugin = new TextRLEProcedurePlugin();
    textRLEProcedurePlugin.createImportPresenter();
    
    procedureLayoutPresenter = new ProcedureLayoutPresenter(pm, textRLEProcedurePlugin);

    BorderPane layout = new BorderPane();
    layout.setCenter(procedureLayoutPresenter.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }
  
  @AfterEach
  void tearDown() throws Exception {
    textRLEProcedurePlugin = null;
    pm = null;
    procedureLayoutPresenter = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.TextRLEProcedurePlugin
   * #pluginDescriptionProperty()}.
   */
  @Test
  void testPluginDescriptionProperty() {
    String expectedDescription = "Komprimierungsverfahren";

    assertEquals(expectedDescription, textRLEProcedurePlugin.pluginDescriptionProperty().get());
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.TextRLEProcedurePlugin#pluginNameProperty()}.
   */
  @Test
  void testPluginNameProperty() {
    String expectedPluginName = "Lauflängencodierung";
    
    assertEquals(expectedPluginName, textRLEProcedurePlugin.pluginNameProperty().get());
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.TextRLEProcedurePlugin
   * #createImportPresenter()}.
   */
  @Test
  void testCreateImportPresenter() {
    assertTrue(textRLEProcedurePlugin.createImportPresenter()
        instanceof RLEImportPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.TextRLEProcedurePlugin
   * #createProcedureInformation()}.
   */
  @Test
  void testCreateProcedureInformation() {
    assertTrue(textRLEProcedurePlugin.createProcedureInformation()
        instanceof TextRLEProcedureInformation);
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.TextRLEProcedurePlugin#getChainHead()}.
   */
  @Test
  void testGetChainHead() {
    assertTrue(textRLEProcedurePlugin.getChainHead() instanceof LetterStringPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.TextRLEProcedurePlugin#getChainTail()}.
   */
  @Test
  void testGetChainTail() {
    assertTrue(textRLEProcedurePlugin.getChainTail() instanceof TupleStringPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.TextRLEProcedurePlugin
   * #initDecodeProcedure(kodex.plugininterface.Content)}.
   * @throws AlertWindowException if an error happens when calculating the chain
   */
  @Test
  void testInitDecodeProcedure() throws AlertWindowException {
    // initialize content   
    ArrayList<Tuple<String, Integer>> tuples = new ArrayList<>();
    tuples.add(new Tuple<>("H", 1));
    tuples.add(new Tuple<>("E", 1));
    tuples.add(new Tuple<>("L", 2));
    tuples.add(new Tuple<>("O", 1));
    
    TupleString content = new TupleString();
    content.setTuples(tuples.toArray((Tuple<String, Integer>[]) new Tuple[tuples.size()]));
    
    // call method to be tested
    textRLEProcedurePlugin.initDecodeProcedure(content);
    
    // get content of last calculated chain link
    LetterString letterString = (LetterString) (textRLEProcedurePlugin.getChainHead().getContent());
    
    String expectedString = "HELLO";
    
    // check if procedure is initialized
    assertEquals(expectedString, letterString.getString());
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.TextRLEProcedurePlugin
   * #initEncodeProcedure(kodex.plugininterface.Content)}.
   * @throws AlertWindowException if an error happens when calculating the chain
   */
  @Test
  void testInitEncodeProcedure() throws AlertWindowException {
    // initialize content
    LetterString content = new LetterString();
    content.setString("HELLO");
    
    // call method to be tested
    textRLEProcedurePlugin.initEncodeProcedure(content);    
    
    ArrayList<Tuple<String, Integer>> tuples = new ArrayList<>();
    tuples.add(new Tuple<>("H", 1));
    tuples.add(new Tuple<>("E", 1));
    tuples.add(new Tuple<>("L", 2));
    tuples.add(new Tuple<>("O", 1));
    Tuple<String, Integer>[] expectedTuples = tuples
        .toArray((Tuple<String, Integer>[]) new Tuple[tuples.size()]);
    
    // get content of last calculated chain link
    TupleString actualTupleString =
        (TupleString) (textRLEProcedurePlugin.getChainTail().getContent());
    
    // check if procedure is initialized
    TupleString expectedTupleString = new TupleString();
    expectedTupleString.setTuples(expectedTuples);

    assertEquals(expectedTupleString, actualTupleString);
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#hashCode()}.
   */
  @Test
  void testHashCode() {
    int hash = 7 * 17 + textRLEProcedurePlugin.pluginNameProperty().get().hashCode();
    assertEquals(hash, textRLEProcedurePlugin.hashCode());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#activatedProperty()}.
   */
  @Test
  void testActivatedProperty() {
    textRLEProcedurePlugin.activatedProperty().set(true);
    assertTrue(textRLEProcedurePlugin.activatedProperty().get());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#equals(java.lang.Object)}.
   */
  @Test
  void testEqualsObject() {
    TextRLEProcedurePlugin secondInstance = new TextRLEProcedurePlugin();
    
    assertEquals(textRLEProcedurePlugin, secondInstance);
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin
   * #compareTo(kodex.plugininterface.ProcedurePlugin)}.
   */
  @Test
  void testCompareTo() {
    int expectedResult = -7;
    BWImageProcedurePlugin secondProcedurePlugin = Mockito.mock(BWImageProcedurePlugin.class);
    BWImageProcedureInformation bwInformation = Mockito.mock(BWImageProcedureInformation.class);
    Mockito.when(bwInformation.getName()).thenReturn("Schwarz & Weiß - Bild");
    Mockito.when(secondProcedurePlugin.createProcedureInformation())
          .thenReturn(bwInformation);

    assertEquals(expectedResult, textRLEProcedurePlugin.compareTo(secondProcedurePlugin));
  }

}
