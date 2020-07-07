package kodex.presenter;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import kodex.model.Network;
import kodex.model.validator.IPAddrValidator;
import kodex.presenter.textformatter.IPAddrFormatter;
import kodex.presenter.textformatter.PortNumFormatter;

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

    @FXML
    private TextField ipConnectTextField;

    @FXML
    private TextField portConnectTextField;

    /*
     * Indicates whether entered IP address syntax is valid.
     */
    private boolean validIPConnectTextField;

    /*
     * Indicates whether entered Port address syntax is valid.
     */
    private boolean validPortConnectTextField;

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
     * Initializes the view-object created by the FXMLLoader.
     */
    @FXML
    private void initialize() {

        /*
         * Initialize the ip connect textfield.
         */
        ipConnectTextField.setTextFormatter(IPAddrFormatter.createTextFormatter());

        // default for the text field is an empty string
        validIPConnectTextField = false;

        /*
         * Initialize the port connect textfield.
         */
        portConnectTextField.setTextFormatter(PortNumFormatter.createTextFormatter());

        // default for the text field is an empty string
        validPortConnectTextField = false;
    }

    /*
     * Sets or removes the error pseudoclass for the given control depending on the
     * given state.
     */
    private void setErrorPseudoClass(Control control, boolean state) {

        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        control.pseudoClassStateChanged(errorClass, state);
    }

    /**
     * This Method is called when the user clicks on the item to send a file. The
     * Connection is constructed with the given information and the chosen file will
     * be send.
     * 
     */
    public void handleSend() {
        
        if (!IPAddrValidator.getInstance().isValid(ipConnectTextField.getText())) {
            
            setErrorPseudoClass(ipConnectTextField, true);
        }
        
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