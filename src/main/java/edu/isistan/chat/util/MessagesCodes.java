package edu.isistan.chat.util;

/**
 * Clase que contiene el protocolo de comunicacion entre el servidor y el cliente.
 * @author Agustin Chirichigno
 * @author Braian Varona
 * @version 1.0
 */
public class MessagesCodes {

    public static final String PRIVATE_MESSAGE = "PRV"; // Si el cliente envia al thread PRV|receiver|message - Si el thread del server envia al cliente PRV|sender|message
    public static final String GLOBAL_MESSAGE = "GBL"; // GBL|sender|message
    public static final String NEW_USER = "NWU"; // NWU|username
    public static final String GET_USERS = "GTU"; // GTU
    public static final String REMOVE_USER = "RMV"; // RMV|username
    public static final String SEPARATOR = "|";

}
