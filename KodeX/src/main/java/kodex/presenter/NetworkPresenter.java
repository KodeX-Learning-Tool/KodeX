package kodex.presenter;

import javafx.scene.layout.AnchorPane;
import kodex.model.Network;

/**
 * Dieser Presenter ist für die Netzwerkseite zuständig. Von der Netzwerkseite aus lassen sich 
 * Dateien über das lokale Netzwerk verschicken und Empfangen.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class NetworkPresenter extends Presenter {

    /**
     * 
     */
    private Network network;



    /**
     * Erstellt einen neuen NetworkPresenter mit einer Referenz zu dem PresenterManger.
     * @param pm : Der PresenterManager
     */
    public void NetworkPresenter(PresenterManager pm) {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Senden Schalt- fläche klickt. 
     * Die Verbindug wird mit den angegebenen Informationen aufgebaut und die Datei versendet.
     */
    public void handleSend() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Senden Schaltfläche klickt. 
     * Das Programm wartet auf dem angegebenen Port auf eine eingehende Verbindung 
     * und eine Datei, die es empfangen kann.
     */
    public void handleReceive() {
        // TODO implement here
    }

	@Override
	public AnchorPane getView() {
		// TODO Auto-generated method stub
		return null;
	}

}