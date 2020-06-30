package kodex.plugininterface;

import javafx.scene.layout.AnchorPane;
import kodex.presenter.IPresenter;
import kodex.presenter.ProcedureLayoutPresenter;

/**
 * Diese Klasse ist für die Eingabe eines Verfahrens zuständig. Sie verwaltet die 
 * entsprechende View mit welchem die Eingabe für das Verfahren erfolgt und mit 
 * welchem zwischen kodieren und dekodieren unterschieden wird. 
 * Zudem enthält sie die Funktionalität, mit welcher die Eingabe erfolgt 
 * (z.B. Datei importieren oder Text eingeben). Des Weitern ist sie dafür
 * zuständig die Eingabe zu verifizieren, aus ihr den Content für die
 * Verfahrenseingabe zu erstellen und diesen an das Verfahren zu übergebenü
 * und es somit zu initialisieren. Ein Importer für ein Verfahren muss 
 * diese Klasse erweitern.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public abstract class ImportPresenter implements IPresenter {
	
	/* Konkretes Plugin, für welches der Import Presenter erstellt wird */
	protected ProcedurePlugin plugin;

    /**
     * Konstruktor, in welchem das ProcedurePlugin übergeben wird ,um in
     * diesem das Verfahren initialisieren zu können wenn eine erfolgreiche
     * Eingabe passiert ist.
     * @param plugin : Plugin, welches diesen Importer benutzt
     */
    public ImportPresenter(ProcedurePlugin plugin) {
        this.plugin = plugin;
    }

    
    /**
     * Validiert die Eingabe eines Verfahrens welche kodiert werden soll
     * @return true, falls Eingagbe valide zum kodieren ist, ansonsten false
     */
    public abstract boolean validateEncodeImport();

    /**
     * Validiert die Eingabe eines Verfahrens welche dekodiert werden soll
     * @return true, falls Eingagbe valide zum dekodieren ist, ansonsten false
     */
    public abstract boolean validateDecodeImport();

    /**
     * Wird aufgerufen, falls Eingabe zum kodieren geladen wird und behandelt diese
     */
    public abstract void handleEncodeImport();

    /**
     * Wird aufgerufen, falls Eingabe zum dekodieren geladen wird und behandelt diese
     */
    public abstract void handleDecodeImport();

    /**
     * Setzt den LayoutPresenter für diesen Import, um auf diesem einen 
     * Wechsel zum ChainPresenter einzuleiten, wenn die Eingabe erfolgreich war
     * @param plp : zu setzender LayoutPresenter
     */
    public void setLayoutPresenter(ProcedureLayoutPresenter plp) {
        // TODO implement here
    }

    /**
     * Gibt die View zum Importieren des Contents zurück
     * @return View in Form eines AnchorPane, in dem der Content importiert
     * 		   werden kann
     */
    public abstract AnchorPane getView();

}