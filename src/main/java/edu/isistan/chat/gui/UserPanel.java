package edu.isistan.chat.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * Clase que representa la ventana de chat privada entre dos usuarios.
 */
public class UserPanel extends JPanel {

    private static Logger log = LogManager.getLogger(UserPanel.class);

    private static final long serialVersionUID = -5292840943808373776L;
    private JTextField sentMsg;
    private JTextArea maintxt;
    private String user;

    /**
     * Create the panel.
     */
    public UserPanel(String user) {
        this.user = user;
        setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);
        
        maintxt = new JTextArea();
        maintxt.setEditable(false);
        scrollPane.setViewportView(maintxt);
        
        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout(0, 0));
        
        sentMsg = new JTextField();
        panel.add(sentMsg, BorderLayout.CENTER);
        sentMsg.setColumns(10);
        
        JButton btnNewButton = new JButton("Send");
        panel.add(btnNewButton, BorderLayout.EAST);
         
        btnNewButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!sentMsg.getText().trim().equals("")) {
                    MainWindows.getIChat().sendMsg(UserPanel.this.user, sentMsg.getText());
                    sentMsg.setText("");
                }
            }
        });

    }

    /**
     * Logica grafica para mostrar nuevos mensajes.
     * @param from nombre de usuario del emisor.
     * @param text texto que envia.
     */
    public void addNewMessage(String from, String text) {
        log.debug("Seteando mensaje: " + from + "|" + text);
        maintxt.setText(maintxt.getText()+from + ": " + text + "\n");
    }

}
