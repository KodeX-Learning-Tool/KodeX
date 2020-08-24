package kodex.plugininterface;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class PluginTest {
  
  static class TestPlugin extends Plugin {

    private SimpleStringProperty pluginName;

    @Override
    public BooleanProperty activatedProperty() {
      // unimplemented
      return null;
    }

    @Override
    public StringProperty pluginDescriptionProperty() {
      // unimplemented
      return null;
    }

    @Override
    public StringProperty pluginNameProperty() {
      return pluginName;
    }
    
    public void setPluginNameProperty(String name) {
      this.pluginName = new SimpleStringProperty(name);
    }
    
  }
  
  private static TestPlugin plugin1;
  private static TestPlugin plugin2;
  private static TestPlugin plugin3;
  
  
  @BeforeAll
  static void setUp() {
    plugin1 = new TestPlugin();
    plugin1.setPluginNameProperty("SomeName");
    
    plugin2 = new TestPlugin();
    plugin2.setPluginNameProperty("SomeName");
    
    plugin3 = new TestPlugin();
    plugin3.setPluginNameProperty("OtherName");
  }

  @Test
  void equalsSelf() {
    
    assertEquals(plugin1, plugin1);
  }
  
  @Test
  void equalsOtherSameName() {
    assertEquals(plugin1, plugin2);
  }
  
  @Test
  void equalsOtherDifferentName() {
    assertNotEquals(plugin1, plugin3);
  }
  
  @Test
  void equalsOtherObject() {
    assertNotEquals(plugin1, new Object());
  }
  
  @Test
  void equalsNull() {
    assertNotEquals(plugin1, null);
  }
  
  @Test
  void hashSame() {
    assertEquals(plugin1.hashCode(), plugin1.hashCode());
  }
  
  @Test
  void hashOtherSameName() {
    assertEquals(plugin1.hashCode(), plugin2.hashCode());
  }
  
  @Test
  void hashOtherDifferentName() {
    assertNotEquals(plugin1.hashCode(), plugin3.hashCode());
  }
}
