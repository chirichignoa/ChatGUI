package edu.isistan.chat;

public interface IChat {

    void sendMsg(String text);
    void sendMsg(String to, String text);
    void registerNewUser(String user);
    void removeUser(String user);
}
