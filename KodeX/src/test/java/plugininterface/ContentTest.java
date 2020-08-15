package plugininterface;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import kodex.plugininterface.Content;

class ContentTest {
  
  private static Content mockContent;

  @Test
  void setAndGetHeader() {
    mockContent = Mockito.mock(Content.class, Mockito.CALLS_REAL_METHODS);
    
    Map<String, String> header = new HashMap<>();
    header.put("key", "value");
    
    mockContent.setHeader(header);
    
    assertEquals(mockContent.getHeader(), header);
  }
  
  @Test
  void setAndGetHeaderChanges() {
    mockContent = Mockito.mock(Content.class, Mockito.CALLS_REAL_METHODS);
    
    Map<String, String> header = new HashMap<>();
    header.put("key", "value");
    
    mockContent.setHeader(header);
    
    Map<String, String> oldheader = mockContent.getHeader();
    
    Map<String, String> otherHeader = new HashMap<>();
    header.put("key2", "value2");
    
    mockContent.setHeader(otherHeader);
    
    assertNotEquals(mockContent.getHeader(), oldheader);
  }
}
