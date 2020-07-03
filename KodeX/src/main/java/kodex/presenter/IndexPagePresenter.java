package kodex.presenter;

import java.util.LinkedList;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import kodex.model.Filter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * Dieser Presenter ist für die Startseite zuständig. Die View der Startseite zeigt 
 * die verfügbaren Kodierungsverfahren an, wobei diese Auswahl durch Filter und Sucheingabe 
 * angepasst werden kann. Wird ein Verfahren ausgewählt so wird zum ProcedureLayoutPresenter gewechselt.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class IndexPagePresenter extends Presenter {

    /**
     * Die Liste der anzuzeigenden Kodierungsverfahren.
     */
    private LinkedList<ProcedurePlugin> shownProcedures;



    /**
     * Erstellt einen neuen IndexPagePresenter mit Referenzen zu einem PresenterManger 
     * und einer PresenterFactory.
     * @param pm : Die Referenz zum PresenterManger, um zu anderen Fenstern wechseln zu können.
     * @param pf : Die Referenz zur PresenterFactory mit welcher der Presenter andere Presenter erstellen kann.
     */
    public void IndexPagePresenter(PresenterManager pm, PresenterFactory pf) {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf ein Kodierungsverfahren klickt. 
     * Wechselt den Presenter zum ProcedureLayoutPresenter, übergibt diesem das gewählte ProcedurePlugin
     * und benachrichtigt den PresenterManger.
     */
    public void handleProcedureSelected() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer die Suche nach Kodierungsverfahren bestätigt. 
     * Sie sorgt dafür das die zutreffenden Kodierungsverfahren ausgewählt werden.
     */
    public void handleSearch() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer die Kodierungsverfahren filtert. 
     * Sie sorgt dafür das die zutreffenden Kodierungsverfahren ausgewählt werden.
     */
    public void handleFilter() {
        // TODO implement here
    }

    /**
     * Durchsucht die Liste der Kodierungsverfahren
     * @param searchTerm : Das Suchwort
     */
    private void searchProcedures(String searchTerm) {
        // TODO implement here
    }

    /**
     * Filtert die Liste der kodierungsverfahren
     * @param filter : Der ausgewählte Filter
     */
    private void filterProcedures(Filter filter) {
        // TODO implement here
    }

    /**
     * Erstellt die Schaltflächen für die Kodierungsverfahren
     */
    private void createProcedureButtons() {
        // TODO implement here
    }

    /**
     * Fügt die Schaltflächen für die Kodierungsverfahren in die View ein
     * @param procedure : Eine konkrete Schaltfläche
     */
    private void addProcedureButton(Button procedure) {
        // TODO implement here
    }

	@Override
	public AnchorPane getView() {
		// TODO Auto-generated method stub
		return null;
	}

}