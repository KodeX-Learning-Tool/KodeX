package kodex.presenter.textformatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javafx.css.PseudoClass;
import javafx.scene.control.Control;
import javafx.scene.control.TextFormatter;

/**
 * Tests for the IPAddrFormatter class.
 *
 * @author "Leonhard Kraft"
 */
class IPAddrFormatterTest {

  private static TextFormatter<String> formatter;

  @BeforeAll
  static void setUp() {
    formatter = IPAddrFormatter.createTextFormatter();
  }

  @Test
  void testNoChange() {

    TextFormatter.Change changeMock = Mockito.mock(TextFormatter.Change.class);

    Mockito.when(changeMock.isContentChange()).thenReturn(false);

    assertEquals(formatter.getFilter().apply(changeMock), changeMock);
  }
  
  @Test
  void testNoMatch1() {

    TextFormatter.Change changeMock = Mockito.mock(TextFormatter.Change.class);

    Mockito.when(changeMock.isContentChange()).thenReturn(true);
    Mockito.when(changeMock.getControlNewText()).thenReturn("NotAnIPAddressFormat");

    assertNull(formatter.getFilter().apply(changeMock));
  }
  
  @Test
  void testNoMatch2() {
    
    TextFormatter.Change change = new TextFormatter.Change(null, null, 0, 0, null);
    TextFormatter.Change changeMock = Mockito.mock(TextFormatter.Change.class);

    Mockito.when(changeMock.isContentChange()).thenReturn(true);
    Mockito.when(changeMock.getControlNewText()).thenReturn("0.0.a");

    assertNull(formatter.getFilter().apply(changeMock));
  }
  
  @Test
  void testFormatterMatch() {

    TextFormatter.Change changeMock = Mockito.mock(TextFormatter.Change.class);

    Mockito.when(changeMock.isContentChange()).thenReturn(true);
    Mockito.when(changeMock.getControlNewText()).thenReturn("1.1.1.1");
    
    Control controlMock = Mockito.mock(Control.class);
    
    Mockito.when(changeMock.getControl()).thenReturn(controlMock);
    
    assertEquals(formatter.getFilter().apply(changeMock), changeMock);
    Mockito.verify(controlMock).pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), false);
    
  }
}
