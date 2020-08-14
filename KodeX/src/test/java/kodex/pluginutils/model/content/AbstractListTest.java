package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AbstractListTest {
  private static int size = 10;
  private static BitList bitlist;
  private static LinkedList<Integer> intlist;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    bitlist = new BitList();
    
    intlist = new LinkedList<Integer>();
    for (int i = 0; i < size; i++) {
      intlist.add((int) Math.random());
    }
  }

  @Test
  void testSetAndGet() {
    assertThrows(IllegalArgumentException.class, () -> bitlist.setList(null));
    bitlist.setList(intlist);
    assertEquals(intlist.size(), bitlist.size());
    for (int i = 0; i < size; i++) {
      assertEquals(intlist.get(i).intValue(), bitlist.get(i).intValue());
      assertEquals(intlist.get(i).intValue(), bitlist.getList().get(i).intValue());
    }
    bitlist.add(1);
    bitlist.add(0);
    assertEquals(1, bitlist.get(size));
    assertEquals(0, bitlist.get(size + 1));
    assertThrows(IndexOutOfBoundsException.class, () -> bitlist.get(size + 2));
  }

}
