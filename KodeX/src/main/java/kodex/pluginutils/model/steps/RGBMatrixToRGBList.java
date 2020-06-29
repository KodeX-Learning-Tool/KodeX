package PluginUtils.Model.Steps;

import kodex.plugininterface.ChainStep;

/**
 * 
 */
public class RGBMatrixToRGBList extends ChainStep {

    /**
     * Default constructor
     */
    public RGBMatrixToRGBList() {
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