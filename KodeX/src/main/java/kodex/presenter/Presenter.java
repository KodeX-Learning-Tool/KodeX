package kodex.presenter;

/**
 * Diese abstrakte Klasse ist die Überklasse für alle Haupt-Presenter.
 * Haupt-Presenter sind genau die Presenter, die den gesamten Fensterinhalt einnehmen 
 * zusätzlich des Seitenmenüs.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public abstract class Presenter implements IPresenter {
	
    /**
     * Default constructor
     */
    public Presenter() {
    }
	
    /**
     * Die Referenz zum PresenterManger, um zu anderen Fenstern wechseln zu
	 * können.
     */
    protected PresenterManager presenterManager;

    /**
     * Erstellt einen neuen Haupt-Presenter mit einer Referenz zu dem PresenterManager.
     * @param pm : Die Referenz zum PresenterManger, um zu anderen Fenstern wechseln zu können.
     */
    public Presenter(PresenterManager pm) {
        // TODO implement here
    }
}