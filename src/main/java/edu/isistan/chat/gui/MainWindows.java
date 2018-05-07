package edu.isistan.chat.gui;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.*;

import edu.isistan.chat.ChatGUI;
import edu.isistan.chat.ChatMediator;
import edu.isistan.chat.ClientThread;
import edu.isistan.chat.IChat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.BorderLayout;

public class MainWindows implements ChatGUI {

    private static Logger log = LogManager.getLogger(MainWindows.class);

    private static IChat CHAT;
    private static AtomicBoolean LAUNCH = new AtomicBoolean(false);
    private static ChatGUI COURRENT;
    private static String MAIN = "Main";
    private String host;
    private int port;
    private String username;
    private JFrame frame;
    private JTabbedPane tabbedPane;

    public static ChatGUI launchOrGet() {
        if(LAUNCH.compareAndSet(false, true)) {
            COURRENT = new MainWindows("localhost", 2525);
        }
        return COURRENT;
    }


    public static IChat getIChat() {
        return CHAT;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
//        new MainWindows("localhost", 2525);
        MainWindows.launchOrGet();
    }

    /**
     * Create the application.
     */
    private MainWindows(String host, int port) {
        this.host = host;
        this.port = port;
        initialize();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        Socket socket;
        // crear socket con el server
        try {
            frame = new JFrame("Supa Duppa Chat!!");
            frame.setBounds(100, 100, 800, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
            tabbedPane.add(MAIN, new MainPanel((ChatMediator) CHAT));

            UsernameDialog usernameDialog = new UsernameDialog(frame);
            // aca controlar la entrada
            this.host = usernameDialog.getHost();
            this.port = usernameDialog.getPort();
            this.username = usernameDialog.getUsername();
            socket = new Socket(this.host, this.port);
            ClientThread clientThread = new ClientThread(socket, this);
            clientThread.start();

            CHAT = new ChatMediator(socket, this.username);
            CHAT.registerNewUser(this.username);
        } catch (IOException e) {
            log.error("Error creando el socket: " + e.getMessage());
        }
    }


    @Override
    public void addNewGeneralMsg(String from, String text) {
        EventQueue.invokeLater(()->{
            ((MainPanel)tabbedPane.getComponent(0)).addNewMessage(from, text); 
        });
    }


    @Override
    public void addNewMsg(String from, String text) {
        EventQueue.invokeLater(()->{
            if (!from.equals(this.username)) {
                int tab = -1;
                for(int i = 0; i < tabbedPane.getTabCount(); i++)
                    if (tabbedPane.getTitleAt(i).equals(from)) {
                        tab = i;
                        break;
                    }
                if (tab == -1) {
                    tabbedPane.addTab(from, new UserPanel(from));
                    tab = tabbedPane.getTabCount() - 1;
                }
                ((UserPanel) tabbedPane.getComponent(tab)).addNewMessage(text);
            }
        });
    }


    @Override
    public void addUser(String user) {
        EventQueue.invokeLater(()->{
            if (user.equals(MAIN)) {
                System.err.println("No se puere agregar un user Main");
                return;
            }
            ((MainPanel)tabbedPane.getComponent(0)).addUser(user);
        });
    }


    @Override
    public void removeUser(String user) {
        EventQueue.invokeLater(()->{
            if (user.equals(MAIN)) {
                System.err.println("No se puere remover la ventana Main");
                return;
            }
            for(int i = 0; i < tabbedPane.getTabCount(); i++)
                if (tabbedPane.getTitleAt(i).equals(user)) {
                    tabbedPane.remove(i);
                    break;
                }
            ((MainPanel)tabbedPane.getComponent(0)).remevoUser(user);
        });
    }


    @Override
    public void chatWith(String user) {
        EventQueue.invokeLater(()->{
            for(int i = 0; i < tabbedPane.getTabCount(); i++)
                if (tabbedPane.getTitleAt(i).equals(user))
                    return;
            tabbedPane.addTab(user, new UserPanel(user));
        });
    }

    @Override
    public void updateUserList(String[] users) {
        for (int i = 1; i < users.length ; i++) {
            this.addUser(users[i]);
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }
}
