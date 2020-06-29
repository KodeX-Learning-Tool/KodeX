package kodex.model;

import java.net.ServerSocket;
import java.net.Socket;

import javafx.stage.FileChooser;

/**
 * 
 */
public class Network {

    /**
     * Default constructor
     */
    public Network() {
    }

    /**
     * 
     */
    private ServerSocket server;

    /**
     * 
     */
    private Socket client;

    /**
     * 
     */
    private FileChooser chooser;




    /**
     * @param port
     */
    public void receiveFile(int port) {
        // TODO implement here
    }

    /**
     * @param ip 
     * @param port
     */
    public void sendFile(String ip, int port) {
        // TODO implement here
    }

}