package kodex.plugininterface;

import java.util.*;

/**
 * 
 */
public abstract class ChainLinkPresenter implements IPresenter {

    /**
     * Default constructor
     */
    public ChainLinkPresenter() {
    }

    /**
     * 
     */
    protected ChainLinkPresenter next;

    /**
     * 
     */
    protected ChainLinkPresenter previous;

    /**
     * 
     */
    protected Content content;

    /**
     * 
     */
    protected ChainStep previousStep;

    /**
     * 
     */
    protected ChainStep nextStep;





    /**
     * @param previous 
     * @param previousStep 
     * @param nextStep
     */
    public void ChainLinkPresenter(ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
        // TODO implement here
    }

    /**
     * @return
     */
    public ChainLinkEditPresenter getChainLinkEditPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Pane getChainLinkHeaderView() {
        // TODO implement here
        return null;
    }

    /**
     * @param content
     */
    public void setContent(Content content) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Content getContent() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void updateChain() {
        // TODO implement here
    }

    /**
     * 
     */
    public void updateNextChainLink() {
        // TODO implement here
    }

    /**
     * 
     */
    public void updatePrevChainLink() {
        // TODO implement here
    }

    /**
     * @param id
     */
    protected void mark(int id) {
        // TODO implement here
    }

    /**
     * 
     */
    public void handleMark() {
        // TODO implement here
    }

    /**
     * @param id
     */
    public void markPrev(int id) {
        // TODO implement here
    }

    /**
     * @param id
     */
    public void markNext(int id) {
        // TODO implement here
    }

    /**
     * @param next
     */
    public void setNext(ChainLinkPresenter next) {
        // TODO implement here
    }

    /**
     * @return
     */
    public ChainLinkPresenter getNext() {
        // TODO implement here
        return null;
    }

    /**
     * @param prev
     */
    public void setPrev(ChainLinkPresenter prev) {
        // TODO implement here
    }

    /**
     * @return
     */
    public ChainLinkPresenter getPrev() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Image getSymbol() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void handleExport() {
        // TODO implement here
    }

    /**
     * @return
     */
    public AnchorPane getView() {
        // TODO implement here
        return null;
    }

}