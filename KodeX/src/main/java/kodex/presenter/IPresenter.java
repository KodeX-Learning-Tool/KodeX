package kodex.presenter;

import javafx.scene.layout.AnchorPane;

/**
 * Dies ist das Interface, welches alle Presenter implentieren müssen.
 * Ein Presenter erstellt die View-Objekte, die in der GUI angezeigt werden
 * und ist zudem für die Interaktion mit dem Benutzer zuständig.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public interface IPresenter {

    /**
     * Die Methode gibt ein View-Objekt zurück, dass in den Gesamt-View eingebaut wird.
     * @return AnchorPane : Das View-Objekt zum Einbauen
     */
    public AnchorPane getView();

}