package kodex.model;

/**
 * The class is an enumeration of names for different views of the side menu.
 *
 * @author StarUML
 * @version 1.0
 */
public enum SideMenuTypes {
  MINI_MENU("minisidemenu"), // minimized side menu
  EXTENDED_MENU("sidemenu"); // standard side menu

  private final String fxmlName;

  SideMenuTypes(String fxmlName) {
    this.fxmlName = fxmlName;
  }

  @Override
  public String toString() {
    return fxmlName;
  }
}
