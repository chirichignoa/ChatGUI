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

/**
 * Ventana contenedora de los componentes de la GUI.
 */
public class MainWindows implements ChatGUI {

    private static Logger log = LogManager.getLogger(MainWindows.class);

    private static IChat CHAT = null;
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
            frame = new JFrame("Supa dupa chat");
            frame.setBounds(100, 100, 800, 800);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    if (JOptionPane.showConfirmDialog(frame,
                            "Estas seguro de cerrar la ventana?", "Atencion?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        log.debug("Cerrando ventana.");
                        CHAT.removeUser(MainWindows.this.username);
                        System.exit(0);
                    }
                }
            });

            tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
            tabbedPane.add(MAIN, new MainPanel((ChatMediator) CHAT));

            UsernameDialog usernameDialog = new UsernameDialog(frame);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewGeneralMsg(String from, String text) {
        EventQueue.invokeLater(()->{
            ((MainPanel)tabbedPane.getComponent(0)).addNewMessage(from, text); 
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewMsg(String from, String to, String text) {
        EventQueue.invokeLater(()->{
            String user = from.equals(this.username) ? to : from;
            int tab = -1;
            for(int i = 0; i < tabbedPane.getTabCount(); i++)
                if (tabbedPane.getTitleAt(i).equals(user)) {
                    tab = i;
                    break;
                }
            if (tab == -1) {
                tabbedPane.addTab(user, new UserPanel(user));
                tab = tabbedPane.getTabCount() - 1;
            }
            ((UserPanel) tabbedPane.getComponent(tab)).addNewMessage(from, text);
        });
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void chatWith(String user) {
        if(!this.username.equals(user)) {
            EventQueue.invokeLater(() -> {
                for (int i = 0; i < tabbedPane.getTabCount(); i++)
                    if (tabbedPane.getTitleAt(i).equals(user))
                        return;
                tabbedPane.addTab(user, new UserPanel(user));
            });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUserList(String[] users) {
        for (int i = 1; i < users.length ; i++) {
            this.addUser(users[i]);
        }
    }
}
