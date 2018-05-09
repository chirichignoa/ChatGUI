package edu.isistan.chat;

import edu.isistan.chat.util.MessagesCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Clase que se encarga de la comunicacion de un cliente con el servidor.
 * @author Agustin Chirichigno
 * @author Braian Varona
 * @version 1.0
 */
public class ChatMediator implements IChat {

    private static Logger log = LogManager.getLogger(ChatMediator.class);

    private Socket socket;
    private DataOutputStream dataOut;
    private String username;

    /**
     * Constructor de la clase.
     * @param socket socket que mantiene la conexion con el servidor.
     * @param username nombre de usuario asociado al cliente.
     */
    public ChatMediator(Socket socket, String username) {
        this.username = username;
        this.socket = socket;
        try {
            dataOut = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            log.error("No se pudo obtener el dataOutput");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMsg(String text) {
        try {
            String message = MessagesCodes.GLOBAL_MESSAGE +
                    MessagesCodes.SEPARATOR +
                    text;
            log.debug("Enviando mensaje global: " + message);
            this.dataOut.writeUTF(message);
        } catch (IOException e) {
            log.error("Error al enviar mensaje global en el data output.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMsg(String to, String text) {
        try {
            String message = MessagesCodes.PRIVATE_MESSAGE +
                    MessagesCodes.SEPARATOR +
                    this.username +
                    MessagesCodes.SEPARATOR +
                    to +
                    MessagesCodes.SEPARATOR +
                    text;
            log.debug("Enviando mensaje privado: " + message);
            this.dataOut.writeUTF(message);
        } catch (IOException e) {
            log.error("Error al enviar mensaje privado en el data output.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerNewUser(String user) {
        try {
            String message = MessagesCodes.NEW_USER +
                    MessagesCodes.SEPARATOR +
                    user;
            this.dataOut.writeUTF(message);
        } catch (IOException e) {
            log.error("Error al registrar nuevo usuario en el data output.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUser(String user) {
        try {
            String message = MessagesCodes.REMOVE_USER +
                    MessagesCodes.SEPARATOR +
                    user;
            log.debug("Eliminando usuario: " + message);
            this.dataOut.writeUTF(message);
        } catch (IOException e) {
            log.error("Error al remover usuario en el data output.");
        }
    }
}
