package kodex.plugininterface;

import javafx.scene.layout.AnchorPane;
import kodex.presenter.IPresenter;

/**
 * Diese Klasse stellt einen abstrakten EditPresenter für eine konkrete Stufe dar.
 * Sie verwaltet den Edit View und ist zuständig für sämtliche, vom Benutzer 
 * durchgeführten unddurchführbaren Änderungen im Content.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public abstract class ChainLinkEditPresenter implements IPresenter {

	/* 
	 * Die Stufe, zu welcher dieser Edit gehört und dessen Content 
	 * von diesem Edit bearbeitet und verwendet wird
	 */
    protected ChainLinkPresenter chainLinkPresenter;

    /*
     * Die ID des Elementes, welches momentan markiert bzw. ausgewählt ist 
     */
    protected int markID;


    /**
     * Der Konstruktor dieses ChainLinkEditPresenters, welcher die¸ zu diesem
     * Edit gehörende, Stufe entgegennimmt und diese setzt
     * @param chainLinkPresenter : die zu diesem Edit gehörende Stufe
     */
    public ChainLinkEditPresenter(ChainLinkPresenter chainLinkPresenter) {
      this.chainLinkPresenter = chainLinkPresenter;
    }

    /**
     * Diese Methode wird aufgerufen, wenn der Benutzer eine Änderung des
     * Contents einer Stufe in Auftrag gibt und behandelt diese Änderung
     */
    public abstract void handleSubmit();
    

    /**
     * Setzt die markID dieses Edits. Dies sollte immer geschehen, wenn etwas
     * neues in der Stufe markiert wurde, um den Edit immer aktuell zu halten
     * @param id : die zu setzende ID
     */
    public void setMarkID(int id) {
        this.markID = id;
    }

    /**
     * Gibt die View zum Bearbeiten des Contents zurück
     * @return View in Form eines AnchorPane, in dem der Inhalt bearbeitet werden kann
     */
    public abstract AnchorPane getView();

}