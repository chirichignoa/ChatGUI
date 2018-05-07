package test;

import edu.isistan.chat.ChatGUI;
import edu.isistan.chat.IChat;
import edu.isistan.chat.gui.MainWindows;

public class ChatTest implements IChat {

    @Override
    public void sendMsg(String text) {
        System.out.println("TEXT " + text);
    }

    @Override
    public void sendMsg(String to, String text) {
        System.out.println("TO " + to + " TEXT "+text);
    }

    @Override
    public void registerNewUser(String user) {
        System.out.println("NEW USER " + user);
    }

    public static void main(String[] args) {
        ChatTest ct = new ChatTest();
        ChatGUI gui = MainWindows.launchOrGet();
        Thread t = new Thread(()->{
            try {
             Thread.sleep(5000);
         } catch (InterruptedException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
            gui.addUser("juan");
         });
        t.start();
        t = new Thread(()->{
            try {
             Thread.sleep(6000);
         } catch (InterruptedException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
            gui.addUser("pedro");
         });
        t.start();
        t = new Thread(()->{
            try {
             Thread.sleep(6000);
         } catch (InterruptedException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
            gui.addUser("clara");
         });
        t.start();
        t = new Thread(()->{
            try {
             Thread.sleep(10000);
         } catch (InterruptedException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
            gui.removeUser("juan");
         });
        t.start();
        t = new Thread(()->{
            try {
             Thread.sleep(10000);
         } catch (InterruptedException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
            gui.addNewMsg("pedro", "hola");
         });
        t.start();

        t = new Thread(()->{
            try {
             Thread.sleep(10000);
         } catch (InterruptedException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
            gui.addNewGeneralMsg("pedro", "hola Mundo");
         });
        t.start();
    }

}
