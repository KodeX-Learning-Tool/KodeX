package edu.kit.scc.git.kodex.pluginutils.model.content;

import java.io.File;

import edu.kit.scc.git.kodex.plugininterface.Content;

public class LetterString extends Content<String> {

  private String letterString;

  /**
   * Gets the letter string.
   *
   * @return the letterString
   */
  public String getLetterString() {
    return letterString;
  }

  @Override
  public boolean isValid(String input) {
    boolean valid = input.chars().allMatch(Character::isLetter);

    if (valid) {
      this.letterString = input;
    }

    return valid;
  }

  /**
   * Sets the letter string.
   *
   * @param letterString the letterString to set
   */
  public void setLetterString(String letterString) {
    this.letterString = letterString;
  }

  @Override
  public void export(File file) {
    // TODO Auto-generated method stub
  }
  
}
