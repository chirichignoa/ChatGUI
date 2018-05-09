package edu.isistan.chat;

import edu.isistan.chat.util.MessagesCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Thread que recibe los mensajes enviados por el servidor y en base a ello ejecuta acciones correspondientes en la GUI
 * @author Agustin Chirichigno
 * @author Braian Varona
 * @version 1.0
 */
public class ClientThread extends Thread {

    private static Logger log = LogManager.getLogger(ClientThread.class);

    private Socket socket;
    private ChatGUI chatGUI;

    /**
     * Constructor de la clase.
     * @param socket socket que mantiene la conexion con el servidor.
     * @param chatGUI instancia de {@link ChatMediator}, la cual que maneja las conexiones con el servidor.
     * @see ChatMediator
     */
    public ClientThread(Socket socket, ChatGUI chatGUI) {
        this.socket = socket;
        this.chatGUI = chatGUI;
    }

    /**
     * Metodo que contiene la logica para que el thread atienda las peticiones que llegan desde servidor. Consiste en decodificar
     * la peticion para tomar la accion que corresponda en la GUI.
     */
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
        try {
            dataIn.close();
            this.socket.close();
        } catch (IOException e) {
            log.error("El socket no se cerro correctamente. ");
        }

    }

    /**
     * Metodo que decodifica la peticion que recibe y realiza la accion correspondiente de acuerdo al tipo de la misma.
     * @param request string con la peticion que el cliente envia.
     */
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
                this.chatGUI.addNewMsg(args[1], args[2], args[3]); //args[1] contains sender username args[2] contains message
                break;
            case MessagesCodes.GET_USERS:
                this.chatGUI.updateUserList(args);
                break;
            case MessagesCodes.REMOVE_USER:
                this.chatGUI.removeUser(args[1]);
                break;
            default:
                break;
        }
    }
}
