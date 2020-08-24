package kodex.standardplugins.rle.model.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import kodex.model.Tuple;
import kodex.pluginutils.model.content.LetterString;
import kodex.pluginutils.model.content.TupleString;

/**
 * This test class is responsible for the RLEStep class.
 * 
 * @author Raimon Gramlich
 */
class RLEStepTest {
  
  private RLEStep rleStep;
  private LetterString letterString;
  private TupleString tupleString;
  
  private Tuple<String, Integer>[] testTuples;
  private String testString;
  
  @BeforeEach
  void setUp() {
    rleStep = new RLEStep();
    letterString = new LetterString();
    tupleString = new TupleString();
    
    // initialize tuples
    ArrayList<Tuple<String, Integer>> tuples = new ArrayList<>();
    tuples.add(new Tuple<>("H", 1));
    tuples.add(new Tuple<>("E", 3));
    tuples.add(new Tuple<>("L", 3));
    tuples.add(new Tuple<>("O", 2));
    testTuples = tuples.toArray((Tuple<String, Integer>[]) new Tuple[tuples.size()]);
    
    testString = "HEEELLLOO";
  }
  
  @AfterEach
  void tearDown() {
    rleStep = null;
    letterString = null;
    tupleString = null;
    
    testTuples = null;
    testString = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.model.steps.RLEStep
   * #decode(kodex.plugininterface.Content, kodex.plugininterface.Content)}.
   */
  @Test
  void testDecode() {
    tupleString.setTuples(testTuples);
    
    rleStep.decode(tupleString, letterString);
    
    assertEquals(testString, letterString.getString());
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.model.steps.RLEStep
   * #encode(kodex.plugininterface.Content, kodex.plugininterface.Content)}.
   */
  @Test
  void testEncode() {
    letterString.setString(testString);
    
    rleStep.encode(letterString, tupleString);
    
    TupleString exptectedTupleString = new TupleString();
    exptectedTupleString.setTuples(testTuples);
    
    // check if the tuples are equal
    assertEquals(exptectedTupleString, tupleString);
  }

}
