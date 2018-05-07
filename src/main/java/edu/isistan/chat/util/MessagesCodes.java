package edu.isistan.chat.util;

public class MessagesCodes {

    public static final String PRIVATE_MESSAGE = "PRV"; // Si el cliente envia al thread PRV|receiver|message - Si el thread del server envia al cliente PRV|sender|message
    public static final String GLOBAL_MESSAGE = "GBL"; // GBL|sender|message
    public static final String NEW_USER = "NWU"; // NWU|username
    public static final String GET_USERS = "GTU"; // GTU
    public static final String SEPARATOR = "|";

}
