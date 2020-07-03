package kodex.presenter;

import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * Diese Klasse verwaltet eine Reihe an Presenter, die für das Kodierungsverfahren als Ganzes notwendig sind.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class ProcedureLayoutPresenter extends Presenter {
	
    /**
     * Das aktive Kodierungsverfahren
     */
    private ProcedurePlugin activeProcedure;

    /**
     * Der Presenter für die Übersicht zu dem Verfahren
     */
    private OverviewPresenter overviewPresenter;

    /**
     * 
     */
    private IPresenter activePresenter;

    /**
     * Der aktive EditPresenter zu dem Verfahren
     */
    private ChainLinkEditPresenter editPresenter;



    /**
     * Erstellt einen neuen ProcedureLayoutPresenter mit Referenzen zu dem PresenterManager 
     * und einem ProcedurePlugin.
     * @param pm : Referenz zum PresenterManger, um zu anderen Fenstern wechseln zu können.
     * @param activePlugin : Das aktive Verfahren, welches ausgewählt wurde.
     */
    public ProcedureLayoutPresenter(PresenterManager pm, ProcedurePlugin activePlugin) {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Schaltfläche zum Schließen des Bearbeitungsfenster klickt. 
     * Schließt das Bearbeitungs-fenster.
     */
    public void handleCloseEditWindow() {
        // TODO implement here
    }

    /**
     * Die Methode erstellt einen neuen ChainPresenter und setzt diesen als aktiven Presenter.
     */
    public void switchToChainPresenter() {
        // TODO implement here
    }

    /**
     * Die Methode setzt den EditPresenter zum Anzeigen des Bearbeitungs-fensters.
     * @param editPresenter : Der Presenter für das Bearbeitungsfenster.
     */
    public void setEditPresenter(ChainLinkEditPresenter editPresenter) {
        // TODO implement here
    }

    /**
     * Die Methode gibt die View für das Bearbeitungsfenster frei, sodass es nicht mehr angezeigt wird.
     */
    public void clearEditView() {
        // TODO implement here
    }

	@Override
	public AnchorPane getView() {
		// TODO Auto-generated method stub
		return null;
	}

}