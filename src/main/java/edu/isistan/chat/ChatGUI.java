package edu.isistan.chat;

/**
 * Interface que expone la funcionalidad requerida del chat para actualizar la GUI.
 */
public interface ChatGUI {

    /**
     * Metodo que actualiza la GUI ante un nuevo mensaje global.
     * @param from nombre del usuario emisor.
     * @param text mensaje.
     */
    public void addNewGeneralMsg(String from, String text);

    /**
     * Metodo que actualiza la GUI ante un nuevo mensaje privado.
     * @param from nombre del usuario emisor.
     * @param to nombre del usuario receptor.
     * @param text mensaje.
     */
    public void addNewMsg(String from, String to, String text);

    /**
     * Metodo que actualiza la GUI ante un nuevo usuario.
     * @param usr nombre del nuevo usuario.
     */
    public void addUser(String usr);

    /**
     * Metodo que actualiza la GUI ante un la desconexion de un usuario.
     * @param usr nombre del usuario.
     */
    public void removeUser(String usr);

    /**
     * Metodo que actualiza la GUI al iniciar un nuevo chat privado.
     * @param usr usuario con el que se inicia la comunicacion.
     */
    public void chatWith(String usr);

    /**
     * Metodo que actualiza la GUI para cargar inicialmente los usuarios conectados.
     * @param users conjunto de nombres de usuario.
     */
    public void updateUserList(String[] users );
}
