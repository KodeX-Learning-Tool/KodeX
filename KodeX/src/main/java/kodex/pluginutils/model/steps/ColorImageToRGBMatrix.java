package PluginUtils.Model.Steps;

import java.util.*;

/**
 * 
 */
public class ColorImageToRGBMatrix extends ChainStep {

    /**
     * Default constructor
     */
    public ColorImageToRGBMatrix() {
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