package kodex.pluginutils.model.content;

import java.io.File;

import kodex.plugininterface.Content;

public class LetterString extends Content<String> {

    private String letterString;

    @Override
    protected File toFile() {
        // TODO Auto-generated method stub
        return null;
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
     * @return the letterString
     */
    public String getLetterString() {
        return letterString;
    }

    /**
     * @param letterString the letterString to set
     */
    public void setLetterString(String letterString) {
        this.letterString = letterString;
    }
}
