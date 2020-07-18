package kodex.presenter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import kodex.model.DefaultSettings;
import kodex.model.I18N;
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
    private Label header;

    @FXML
    private TextField ipConnectTextField;

    @FXML
    private TextField portConnectTextField;
    
    @FXML
    private Label ipConnectLbl;
    
    @FXML
    private Label portConnectLbl;

    @FXML
    private TextField ipHostTextField;

    @FXML
    private TextField portHostTextField;
    
    @FXML
    private Label ipHostLbl;
    
    @FXML
    private Label portHostLbl;

    @FXML
    private Button connectButton;

    @FXML
    private Button hostButton;

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
        
        header.textProperty().bind(I18N.createStringBinding("networkpage.header"));
        
        ipConnectLbl.textProperty().bind(I18N.createStringBinding("networkpage.connect.ip.lbl"));
        portConnectLbl.textProperty().bind(I18N.createStringBinding("networkpage.connect.port.lbl"));
        connectButton.textProperty().bind(I18N.createStringBinding("networkpage.connect.button"));
        
        ipHostLbl.textProperty().bind(I18N.createStringBinding("networkpage.host.ip.lbl"));
        portHostLbl.textProperty().bind(I18N.createStringBinding("networkpage.host.port.lbl"));
        hostButton.textProperty().bind(I18N.createStringBinding("networkpage.host.button"));
        
        /*
         * Initialize the ip connect textfield.
         */
        ipConnectTextField.setTextFormatter(IPAddrFormatter.createTextFormatter());

        /*
         * Initialize the port connect textfield.
         */
        portConnectTextField.setTextFormatter(PortNumFormatter.createTextFormatter());
    }

    // TODO create util for this?
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
     * @throws IOException faulty stream TODO try-catch or throws?
     */
    public void handleSend() throws IOException {

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
        
        /*
         * Open socket with the entered ip and port.
         */
        //TODO what if entered credentials are invalid (server doesn't exist)
        Socket socket = new Socket(ipText, Integer.parseInt(portText));

        File defaultDirectory = getDefaultDirectoryAsFile();

        // choose file to transmit
        FileChooser fileChooser = new FileChooser();

        if (defaultDirectory.exists()) {
            // set default directory if it exists
            fileChooser.setInitialDirectory(defaultDirectory);
        }

        File sendFile = fileChooser.showOpenDialog(null);
        
        if (sendFile == null) {
            setHostDisable(false);
            return;
        }

        /*
         * task for a new Thread to prevent ui freeze
         */
        Runnable sendTask = () -> {

            try (OutputStream os = socket.getOutputStream()) {
                DataOutputStream nameStream = new DataOutputStream(os);
                
                nameStream.writeUTF(sendFile.getName());

                byte[] bytes = new byte[8192];
                int count;

                InputStream is = new FileInputStream(sendFile);

                while ((count = is.read(bytes)) > 0) {
                    os.write(bytes, 0, count);
                }

                is.close();
                os.flush();
                
                socket.close();

                setHostDisable(false);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        };

        Thread clientSendThread = new Thread(sendTask);

        setHostDisable(true);

        clientSendThread.start();
    }

    /**
     * This Method is called when the user clicks on the item to host a connection.
     * The application creates a new thread and waits on the given port for a
     * connection an a file to receive and save.
     * 
     * @throws IOException TODO throw or try-catch
     */
    public void handleReceive() throws IOException {

        // TODO cancel button

        Runnable serverTask = () -> {
            /*
             * try to create a server based on the default port
             */
            try (ServerSocket serverSocket = new ServerSocket(DefaultSettings.getPort())) {

                /*
                 * display local ip address and server port after server creation was successful
                 */
                ipHostTextField.setText(InetAddress.getLocalHost().getHostAddress());
                portHostTextField.setText(Integer.toString(serverSocket.getLocalPort()));

                /*
                 * Wait for a incoming connection. This is a blocking method call that only
                 * returns when a connection has been established
                 */
                Socket clientSocket = serverSocket.accept();

                InputStream clientInputStream = clientSocket.getInputStream();

                /*
                 * A DataInputStream is created to read the file name via readUTF.
                 */
                DataInputStream dis = new DataInputStream(clientInputStream);
                String fileName = dis.readUTF();
                
                File defaultDirectory = getDefaultDirectoryAsFile();
                System.out.println("Name received:" + fileName);
                byte[] bytes = new byte[8192];

                /*
                 * Blocks until readable data has been detected or end of file.
                 */
                int count = clientInputStream.read(bytes);

                /*
                 * CompletableFuture is used, because it is not possible to open a FileChoser
                 * from a different Thread than the application thread. Therefore we have to
                 * delegate the opening of the FileChooser to the main thread.
                 */
                File saveFile = CompletableFuture.supplyAsync(() -> {

                    FileChooser fileChooser = new FileChooser();

                    if (defaultDirectory.exists()) {
                        // set default directory if it exists
                        fileChooser.setInitialDirectory(defaultDirectory);
                    }

                    fileChooser.setInitialFileName(fileName);
                    return fileChooser.showSaveDialog(null);

                }, Platform::runLater).join(); // runs on FX thread and waits for result

                if (saveFile == null) {
                    
                    setConnectDisable(false);
                    return;
                }

                // outputstream to write the received data to
                OutputStream out = new FileOutputStream(saveFile);

                do {

                    out.write(bytes, 0, count);

                } while ((count = clientInputStream.read(bytes)) > 0);

                out.close();

                // server finished and send can be re-enabled
                setConnectDisable(false);

            } catch (IOException e) {
                System.out.println("Already created this socket");
            }
        };

        Thread serverThread = new Thread(serverTask);

        // disable send button to prevent sending while having an open server
        setConnectDisable(true);

        serverThread.start();
    }

    /**
     * Create a file from the directory saved as the default path.
     */
    private File getDefaultDirectoryAsFile() {
        return new File(DefaultSettings.getInstance().getDefaultPath());
    }

    private void setConnectDisable(Boolean disable) {
        ipConnectTextField.setDisable(disable);
        portConnectTextField.setDisable(disable);
        connectButton.setDisable(disable);
    }

    private void setHostDisable(Boolean disable) {
        hostButton.setDisable(disable);
    }
}