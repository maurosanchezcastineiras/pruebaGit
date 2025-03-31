/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.biblioteca3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aleja
 */
public class GestionSocios extends JFrame {

    static Connection conexion;
    static DefaultTableModel Tabla;
    static JTable tablaSocios;
    static ResultSet resultado;

    public GestionSocios() {
        setTitle("Gestión de Socios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("img\\logo.png"); 
        setIconImage(logo.getImage());

        JPanel panel = new JPanel();
        JButton botonMostrar = new JButton("Mostrar Socios");
        JButton botonAlta = new JButton("Dar de Alta");
        JButton botonBaja = new JButton("Dar de Baja");
        JButton botonModificar = new JButton("Modificar");
        JButton botonBuscarNombre = new JButton("Buscar");
        JButton botonBuscarApellidos = new JButton("Buscar");

        JTextField buscarNombre = new JTextField(10);
        JTextField buscarApellidos = new JTextField(10);

        panel.add(botonMostrar);
        panel.add(botonAlta);
        panel.add(botonBaja);
        panel.add(botonModificar);
        panel.add(new JLabel("Buscar Socio por Nombre:"));
        panel.add(buscarNombre);
        panel.add(botonBuscarNombre);
        panel.add(new JLabel("Buscar Socio por Apellidos:"));
        panel.add(buscarApellidos);
        panel.add(botonBuscarApellidos);
        add(panel, BorderLayout.NORTH);

        Tabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellidos", "Edad", "Dirección", "Teléfono"}, 0);
        tablaSocios = new JTable(Tabla);
        JScrollPane scrollPane = new JScrollPane(tablaSocios);
        add(scrollPane, BorderLayout.CENTER);

        conexion = Conexion();

        botonAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darDeAltaSocio();
            }
        });

        botonBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darDeBajaSocio();
            }
        });

        botonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarSocio();
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarSocios();
            }
        });

        botonBuscarNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarSociosPorNombre(buscarNombre.getText());
            }
        });

        botonBuscarApellidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarSociosPorApellidos(buscarApellidos.getText());
            }
        });
    }

    // Método para abrir la conexión a la base de datos
    private static Connection Conexion() {
        try {
            String url = "jdbc:mysql://localhost:3306/biblioteca";
            String usuario = "root";
            String contraseña = "";
            return DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void darDeAltaSocio() {

        String nombreInput = JOptionPane.showInputDialog("Nombre del socio:");
        String apellidosInput = JOptionPane.showInputDialog("Apellidos del socio:");
        String edadInput = JOptionPane.showInputDialog("Edad del socio:");
        String direccionInput = JOptionPane.showInputDialog("Dirección del socio:");
        String telefonoInput = JOptionPane.showInputDialog("Teléfono del socio:");

        if (nombreInput != null && !nombreInput.trim().isEmpty() && apellidosInput != null && !apellidosInput.trim().isEmpty() && edadInput != null && !edadInput.trim().isEmpty() && direccionInput != null && !direccionInput.trim().isEmpty() && telefonoInput != null && !telefonoInput.trim().isEmpty()) {

            int edad;

            try {
                edad = Integer.parseInt(edadInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La edad debe ser un número.");
                return;
            }

            String consulta = "INSERT INTO Socios (nombre, apellidos, edad, direccion, telefono) VALUES (?, ?, ?, ?, ?)";
            int resultado = 0;

            // Crea un PreparedStatement con la consulta para insertar un socio en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Va estableciendo los parámetros
                pstmt.setString(1, nombreInput);
                pstmt.setString(2, apellidosInput);
                pstmt.setInt(3, edad);
                pstmt.setString(4, direccionInput);
                pstmt.setString(5, telefonoInput);
                // Ejecuta la consulta 
                resultado = pstmt.executeUpdate();

                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "Socio añadido con éxito.");
                    mostrarSocios();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo añadir el socio.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar socio: " + ex.getMessage());
                ex.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
        }
    }

    private void darDeBajaSocio() {
        // Obtiene el índice de la fila seleccionada en la tabla de socios
        int selectedRow = tablaSocios.getSelectedRow();
        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaSocios.getValueAt(selectedRow, 0);
            eliminarPrestamosPorSocio(id);
            String consulta = "DELETE FROM Socios WHERE id = ?";
            int resultado = 0;
            // Crea un PreparedStatement con la consulta para eliminar un libro en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Asigna el valor del id como primer parámetro en la consulta 
                pstmt.setInt(1, id);
                // Ejecuta la consulta
                resultado = pstmt.executeUpdate();

                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "Socio eliminado con éxito.");
                    mostrarSocios();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el socio.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar socio: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un socio para eliminar.");
        }
    }
    
    private void eliminarPrestamosPorSocio(int socioId) {
        String consulta = "DELETE FROM prestamos WHERE socioID = ?";
        // Crea un PreparedStatement con la consulta 
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            // Asigna el valor del socioID como primer parámetro en la consulta 
            pstmt.setInt(1, socioId);
            // Ejecuta la consulta
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar préstamos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void modificarSocio() {

        int nuevaEdad;
        // Obtiene el índice de la fila seleccionada en la tabla de socios
        int selectedRow = tablaSocios.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaSocios.getValueAt(selectedRow, 0);
            String nuevoNombreInput = JOptionPane.showInputDialog("Nuevo nombre:", tablaSocios.getValueAt(selectedRow, 1));
            String nuevoApellidoInput = JOptionPane.showInputDialog("Nuevos apellidos:", tablaSocios.getValueAt(selectedRow, 2));
            String nuevaEdadInput = JOptionPane.showInputDialog("Nueva edad:", tablaSocios.getValueAt(selectedRow, 3));
            String nuevaDireccionInput = JOptionPane.showInputDialog("Nueva dirección:", tablaSocios.getValueAt(selectedRow, 4));
            String nuevoTelefonoInput = JOptionPane.showInputDialog("Nuevo teléfono:", tablaSocios.getValueAt(selectedRow, 5));

            if (nuevoNombreInput != null && !nuevoNombreInput.trim().isEmpty() && nuevoApellidoInput != null && !nuevoApellidoInput.trim().isEmpty() && nuevaEdadInput != null && !nuevaEdadInput.trim().isEmpty() && nuevaDireccionInput != null && !nuevaDireccionInput.trim().isEmpty() && nuevoTelefonoInput != null && !nuevoTelefonoInput.trim().isEmpty()) {

                try {
                    nuevaEdad = Integer.parseInt(nuevaEdadInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La edad debe ser un número.");
                    return;
                }

                String consulta = "UPDATE Socios SET nombre=?, apellidos=?, edad=?, direccion=?, telefono=? WHERE id=?";
                int resultado = 0;

                // Crea un PreparedStatement con la consulta para modificar un socio en la base de datos
                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setString(1, nuevoNombreInput);
                    pstmt.setString(2, nuevoApellidoInput);
                    pstmt.setInt(3, nuevaEdad);
                    pstmt.setString(4, nuevaDireccionInput);
                    pstmt.setString(5, nuevoTelefonoInput);
                    pstmt.setInt(6, id);
                    // Ejecuta la consulta
                    resultado = pstmt.executeUpdate();

                    if (resultado > 0) {
                        JOptionPane.showMessageDialog(null, "Socio modificado con éxito.");
                        mostrarSocios();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar el socio.");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar socio: " + ex.getMessage());
                    ex.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un socio para modificar.");
        }
    }

    public static void mostrarSocios() {
        // Limpia todas las filas de la tabla mediante setRowCount()
        Tabla.setRowCount(0);
        String consulta = "SELECT * FROM Socios";

        // Crea un bloque try para ejecutar la consulta
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            // Itera sobre cada fila del ResultSet mediante while
            while (rs.next()) {
                // Obtiene el valor de cada campos y crea un array de objetos para almacenar los valores
                Object[] fila = {
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("edad"),
                    rs.getString("direccion"),
                    rs.getString("telefono")
                };
                // Añade la fila a la tabla mediante addRow()
                Tabla.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar socios: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void buscarSociosPorNombre(String nombre) {
        String consulta = "SELECT * FROM Socios WHERE nombre = ?";
        // Crea un PreparedStatement con la consulta 
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            // Establece el primer parámetro de la consulta como un string con el nombre buscado
            pstmt.setString(1, nombre);
            // Ejecuta la consulta mediante executeQuery()
            resultado = pstmt.executeQuery();
            mostrarBusqueda(resultado);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar socios por nombre: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void buscarSociosPorApellidos(String apellidos) {
        String consulta = "SELECT * FROM Socios WHERE apellidos = ?";
        // Crea un PreparedStatement con la consulta 
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            // Establece el primer parámetro de la consulta como un string con el nombre buscado
            pstmt.setString(1, apellidos);
            // Ejecuta la consulta mediante executeQuery()
            resultado = pstmt.executeQuery();
            mostrarBusqueda(resultado);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar socios por apellidos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void mostrarBusqueda(ResultSet resultado) throws SQLException {
        // Limpia la tabla antes de mostrar resultados mediante setRowCount()
        Tabla.setRowCount(0); 
        // Itera sobre cada fila del ResultSet mediante while
        while (resultado.next()) {
            // Se declara un array de objetos para almacenar los datos de cada socio
            Object[] campos = new Object[6];
            campos[0] = resultado.getInt("id");
            campos[1] = resultado.getString("nombre");
            campos[2] = resultado.getString("apellidos");
            campos[3] = resultado.getInt("edad");
            campos[4] = resultado.getString("direccion");
            campos[5] = resultado.getString("telefono");
            // Añade la fila a la tabla mediante addRow()
            Tabla.addRow(campos);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GestionSocios().setVisible(true);
        });
    }
}
