package kodex.presenter;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ProcedurePlugin;

/**
 * Dieser Presenter ist für die Kodierungskette zuständig. Er baut aus den einzelnen View-Objekten 
 * der Kodierungsstufen eine zusammenhängende View für die Kodierungskette zusammen.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class ChainPresenter implements IPresenter {

    /**
     * Die Position der Scroll-Leiste
     */
    private double horizontalScrollPosition;

    /**
     * Das scrollbare Fenster
     */
    private ScrollPane scrollPane;

    /**
     * Das geteilte Fenster mit den Kodierungsstufen
     */
    private SplitPane splitPane;

    

    /**
     * Erstellt einen neuen ChainPresenter.
     */
    public ChainPresenter() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Schaltfläche zum Verstecken der 
     * Kodierungsstufe klickt. Minimiert den sichtbaren Ausschnitt der Kodierungsstufe.
     */
    public void handleHideChainLink() {
        // TODO implement here
    }

    /**
     * Die Methode baut sich die Kodierungskette aus den einzelnen Kodierungsstufen zusammen.
     * @param activeProcedure das aktive Kodierungsverfahren
     */
    public void createChainView(ProcedurePlugin activeProcedure) {
        // TODO implement here
    }

    /**
     * Die Methode springt in der Kette zur Kodierungstufe mit der angegebenen ID.
     * @param id : Die ID der Kodierungsstufe.
     */
    public void jumpToChainLink(int id) {
        // TODO implement here
    }

    /**
     * Die Methode setzt die Position der Teiler zwischen den Kodierungsstufen.
     * @param id : Die ID des Teilers
     * @param pos : Die Position, an die der Teiler verschoben werden soll.
     */
    public void setStageDivider(int id, double pos) {
        // TODO implement here
    }

    /**
     * Scrollt zu einer Kodierungsstufe
     * @param hValue Setzt den Scroll-Wert
     */
    private void setHorizontalScrollPosition(double hValue) {
        // TODO implement here
    }

    @Override
    public AnchorPane getView() {
        // TODO implement here
        return null;
    }

}