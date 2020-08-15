package kodex.presenter;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

  private void closeResources(Closeable... closeables) {

    for (Closeable closeable : closeables) {

      try {
        closeable.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
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
            serverSocket = new ServerSocket(DefaultSettings.getPort());

          } catch (IOException e) {
            Platform.runLater(
                () ->
                    showErrorDialog(
                        "alert.input.invalid",
                        "networkpage.error.port.used",
                        DefaultSettings.getPort()));
            endHosting();
            return;
          }

          /*
           * display local ip address and server port after server creation was successful
           */
          try {
            ipHostTextField.setText(InetAddress.getLocalHost().getHostAddress());

          } catch (UnknownHostException e1) {
            e1.printStackTrace();
            ipHostTextField.setText(I18N.get("networkpage.error.localhost"));

            closeResources(serverSocket);

            return;
          }

          portHostTextField.setText(Integer.toString(serverSocket.getLocalPort()));

          /*
           * Wait for a incoming connection. This is a blocking method call that only
           * returns when a connection has been established
           */
          Socket clientSocket = null;

          try {
            clientSocket = serverSocket.accept();

          } catch (SocketException e) {

            // only show this if closing the connection was not intended
            if (!canceling) {
              // Alerts and Dialogs can only be shown on JavaFX Application Thread
              Platform.runLater(
                  () ->
                      showErrorDialog(
                          "alert.connection.failed", "networkpage.error.connection.build"));
              closeResources(serverSocket);
              endHosting();
              return;
            }

            // clear text when receiving is canceled
            clearHostTextFields();
            closeResources(serverSocket);
            endHosting();
            return;

          } catch (IOException e) {
            // the error message probably has to be adjusted,
            // since it seems most errors regarding sockets will be caught by one above
            System.err.println("Already created this socket.");
            closeResources(serverSocket);
            endHosting();
            return;
          }

          if (clientSocket == null) {
            Platform.runLater(
                () ->
                    showErrorDialog(
                        "alert.connection.failed", "networkpage.error.connection.build"));
            closeResources(serverSocket);
            endHosting();
            return;
          }

          InputStream clientInputStream;

          try {
            clientInputStream = clientSocket.getInputStream();

          } catch (IOException e1) {
            e1.printStackTrace();
            Platform.runLater(
                () ->
                    showErrorDialog(
                        "alert.connection.failed", "networkpage.error.connection.read"));
            closeResources(serverSocket, clientSocket);
            endHosting();
            return;
          }

          /*
           * A DataInputStream is created to read the file name via readUTF.
           */
          DataInputStream dis = new DataInputStream(clientInputStream);

          String fileName;
          try {
            fileName = dis.readUTF();

          } catch (IOException e1) {
            e1.printStackTrace();
            Platform.runLater(
                () -> showErrorDialog("alert.load.failed", "networkpage.error.read.fileheader"));
            closeResources(serverSocket, clientSocket, clientInputStream);
            endHosting();
            return;
          }

          File defaultDirectory = getDefaultDirectoryAsFile();

          byte[] bytes = new byte[8192];

          /*
           * Blocks until readable data has been detected or end of file.
           */
          int count;
          try {
            count = clientInputStream.read(bytes);

          } catch (IOException e1) {
            e1.printStackTrace();
            Platform.runLater(
                () -> showErrorDialog("alert.load.failed", "networkpage.error.read.file"));
            closeResources(serverSocket, clientSocket, clientInputStream, dis);
            endHosting();
            return;
          }

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

            closeResources(serverSocket, clientSocket, clientInputStream, dis);
            endHosting();
            return;
          }

          // outputstream to write the received data to
          OutputStream out;

          try {
            out = new FileOutputStream(saveFile);

          } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            Platform.runLater(
                () -> showErrorDialog("alert.save.failed", "networkpage.error.write.file"));
            closeResources(serverSocket, clientSocket, clientInputStream, dis);
            endHosting();
            return;
          }

          try {
            do {

              out.write(bytes, 0, count);

            } while ((count = clientInputStream.read(bytes)) > 0);

          } catch (IOException e1) {
            e1.printStackTrace();
            Platform.runLater(
                () -> showErrorDialog("alert.save.failed", "networkpage.error.write.file"));
            closeResources(serverSocket, clientSocket, clientInputStream, dis, out);
            endHosting();
            return;
          }

          closeResources(serverSocket, clientSocket, clientInputStream, dis, out);

          endHosting();
        };

    Thread serverThread = new Thread(serverTask);

    // disable send button to prevent sending while having an open server
    setConnectDisable(true);
    // also disables host since it doesn't make sense to open multiple listener connections
    setHostDisable(true);

    serverThread.start();
  }

  private void endHosting() {

    // server finished and send can be re-enabled
    setConnectDisable(false);
    setHostDisable(false);

    // clear host text fields after successfully receiving a file
    clearHostTextFields();

    receiving = false;
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
  private void showInformationDialog(String messageKey, Object... args) {
    presenterManager.showAlertDialog(
        AlertType.INFORMATION,
        I18N.get("alert.title.information"),
        I18N.get("alert.header.network"),
        I18N.get(messageKey, args));
  }

  /**
   * Shows an error window.
   *
   * @param message the message
   */
  private void showErrorDialog(String titleKey, String messageKey, Object... args) {
    presenterManager.showAlertDialog(
        AlertType.ERROR,
        I18N.get("alert.title.error"),
        I18N.get(titleKey),
        I18N.get(messageKey, args));
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
        showInformationDialog("networkpage.info.close.connection");
        setConnectDisable(false);
        setHostDisable(false);
        canceling = false;
      } catch (IOException e) {
        showErrorDialog("alert.input.network", "networkpage.error.close.connection");
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

      showErrorDialog("alert.input.invalid", "networkpage.error.invalid.ipformat");
    }

    if (!PortNumValidator.getInstance().isValid(portText)) {

      setErrorPseudoClass(portConnectTextField, true);
      invalid = true;

      showErrorDialog("alert.input.invalid", "networkpage.error.invalid.portformat");
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

      // host name is not valid to connect to
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
    ipConnectTextField
        .promptTextProperty()
        .bind(I18N.createStringBinding("networkpage.connect.ip.prompt"));
    portConnectLbl.textProperty().bind(I18N.createStringBinding("networkpage.connect.port.lbl"));
    portConnectTextField
        .promptTextProperty()
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

  @Override
  public void onExit() {
    handleCancel();
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
