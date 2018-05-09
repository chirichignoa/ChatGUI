package edu.isistan.chat;

/**
 * Interface que expone la funcionalidad requerida del chat para comunicarse con el servidor.
 */
public interface IChat {

    /**
     * Funcionalidad para enviar los mensajes globales desde el cliente al servidor.
     * @param text mensaje que se envia.
     */
    void sendMsg(String text);

    /**
     * Funcionalidad para enviar los mensajes privados entre dos usuarios desde el cliente al servidor.
     * @param to nombre del usuario receptor
     * @param text mensaje
     */
    void sendMsg(String to, String text);

    /**
     * Funcionalidad para notificar el registro de un nuevo usuario al server.
     * @param user nombre del usuario.
     */
    void registerNewUser(String user);

    /**
     * Funcionalidad para notificar la desconexion de un usuario al server.
     * @param user nombre del usuario.
     */
    void removeUser(String user);
}
