package kodex.presenter;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.model.SideMenuTypes;

/**
 * Diese Klasse verwaltet die Haupt-Presenter und hat die Referenz zu dem Fenster, 
 * das angezeigt werden soll. Sie baut aus den einzelnen View-Objekten, die sie anfordert, 
 * eine zusammenhängende View für das Programm und zeigt sie im Fenster an.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class PresenterManager {

    /**
     * Die root Stage des Programms
     */
    private Stage rootStage;

    /**
     * Der momentan aktive Haupt-Presenter
     */
    private Presenter currentPresenter;

    /**
     * Der Seitenmenü Presenter
     */
    private SideMenuPresenter sideMenuPresenter;

    /**
     * Die Hauptsicht ohne das Seitenmenü
     */
    private AnchorPane mainView;

    /**
     * Die Sicht des Seitenmenüs
     */
    private AnchorPane sideMenuView;

    /**
     * Das Layout für den Presenter Manager
     */
    private BorderPane rootLayout;


    
    /**
     * Erstellt einen neuen PresenterManager mit einer Referenz zu der Stage;
     * das Fenster, in dem alles angezeigt wird
     * @param rootStage : Die root Stage des Programms
     */
    public PresenterManager(Stage rootStage) {
        // TODO implement here
    }

    /**
     * Die Methode holt sich das aktuelle View-Objekt vom momentan aktiven Presenter.
     */
    public void updateMainView() {
        // TODO implement here
    }

    /**
     * Die Methode setzt den benötigten Seitenmenütyp im SideMenuPresenter
     * und holt sich das aktuelle View-Objekt vom SideMenuPresenter.
     * @param type : Der Typ des Seitenmenüs
     */
    public void updateSideMenuView(SideMenuTypes type) {
        // TODO implement here
    }

    /**
     * Die Methode setzt den Presenter der als nächstes anzeigen darf.
     * @param newPresenter : Der nächste Haupt-Presenter, der seine View anzeigen soll.
     */
    public void updatePresenter(Presenter newPresenter) {
        // TODO implement here
    }

    /**
     * Die Methode setzt den SideMenuPresenter.
     * @param smp : Der Presenter für das Seitenmenü.
     */
    public void setSideMenuPresenter(SideMenuPresenter smp) {
        // TODO implement here
    }
}