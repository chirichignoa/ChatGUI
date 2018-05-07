package edu.isistan.chat;

import edu.isistan.chat.util.MessagesCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private static Logger log = LogManager.getLogger(ClientThread.class);

    private Socket socket;
    private ChatGUI chatGUI;
    // escuchar peticiones del server, decodificar y accion correspondiente (llamar metodos de chat gui)

    public ClientThread(Socket socket, ChatGUI chatGUI) {
        this.socket = socket;
        this.chatGUI = chatGUI;
    }

    @Override
    public void run() {

        DataInputStream dataIn = null;
        String receivedMessage;
        try {
            dataIn = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            log.error("Error al crear el stream de entrada: " + ex.getMessage());
        } catch (NullPointerException ex) {
            log.error("El socket no se creo correctamente. ");
        }

        // Bucle infinito que recibe mensajes del servidor
        boolean connected = true;
        while (connected) {
            try {
                receivedMessage = dataIn.readUTF();
                decodeRequest(receivedMessage);
            } catch (IOException ex) {
                log.error("Error al leer del stream de entrada: " + ex.getMessage());
                connected = false;
            } catch (NullPointerException ex) {
                log.error("El socket no se creo correctamente. ");
                connected = false;
            }
        }
    }

    private void decodeRequest(String request) {
        String[] args = request.split("\\" + MessagesCodes.SEPARATOR);
        switch (args[0]) {
            case MessagesCodes.NEW_USER:
                this.chatGUI.addUser(args[1]);
                break;
            case MessagesCodes.GLOBAL_MESSAGE:
                this.chatGUI.addNewGeneralMsg(args[1], args[2]); //args[1] contains sender username args[2] contains message
                break;
            case MessagesCodes.PRIVATE_MESSAGE:
                this.chatGUI.addNewMsg(args[1], args[2]); //args[1] contains sender username args[2] contains message
                break;
            case MessagesCodes.GET_USERS:
                this.chatGUI.updateUserList(args);
                break;
            default:
                break;
        }
    }
}
