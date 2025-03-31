/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jardines;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aleja
 */
public class GestionClientes extends JFrame {

    static Connection conexion;
    static DefaultTableModel Tabla;
    static JTable tablaClientes;

    public GestionClientes() {
        setTitle("Gestión de los clientes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("img\\logo.png");
        setIconImage(logo.getImage());

        JPanel panel = new JPanel();
        JButton botonMostrar = new JButton("Mostrar clientes");
        JButton botonAlta = new JButton("Introducir clientes");
        JButton botonBaja = new JButton("Eliminar clientes");
        JButton botonModificar = new JButton("Modificar clientes");
        JButton botonBuscarCliente = new JButton("Buscar por clientes");
        JButton botonBuscarPorJardin = new JButton("Buscar por jardín");

        JTextField buscarCliente = new JTextField(10);
        JTextField buscarPorJardin = new JTextField(10);

        panel.add(botonMostrar);
        panel.add(botonAlta);
        panel.add(botonBaja);
        panel.add(botonModificar);
        panel.add(new JLabel("Buscar cliente:"));
        panel.add(buscarCliente);
        panel.add(botonBuscarCliente);
        panel.add(new JLabel("Buscar por jardín:"));
        panel.add(buscarPorJardin);
        panel.add(botonBuscarPorJardin);
        add(panel, BorderLayout.NORTH);

        Tabla = new DefaultTableModel(new Object[]{"ID", "Usuario", "Contraseña", "Jardín"}, 0);
        tablaClientes = new JTable(Tabla);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        add(scrollPane, BorderLayout.CENTER);

        conexion = Mysql.Conexion();

        botonAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntroducirCliente();
            }
        });

        botonBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EliminarCliente();
            }
        });

        botonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarCliente();
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarClientes();
            }
        });

        botonBuscarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientesPorNombre(buscarCliente.getText());
            }
        });

        botonBuscarPorJardin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientesPorJardin(buscarPorJardin.getText());
            }
        });
    }

    private void IntroducirCliente() {

        int eljardin;
        String usuarioInput = JOptionPane.showInputDialog("Usuario del cliente:");
        String contrasenaInput = JOptionPane.showInputDialog("Contraseña del cliente:");
        String jardinInput = JOptionPane.showInputDialog("Jardín del cliente:");

        if (usuarioInput != null && !usuarioInput.trim().isEmpty() && contrasenaInput != null && !contrasenaInput.trim().isEmpty()) {

            try {
                eljardin = parseInt(jardinInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                return;
            }

            String consulta = "INSERT INTO clientes(usuario, contrasena, jardin_asociado) VALUES (?, ?, ?)";
            int resultado = 0;
            // Crea un PreparedStatement con la consulta para insertar un nuevo libro en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Va estableciendo los parámetros
                pstmt.setString(1, usuarioInput);
                pstmt.setString(2, contrasenaInput);
                pstmt.setInt(3, eljardin);
                // Ejecuta la consulta 
                resultado = pstmt.executeUpdate();

                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente añadido correctamente.");
                    mostrarClientes();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo añadir el cliente.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar cliente: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos.");
        }
    }

    private void EliminarCliente() {
        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaClientes.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaClientes.getValueAt(selectedRow, 0);
            String consulta = "DELETE FROM clientes WHERE id = ?";
            int devolver = 0;

            // Crea un PreparedStatement con la consulta para eliminar un libro en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Asigna el valor del id como primer parámetro en la consulta 
                pstmt.setInt(1, id);
                // Ejecuta la consulta
                devolver = pstmt.executeUpdate();

                if (devolver > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente eliminado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el cliente.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar cliente: " + ex.getMessage());
                ex.printStackTrace();
            }

            mostrarClientes();

        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un cliente para eliminar.");
        }
    }

    private void modificarCliente() {

        int nuevoJardinCliente;

        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaClientes.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaClientes.getValueAt(selectedRow, 0);
            String nuevoUsuarioInput = JOptionPane.showInputDialog("Nuevo usuario:", tablaClientes.getValueAt(selectedRow, 1));
            String nuevaContrasenaInput = JOptionPane.showInputDialog("Nueva contraseña:", tablaClientes.getValueAt(selectedRow, 2));
            String nuevoJardinInput = JOptionPane.showInputDialog("Nuevo jardín:", tablaClientes.getValueAt(selectedRow, 3));

            if (nuevoUsuarioInput != null && !nuevoUsuarioInput.trim().isEmpty() && nuevaContrasenaInput != null && !nuevaContrasenaInput.trim().isEmpty()
                    && nuevoJardinInput != null && !nuevoJardinInput.trim().isEmpty()) {

                try {
                    nuevoJardinCliente = parseInt(nuevoJardinInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                    return;
                }

                String consulta = "UPDATE clientes SET usuario=?, contrasena=?, jardin_asociado=? WHERE id=?";
                int devolver = 0;

                // Crea un PreparedStatement con la consulta para modificar un libro en la base de datos
                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setString(1, nuevoUsuarioInput);
                    pstmt.setString(2, nuevaContrasenaInput);
                    pstmt.setInt(3, nuevoJardinCliente);
                    pstmt.setInt(4, id);
                    // Ejecuta la consulta
                    devolver = pstmt.executeUpdate();

                    if (devolver > 0) {
                        JOptionPane.showMessageDialog(null, "Cliente modificado con éxito.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar el cliente.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar cliente: " + ex.getMessage());
                    ex.printStackTrace();
                }

                mostrarClientes();
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un cliente para modificar.");
        }
    }

    private void mostrarClientes() {
        // Limpia todas las filas de la tabla mediante setRowCount()
        Tabla.setRowCount(0);
        String consulta = "SELECT * FROM clientes";

        // Crea un bloque try para ejecutar la consulta
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            // Itera sobre cada fila del ResultSet mediante while
            while (rs.next()) {
                // Se declara un array de objetos para almacenar los datos de cada libro
                Object[] cliente = new Object[6];
                cliente[0] = rs.getInt("id");
                cliente[1] = rs.getString("usuario");
                cliente[2] = rs.getString("contrasena");
                cliente[3] = rs.getInt("jardin_asociado");
                // Añade la fila a la tabla mediante addRow()
                Tabla.addRow(cliente);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los clientes: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void buscarClientesPorNombre(String usuario) {
        if (usuario != null && !usuario.trim().isEmpty()) {
            // Limpia la tabla antes de mostrar resultados mediante setRowCount()
            Tabla.setRowCount(0);
            String consulta = "SELECT * FROM clientes WHERE usuario = ?";

            // Crea un PreparedStatement con la consulta 
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Establece el primer parámetro de la consulta como un string con el título buscado 
                pstmt.setString(1, usuario);
                // Ejecuta la consulta con executeQuery()
                ResultSet rs = pstmt.executeQuery();

                // Itera sobre cada fila del ResultSet mediante while
                while (rs.next()) {
                    // Se declara un array de objetos para almacenar los datos de cada libro
                    Object[] cliente = new Object[6];
                    cliente[0] = rs.getInt("id");
                    cliente[1] = rs.getString("usuario");
                    cliente[2] = rs.getString("contrasena");
                    cliente[3] = rs.getDouble("jardin_asociado");
                    // Añade la fila a la tabla mediante addRow()
                    Tabla.addRow(cliente);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar por cliente: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Escribe un cliente para buscar.");
        }
    }

    private void buscarClientesPorJardin(String jardinInput) {

        int eljardin;

            try {
                eljardin = parseInt(jardinInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                return;
            }
            // Limpia la tabla antes de mostrar resultados mediante setRowCount()
            Tabla.setRowCount(0);
            String consulta = "SELECT * FROM clientes WHERE jardin_asociado = ?";

            // Crea un PreparedStatement con la consulta 
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Establece el primer parámetro de la consulta como un string con la editorial buscada
                pstmt.setInt(1, eljardin);
                // Ejecuta la consulta con executeQuery()
                ResultSet rs = pstmt.executeQuery();

                // Itera sobre cada fila del ResultSet mediante while
                while (rs.next()) {
                    // Se declara un array de objetos para almacenar los datos de cada libro
                    Object[] clientes = new Object[6];
                    clientes[0] = rs.getInt("id");
                    clientes[1] = rs.getString("usuario");
                    clientes[2] = rs.getString("contrasena");
                    clientes[3] = rs.getInt("jardin_asociado");
                    // Añade la fila a la tabla mediante addRow()
                    Tabla.addRow(clientes);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar por jardín: " + ex.getMessage());
                ex.printStackTrace();
            }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionClientes frame = new GestionClientes();
            frame.setVisible(true);
        });
    }
}
