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
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import kodex.model.DefaultSettings;
import kodex.model.I18N;
import kodex.model.validator.IpAddrValidator;
import kodex.model.validator.PortNumValidator;
import kodex.presenter.textformatter.IPAddrFormatter;
import kodex.presenter.textformatter.PortNumFormatter;

/**
 * This Presenter is responsible for the network page. From the network page it is possible to send
 * and receive files over the local network.
 *
 * @author Leonhard Kraft
 * @author Yannick Neubert
 * @version 1.0
 */
public class NetworkPresenter extends Presenter {

  @FXML private Label header;

  @FXML private TextField ipConnectTextField;

  @FXML private TextField portConnectTextField;

  @FXML private Label ipConnectLbl;

  @FXML private Label portConnectLbl;

  @FXML private TextField ipHostTextField;

  @FXML private TextField portHostTextField;

  @FXML private Label ipHostLbl;

  @FXML private Label portHostLbl;

  @FXML private Button connectButton;

  @FXML private Button hostButton;

  @FXML private Button cancelButton;

  /** The server socket which can be accessed for closing. */
  private ServerSocket serverSocket;
  
  /** The socket used to send the file. */
  private Socket sendSocket;

  /** The boolean receiving indicates whether the program is currently waiting for a connection. */
  private boolean receiving;

  /** The boolean canceling indicates that closing the connection prematurely was intended. */
  private boolean canceling;

  /**
   * Creates a new NetworkPresenter with a reference to the PresenterManger for its superclass.
   *
   * @param presenterManager The PresenterManager.
   */
  public NetworkPresenter(PresenterManager presenterManager) {
    super(presenterManager, "networkpage");

    // disable cancel button at startup
    setConnectCancelDisable(true);
  }

  /** Create a file from the directory saved as the default path. */
  private File getDefaultDirectoryAsFile() {
    return new File(DefaultSettings.getInstance().getDefaultPath());
  }

