package edu.isistan.chat.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Clase que representa la ventana de configuracion de la aplicacion. La cual permitira inicialmente establecer
 * el nombre de usuario, el host y el puerto del servidor.
 */
public class UsernameDialog extends JDialog {

    private static Logger log = LogManager.getLogger(UsernameDialog.class);
    private JTextField tfUsuario;
    private JTextField tfHost;
    private JTextField tfPuerto;

    /**
     * Constructor de la clase.
     * @param root ventana inicial.
     */
    public UsernameDialog(JFrame root) {
        super(root, "Configuracion inicial", true);

        JLabel lbUsuario = new JLabel("Usuario:");
        JLabel lbHost = new JLabel("Host:");
        JLabel lbPuerto = new JLabel("Puerto:");

        this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		        System.exit(ABORT);
		    }
        });
        
        tfUsuario = new JTextField();
        tfHost = new JTextField("localhost");
        tfPuerto = new JTextField("2525");

        
        JButton btAceptar = new JButton("Aceptar");
        btAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //setVisible(false);
                dispose();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Container c = this.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(20, 20, 0, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        c.add(lbUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        c.add(lbHost, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        c.add(lbPuerto, gbc);

        gbc.ipadx = 100;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 1;
        gbc.gridy = 0;
        c.add(tfUsuario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        c.add(tfHost, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        c.add(tfPuerto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 20, 20);
        c.add(btAceptar, gbc);

        this.pack(); // Le da a la ventana el minimo tamaño posible
        this.setLocation(450, 200); // Posicion de la ventana
        this.setResizable(false); // Evita que se pueda estirar la ventana
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Deshabilita el boton de cierre de la ventana
        this.setVisible(true);
    }

    /**
     * Metodo para obtener el nombre de usuario que se ha ingresado.
     * @return nombre del usuario.
     */
    public String getUsername(){
        return this.tfUsuario.getText();
    }

    /**
     * Metodo para obtener la direccion del servidor que se ha ingresado.
     * @return direccion del host.
     */
    public String getHost(){
        return this.tfHost.getText();
    }

    /**
     * Metodo para obtener el numero de puerto del servidor que se ha ingresado.
     * @return numero de puerto.
     */
    public int getPort(){
        return Integer.parseInt(this.tfPuerto.getText());
    }
}
