package PluginUtils.Model.Steps;

import java.util.*;

/**
 * 
 */
public class TextToBinaryString extends ChainStep {

    /**
     * Default constructor
     */
    public TextToBinaryString() {
    }

    /**
     * @param left 
     * @param right
     */
    public abstract void encode(Content left, Content right);

    /**
     * @param right 
     * @param left
     */
    public abstract void decode(Content right, Content left);

}