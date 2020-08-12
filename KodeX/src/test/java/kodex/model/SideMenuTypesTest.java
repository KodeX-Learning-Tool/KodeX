package kodex.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SideMenuTypesTest {

  @Test
  void testSideMenuTypes() {
    assertTrue(SideMenuTypes.EXTENDED_MENU.toString().equals("sidemenu")
        && SideMenuTypes.MINI_MENU.toString().equals("minisidemenu"));
  }

}
