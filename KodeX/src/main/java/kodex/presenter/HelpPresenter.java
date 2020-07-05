package kodex.presenter;

import javafx.scene.layout.AnchorPane;
import kodex.model.Help;

/**
 * Diese Klasse verwaltet die Haupt-Presenter und hat die Referenz zu dem Fenster,
 * das angezeigt werden soll. Sie baut aus den einzelnen View-Objekten, die sie anfordert,
 * eine zusammenhängende View für das Programm und zeigt sie im Fenster an.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class HelpPresenter extends Presenter {
	
    /**
     * Die Instanz der Hilfe Klasse mit dem FAQ
     */
    private Help help;

    /**
     * Erstellt einen neuen HelpPresenter mit einer Referenz zu einem PresenterManger.
     * @param pm : Der PresenterManager
     */
    public HelpPresenter(PresenterManager pm) {
    	super(pm, "helppage");
    }
	}

}