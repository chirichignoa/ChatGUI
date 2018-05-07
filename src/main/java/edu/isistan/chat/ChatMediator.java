package edu.isistan.chat;

import edu.isistan.chat.util.MessagesCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatMediator implements IChat {

    private static Logger log = LogManager.getLogger(ChatMediator.class);

    private Socket socket;
    private DataOutputStream dataOut;
    private String username;

    public ChatMediator(Socket socket, String username) {
        this.username = username;
        this.socket = socket;
        try {
            dataOut = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            log.error("No se pudo obtener el dataOutput");
        }
    }

    @Override
    public void sendMsg(String text) {
        try {
            String message = MessagesCodes.GLOBAL_MESSAGE +
                    MessagesCodes.SEPARATOR +
                    text;
            log.debug("Enviando mensaje global: " + message);
            this.dataOut.writeUTF(message);
        } catch (IOException e) {
            log.error("Error al escribir en el data output.");
        }
    }

    @Override
    public void sendMsg(String to, String text) {
        try {
            //AGREGAR QUIEN LO ENVIA, COMPLETAR EN TODAS
            String message = MessagesCodes.PRIVATE_MESSAGE +
                    MessagesCodes.SEPARATOR +
                    to +
                    MessagesCodes.SEPARATOR +
                    text;
            log.debug("Enviando mensaje privado: " + message);
            this.dataOut.writeUTF(message);
        } catch (IOException e) {
            log.error("Error al escribir en el data output.");
        }
    }

    @Override
    public void registerNewUser(String user) {
        try {
            String message = MessagesCodes.NEW_USER +
                    MessagesCodes.SEPARATOR +
                    user;
            this.dataOut.writeUTF(message);
        } catch (IOException e) {
            log.error("Error al escribir en el data output.");
        }
    }
}
