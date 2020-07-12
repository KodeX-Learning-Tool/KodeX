package kodex.presenter;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import kodex.model.DefaultSettings;
import kodex.model.validator.IPAddrValidator;
import kodex.model.validator.PortNumValidator;
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

    @FXML
    private TextField ipConnectTextField;

    @FXML
    private TextField portConnectTextField;
    
    @FXML
    private TextField ipHostTextField;

    @FXML
    private TextField portHostTextField;

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

        /*
         * Initialize the port connect textfield.
         */
        portConnectTextField.setTextFormatter(PortNumFormatter.createTextFormatter());
    }
    
    //TODO create util for this?
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
        
        boolean invalid = false;
        
        String ipText = ipConnectTextField.getText();
        String portText = portConnectTextField.getText();
        
        if (!IPAddrValidator.getInstance().isValid(ipText)) {
            
            setErrorPseudoClass(ipConnectTextField, true);
            invalid = true;
        }
        
        if (!PortNumValidator.getInstance().isValid(portText)) {
            
            setErrorPseudoClass(portConnectTextField, true);
            invalid = true;
        }
        
        if (invalid) {
            return;
        }
        
        Socket socket = null;
        
        // TODO implement here
        try {
            socket = new Socket(ipText, Integer.parseInt(portText));
            
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        FileChooser fileChooser = new FileChooser();
        File sendFile = fileChooser.showOpenDialog(null);
        
        //TODO: Send File to Socket
    }

    /**
     * This Method is called when the user clicks on the item to host a connection.
     * The application creates a new thread and waits on the given port for a
     * connection an a file to receive and save.
     */
    public void handleReceive() {
        // TODO disable send
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(DefaultSettings.getPort());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //TODO break here beacuse of null
        }
        
        ipHostTextField.setText(serverSocket.getInetAddress().getHostAddress());
        portHostTextField.setText(Integer.toString(serverSocket.getLocalPort()));
        
        //TODO wait for connection in new Thread
    }
}