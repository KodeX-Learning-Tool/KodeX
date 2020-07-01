package kodex.plugininterface;

import javafx.scene.layout.AnchorPane;
import kodex.presenter.IPresenter;

/**
 * Diese Klasse stellt einen abstrakten HeaderPresenter für eine konkrete Stufe dar. 
 * Sie sorgt dafür, dass der Header, welcher zum Content der Stufe gehört, angezeigt werden kann.

 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public abstract class ChainLinkHeaderPresenter implements IPresenter {

    /*
     * Der Content der, zum HeaderPresenter zugehörigen, Stufe, welcher den 
     * darzustellenden Header enthält
     */
    protected Content content;

    /**
     * Der Konstruktor dieses ChainLinkHeaderPresenters, welcher die¸ zu diesem
     * Header gehörende, Stufe entgegennimmt und diese setzt
     * @param content : der Content der Stufe zu welcher dieser Header Presenter gehört
     */
    public ChainLinkHeaderPresenter(Content content) {
        this.content = content;
    }

    /**
     * Setzt den Content für diesen HeaderPresenter
     * @param content : Content, welcher für den Header Presenter gesetzt werden soll
     */
    public void setContent(Content content) {
    	this.content = content;
    }

    /**
     * Gibt die View zum Anzeigen des Headers des Contents zurück
     * @return View in Form eines AnchorPane, in dem der Header angeziegt wird
     */
    public abstract AnchorPane getView();

}