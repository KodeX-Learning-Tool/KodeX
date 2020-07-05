package kodex.presenter;

import kodex.model.Network;

/**
 * This Presenter is responsible for the network page. From the network page it
 * is possible to send and receive files over the local network.
 * 
 * @author Leonhard Kraft
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class NetworkPresenter extends Presenter {

    /**
     * 
     */
    private Network network; // TODO do we need a reference to the a network object?

    /**
     * Creates a new NetworkPresenter with a reference to the PresenterManger for
     * its superclass.
     * 
     * @param presenterManager The PresenterManager.
     */
    public NetworkPresenter(PresenterManager presenterManager) {
        super(presenterManager, "networkpage");
    }

    /**
     * This Method is called when the user clicks on the item to send a file. The
     * Connection is constructed with the given information and the chosen file will
     * be send.
     * 
     */
    public void handleSend() {
        // TODO implement here
    }

    /**
     * This Method is called when the user clicks on the item to host a connection.
     * The application creates a new thread and waits on the given port for a
     * connection an a file to receive and save.
     */
    public void handleReceive() {
        // TODO implement here
    }
}