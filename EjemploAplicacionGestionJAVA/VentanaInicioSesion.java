/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tienda_videojuegos;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Crea una ventana para poder inciar sesión con los campos nombre y contraseña,
 * confirma la existencia en la base de datos empleados y si existe se cierra la
 * misma e instancia a la clase InterfazPrincipal.
 *
 * @author Andrés Guillén
 */
public class VentanaInicioSesion extends javax.swing.JFrame {

    String url = "jdbc:mysql://localhost:3306/anmafe_games_bd";
    String usuario = "root";
    String clave = "";
    /*Mio*/
    private String nombre;
    private int id;
    private static VentanaInicioSesion instancia;

    public String getNombre() {
        return nombre;
    }

    public static VentanaInicioSesion obtenerInstancia() {
        return instancia;
    }

    /**
     * Llama al método initComponents() y establece la ubicación de la ventana
     * en el centro de la pantalla.
     */
    public VentanaInicioSesion() {

        initComponents();
        setLocationRelativeTo(null);
        instancia = this;
    }

    /**
     * Crea todos los componentes de la interfaz.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTitulo = new javax.swing.JLabel();
        labelNombre = new javax.swing.JLabel();
        labelClave = new javax.swing.JLabel();
        campoNombre = new javax.swing.JTextField();
        botonEntrar = new javax.swing.JButton();
        campoClave = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Iniciar sesión");
        setResizable(false);

        labelTitulo.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        labelTitulo.setText("Iniciar Sesión");

        labelNombre.setText("Nombre:");

        labelClave.setText("Contraseña:");

        campoNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoNombreKeyPressed(evt);
            }
        });

        botonEntrar.setText("Entrar");
        botonEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEntrarActionPerformed(evt);
            }
        });

        campoClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoClaveKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(labelTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelClave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoClave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(botonEntrar)))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(labelTitulo)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelClave, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(botonEntrar)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Coge los datos de los inputs campoNombre y campoClave y los compara con
     * los datos de la tabla empleados, si el nombre y la contraseña son
     * correctos la ventana se cierra y llama a la clase InterfazPrincipal.
     *
     * @param evt
     */
    private void botonEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEntrarActionPerformed

        nombre = campoNombre.getText();
        // Sentencia para buscar el nombre en la tabla
        String sql = "SELECT id, clave, puesto FROM empleados WHERE nombre=?";

        try (
                Connection conexion = DriverManager.getConnection(url, usuario, clave); PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, nombre);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Booleano que señala si el empleado fue encontrado
                boolean datosCorrectos = false;
                boolean esAdmin = false;
                // Booleano para verificar si ya se ha mostrado el JOptionPane
                boolean mostradoJOptionPane = false;

                // Se revisan todos los resultados de la consulta en bucle
                while (resultSet.next()) {
                    // Si la contraseña ingresada es igual a la contraseña de la tabla
                    // significa que los datos son correctos
                    if (BCrypt.checkpw(new String(campoClave.getPassword()), resultSet.getString("clave"))) {
                        datosCorrectos = true;
                        id = resultSet.getInt("id");
                        System.out.println(resultSet.getString("puesto"));

                        if (resultSet.getString("puesto").equals("gestor")) {
                            esAdmin = true;
                        }
                    }
                }

                // Si los datos son correctos se cierra la ventana y se abre el menú principal
                if (datosCorrectos) {
                    try {
                        // Mostrar el JOptionPane solo si no se ha mostrado antes
                        if (!mostradoJOptionPane) {
                            // Preguntar al usuario por el motivo de llegar tarde
                            String motivoLlegadaTarde = JOptionPane.showInputDialog(this, "¿Por qué llegas tarde?");

                            // Registrar la entrada del empleado con el motivo de llegada tarde como comentarios
                            registrarEntrada(id, motivoLlegadaTarde);

                            // Marcar que se ha mostrado el JOptionPane
                            mostradoJOptionPane = true;

                            // Llamar al método para comprobar la hora después de haber mostrado el JOptionPane y registrado el motivo
                            Checkin checkin = new Checkin(id);
                            checkin.comprobarHora();
                            System.out.println("vuyvuy");
                        }

                        dispose();
                        InterfazPrincipal principal = new InterfazPrincipal(esAdmin, id);
                        principal.iniciarHiloMensajes();
                        principal.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error al intentar acceder a la base de datos: " + e.getMessage());
                    }
                } else {
                    // si no se encuentra al empleado se muestra una ventana de error 
                    JOptionPane.showMessageDialog(this, "Los datos son incorrectos.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al intentar acceder a la base de datos: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al intentar acceder a la base de datos: " + e.getMessage());
        }

    }//GEN-LAST:event_botonEntrarActionPerformed

    private void campoClaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoClaveKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            botonEntrar.doClick();
        }
    }//GEN-LAST:event_campoClaveKeyPressed

    private void campoNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoNombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            campoClave.requestFocusInWindow();
        }
    }//GEN-LAST:event_campoNombreKeyPressed

    public static void registrarEntrada(int empleadoId, String comentarios) {
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/anmafe_games_bd", "root", "")) {
            String sql = "INSERT INTO fichajes (empleado_id, fecha_entrada, fecha_salida, comentarios) VALUES (?, NOW(), NULL, ?)";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setInt(1, empleadoId);
                statement.setString(2, comentarios); // Establecer los comentarios
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al intentar registrar la entrada en la base de datos: " + e.getMessage());
        }
    }

    /**
     * Vuelve visible la clase VentanaInicioSesion.
     *
     * @param args
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaInicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaInicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaInicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaInicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaInicioSesion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEntrar;
    private javax.swing.JPasswordField campoClave;
    private javax.swing.JTextField campoNombre;
    private javax.swing.JLabel labelClave;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelTitulo;
    // End of variables declaration//GEN-END:variables
}
