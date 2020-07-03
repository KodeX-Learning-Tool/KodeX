package kodex.presenter;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import kodex.plugininterface.ChainLinkPresenter;

/**
 * Dieser Presenter ist für die View der Übersichtsleiste zuständig. Durch klicken auf die 
 * Elemente der Leiste, kann man in der Kodierungskette an die jeweilige Stelle springen.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class OverviewPresenter implements IPresenter {
	
    /**
     * Der ChainPresenter für die Sicht der Kette.
     */
    private ChainPresenter chainPresenter;

    /**
     * Die HBox, die die Sicht der Übersicht beinhaltet.
     */
    private HBox overview;



    /**
     * Erstellt einen neuen ChainPresenter
     */
    public OverviewPresenter() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf eine Schaltfläche in der Übersichtsleiste klickt. 
     * Springt in der Kodierungskette zu der ausgewählten Stufe.
     */
    public void handleJumpTo() {
        // TODO implement here
    }

    /**
     * Die Methode setzt den ChainPresenter.
     * @param chainPresenter : Der Presenter für die Kodierungskette.
     */
    public void setChainPresenter(ChainPresenter chainPresenter) {
        // TODO implement here
    }

    /**
     * Die Methode baut die Schaltflächen für die Übersicht aus den Kodierungsstufen zusammenn
     * @param firstChainLink : Der Presenter für die erste Stufe des Verfahrens.
     */
    public void createOverviewItems(ChainLinkPresenter firstChainLink) {
        // TODO implement here
    }

    /**
     * Fügt eine neue Schaltfläche in die Übersicht ein.
     * @param chainLink : Die konkrete Schaltfläche.
     */
    private void addOverviewItem(Button chainLink) {
        // TODO implement here
    }

    /**
     * Markiert eine Schaltfläche der Übersicht.
     * @param id : Die ID der Schaltfläche.
     */
    private void emphasizeButton(int id) {
        // TODO implement here
    }

    @Override
    public AnchorPane getView() {
        // TODO implement here
        return null;
    }

}