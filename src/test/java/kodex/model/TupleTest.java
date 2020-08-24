package kodex.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kodex.standardplugins.bwimageprocedure.BWImageProcedureInformation;

class TupleTest {

  @SuppressWarnings("rawtypes")
  private static Tuple stringTuple;
  @SuppressWarnings("rawtypes")
  private static Tuple mixTuple;
  @SuppressWarnings("rawtypes")
  private static Tuple nullTuple;
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @BeforeEach
  void init() {
    stringTuple = new Tuple(new String("Test"), new String("Kodex"));
    mixTuple = new Tuple(new String("PSE"), 5);
    nullTuple = new Tuple(null, null);
  }
  
  
  @Test
  void testTuple() {
    assertTrue(stringTuple != null && mixTuple != null && nullTuple != null);
  }

  @Test
  void testGetFirst() {
    assertTrue(stringTuple.getFirst().equals("Test") && mixTuple.getFirst().equals("PSE") 
        && nullTuple.getFirst() == null);
  }

  @Test
  void testGetSecond() {
    assertTrue(stringTuple.getSecond().equals("Kodex") && mixTuple.getSecond().equals(5)
        && nullTuple.getSecond() == null);
  }

  @SuppressWarnings("unchecked")
  @Test
  void testSetFirst() {
    stringTuple.setFirst("new Test");
    mixTuple.setFirst(42); //change type
    nullTuple.setFirst("not null"); //overwrite null
    assertTrue(stringTuple.getFirst().equals("new Test") && mixTuple.getFirst().equals(42) 
        && nullTuple.getFirst().equals("not null"));
    
  }

  @SuppressWarnings("unchecked")
  @Test
  void testSetSecond() {
    stringTuple.setSecond(null); //change to null
    mixTuple.setSecond(42);
    nullTuple.setSecond(new BWImageProcedureInformation());
    assertTrue(stringTuple.getSecond() == null && mixTuple.getSecond().equals(42) 
        && nullTuple.getSecond() instanceof BWImageProcedureInformation);
  }

  @Test
  void testToString() {
    boolean same = true;
    if (!stringTuple.toString().equals("Test:Kodex")) {
      same = false;
    }
    if (!mixTuple.toString().equals("PSE:5")) {
      same = false;
    }
    if (nullTuple.toString() != null) {
      same = false;
    }
    assertTrue(same);
  }
}