  /**
   * This Method is called when the user clicks on the item to host a connection. The application
   * creates a new thread and waits on the given port for a connection an a file to receive and
   * save.
   *
   * @throws IOException TODO throw or try-catch
   */
  @FXML
  private void handleReceive() {
    Runnable serverTask =
        () -> {
          /*
           * try to create a server based on the default port
           */
          try {
            // doesn't use try-with-resources because otherwise serverSocket is not accessible
            serverSocket = new ServerSocket(DefaultSettings.getPort());

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
            File saveFile =
                CompletableFuture.supplyAsync(
                    () -> {
                      FileChooser fileChooser = new FileChooser();

                      if (defaultDirectory.exists()) {
                        // set default directory if it exists
                        fileChooser.setInitialDirectory(defaultDirectory);
                      }

                      fileChooser.setInitialFileName(fileName);
                      return fileChooser.showSaveDialog(null);
                    },
                    Platform::runLater)
                    .join(); // runs on FX thread and waits for result

            if (saveFile == null) {

              setConnectDisable(false);
              setHostDisable(false);
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
            setHostDisable(false);

            // clear host text fields after successfully receiving a file
            clearHostTextFields();

          } catch (SocketException e) {
            // only show this if closing the connection was not intended
            if (!canceling) {
              // Alerts and Dialogs can only be shown on JavaFX Application Thread
              Platform.runLater(() -> showErrorDialog("Failed receiving file."));
              return;
            }

            // clear text when receiving is canceled
            clearHostTextFields();

          } catch (IOException e) {
            // the error message probably has to be adjusted,
            // since it seems most errors regarding sockets will be caught by one above
            System.out.println("Already created this socket.");
          } finally {
            try {
              serverSocket.close();
              receiving = false;
            } catch (IOException e) {
              Platform.runLater(() -> showErrorDialog("Couldn't close the connection."));
            }
          }
        };

    Thread serverThread = new Thread(serverTask);

    // disable send button to prevent sending while having an open server
    setConnectDisable(true);
    // also disables host since it doesn't make sense to open multiple listener connections
    setHostDisable(true);

    serverThread.start();
  }

  private void clearHostTextFields() {
    ipHostTextField.clear();
    portHostTextField.clear();
  }

  /**
   * Shows an information window.
   *
   * @param message the message
   */
  private void showInformationDialog(String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.titleProperty().bind(I18N.createStringBinding("alert.title.information"));
    alert.headerTextProperty().bind(I18N.createStringBinding("alert.header.network"));
    alert.setContentText(message);
    PresenterManager.showAlertDialog(alert);
  }

  /**
   * Shows an error window.
   *
   * @param message the message
   */
  private void showErrorDialog(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
    alert.headerTextProperty().bind(I18N.createStringBinding("alert.header.network"));
    alert.setContentText(message);
    PresenterManager.showAlertDialog(alert);
  }

  /**
   * This Method is called when the user clicks on the Cancel button. The connection is closed
   * forcebly.
   */
  @FXML
  private void handleCancel() {

    // only close socket if it is waiting
    if (receiving) {
      try {
        canceling = true;
        serverSocket.close();
        showInformationDialog("Connection closed.");
        setConnectDisable(false);
        setHostDisable(false);
        canceling = false;
      } catch (IOException e) {
        showErrorDialog("Couldn't close the connection.");
      }
    }
  }

  /**
   * This Method is called when the user clicks on the item to send a file. The Connection is
   * constructed with the given information and the chosen file will be send.
   *
   * @throws IOException faulty stream TODO try-catch or throws?
   */
  @FXML
  public void handleSend() throws IOException {

    boolean invalid = false;

    String ipText = ipConnectTextField.getText();
    String portText = portConnectTextField.getText();

    if (!IpAddrValidator.getInstance().isValid(ipText)) {

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
    // TODO what if entered credentials are invalid (server doesn't exist)
    try {
      sendSocket = new Socket(ipText, Integer.parseInt(portText));
      
    } catch (UnknownHostException e) {
      
      //host name is not valid to connect to
      setErrorPseudoClass(ipConnectTextField, true);
      return;
    }
    

    File defaultDirectory = getDefaultDirectoryAsFile();

    // choose file to transmit
    FileChooser fileChooser = new FileChooser();

    if (defaultDirectory.exists()) {
      // set default directory if it exists
      fileChooser.setInitialDirectory(defaultDirectory);
    }

    File sendFile = PresenterManager.showOpenFileChooser(fileChooser);

    if (sendFile == null) {
      setHostDisable(false);
      return;
    }

    /*
     * task for a new Thread to prevent ui freeze
     */
    Runnable sendTask =
        () -> {
          try (OutputStream os = sendSocket.getOutputStream()) {
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

            sendSocket.close();

            setHostDisable(false);

          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        };

    Thread clientSendThread = new Thread(sendTask);

    setHostDisable(true);

    clientSendThread.start();
    
    sendSocket = null;
  }

  /** Initializes the view-object created by the FXMLLoader. */
  @FXML
  private void initialize() {

    header.textProperty().bind(I18N.createStringBinding("networkpage.header"));

    ipConnectLbl.textProperty().bind(I18N.createStringBinding("networkpage.connect.ip.lbl"));
    ipConnectTextField.promptTextProperty()
        .bind(I18N.createStringBinding("networkpage.connect.ip.prompt"));
    portConnectLbl.textProperty().bind(I18N.createStringBinding("networkpage.connect.port.lbl"));
    portConnectTextField.promptTextProperty()
        .bind(I18N.createStringBinding("networkpage.connect.port.prompt"));
    connectButton.textProperty().bind(I18N.createStringBinding("networkpage.connect.button"));

    ipHostLbl.textProperty().bind(I18N.createStringBinding("networkpage.host.ip.lbl"));
    portHostLbl.textProperty().bind(I18N.createStringBinding("networkpage.host.port.lbl"));
    hostButton.textProperty().bind(I18N.createStringBinding("networkpage.host.button"));
    cancelButton.textProperty().bind(I18N.createStringBinding("networkpage.host.button.cancel"));

    /*
     * Initialize the ip connect textfield.
     */
    ipConnectTextField.setTextFormatter(IPAddrFormatter.createTextFormatter());

    /*
     * Initialize the port connect textfield.
     */
    portConnectTextField.setTextFormatter(PortNumFormatter.createTextFormatter());
  }

  private void setConnectDisable(boolean disable) {
    ipConnectTextField.setDisable(disable);
    portConnectTextField.setDisable(disable);
    connectButton.setDisable(disable);

    receiving = disable;
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

  private void setConnectCancelDisable(boolean disable) {
    cancelButton.setDisable(disable);
  }

  private void setHostDisable(boolean disable) {

    setConnectCancelDisable(!disable);
    hostButton.setDisable(disable);
  }
}
