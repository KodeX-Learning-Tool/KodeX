package PluginUtils.Model.Steps;

import java.util.*;

/**
 * 
 */
public class RGBListToRGBByteList extends ChainStep {

    /**
     * Default constructor
     */
    public RGBListToRGBByteList() {
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