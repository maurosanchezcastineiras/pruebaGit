/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.biblioteca3;

import static com.mycompany.biblioteca3.GestionLibros.conexion;
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
import java.time.LocalDate;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aleja
 */
public class GestionPrestamos extends JFrame {

    private JTable tablaPrestamos;
    private DefaultTableModel Tabla;

    public GestionPrestamos() {
        setTitle("Gestión de Préstamos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("img\\logo.png");
        setIconImage(logo.getImage());

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton botonMostrarPrestamos = new JButton("Mostrar Préstamos");
        JButton botonRealizarPrestamo = new JButton("Realizar Préstamo");
        JButton botonDevolverPrestamo = new JButton("Devolver Préstamo");
        JButton botonMostrarPrestamosActuales = new JButton("Mostrar Préstamos Actuales");
        JButton botonContarLibrosPorSocio = new JButton("Contar Libros por Socio");
        JButton botonMostrarLibrosVencidos = new JButton("Mostrar Libros Vencidos");

        panelBotones.add(botonMostrarPrestamos);
        panelBotones.add(botonRealizarPrestamo);
        panelBotones.add(botonDevolverPrestamo);
        panelBotones.add(botonMostrarPrestamosActuales);
        panelBotones.add(botonContarLibrosPorSocio);
        panelBotones.add(botonMostrarLibrosVencidos);

        add(panelBotones, BorderLayout.NORTH);

        Tabla = new DefaultTableModel(new Object[]{"ID", "Libro ID", "Socio ID", "Fecha Inicio", "Fecha Fin"}, 0);
        tablaPrestamos = new JTable(Tabla);
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);
        add(scrollPane, BorderLayout.CENTER);

        conexion = Conexion();

        botonRealizarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarPrestamo();
            }
        });

        botonDevolverPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                devolverPrestamo();
            }
        });

        botonMostrarPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPrestamos();
            }
        });

        botonMostrarPrestamosActuales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPrestamosActuales();
            }
        });

        botonContarLibrosPorSocio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contarLibrosPorSocio(); 
            }
        });

        botonMostrarLibrosVencidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarLibrosVencidos();
            }
        });

    }

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

    private void realizarPrestamo() {
        String libroIDInput = JOptionPane.showInputDialog("Ingrese el ID del libro:");
        String socioIDInput = JOptionPane.showInputDialog("Ingrese el ID del socio:");

        if (libroIDInput == null || libroIDInput.trim().isEmpty() || socioIDInput == null || socioIDInput.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes rellenar ambos campos.");
            return;
        }

        int libroID;
        int socioID;

        try {
            libroID = Integer.parseInt(libroIDInput);
            socioID = Integer.parseInt(socioIDInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los IDs deben ser números.");
            return;
        }

        String consultaLibro = "SELECT COUNT(*) FROM Libros WHERE id = ?";
        String consultaSocio = "SELECT COUNT(*) FROM Socios WHERE id = ?";
        String consultaPrestamo = "INSERT INTO Prestamos (LibroID, SocioID, fechaInicio, fechaFin) VALUES (?, ?, ?, ?)";

        try {
            // Crea un bloque try para ejecutar la consulta
            try (PreparedStatement pstmtLibro = conexion.prepareStatement(consultaLibro)) {
                // Asigna el valor del id como primer parámetro en la consulta 
                pstmtLibro.setInt(1, libroID);
                // Ejecuta la consulta
                ResultSet rsLibro = pstmtLibro.executeQuery();
                // Comprueba si la consulta devolvió algún registro y si la cuenta de libros con el id proporcionado es 0
                if (rsLibro.next() && rsLibro.getInt(1) == 0) {
                    JOptionPane.showMessageDialog(this, "El ID del libro no existe.");
                    return;
                }
            }

            // Crea un bloque try para ejecutar la consulta
            try (PreparedStatement pstmtSocio = conexion.prepareStatement(consultaSocio)) {
                // Asigna el valor del id como primer parámetro en la consulta 
                pstmtSocio.setInt(1, socioID);
                // Ejecuta la consulta
                ResultSet rsSocio = pstmtSocio.executeQuery();
                // Comprueba si la consulta devolvió algún registro y si la cuenta de socios con el id proporcionado es 0
                if (rsSocio.next() && rsSocio.getInt(1) == 0) {
                    JOptionPane.showMessageDialog(this, "El ID del socio no existe.");
                    return;
                }
            }

            LocalDate fechaInicio = LocalDate.now();
            LocalDate fechaFin = fechaInicio.plusDays(30);

            // Crea un bloque try para ejecutar la consulta
            try (PreparedStatement pstmtPrestamo = conexion.prepareStatement(consultaPrestamo)) {
                pstmtPrestamo.setInt(1, libroID);
                pstmtPrestamo.setInt(2, socioID);
                pstmtPrestamo.setDate(3, java.sql.Date.valueOf(fechaInicio));
                pstmtPrestamo.setDate(4, java.sql.Date.valueOf(fechaFin));

                int resultado = pstmtPrestamo.executeUpdate();

                if (resultado > 0) {
                    JOptionPane.showMessageDialog(this, "Préstamo realizado exitosamente.");
                    mostrarPrestamos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al realizar el préstamo.");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al realizar el préstamo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void devolverPrestamo() {
        // Obtiene el índice de la fila seleccionada en la tabla de préstamos
        int selectedRow = tablaPrestamos.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int idPrestamo = (int) tablaPrestamos.getValueAt(selectedRow, 0);
            String consulta = "DELETE FROM Prestamos WHERE id = ?";
            int filas = 0;

            // Crea un PreparedStatement con la consulta para eliminar un préstamo en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Asigna el valor del id como primer parámetro en la consulta
                pstmt.setInt(1, idPrestamo);
                // Ejecuta la consulta
                filas = pstmt.executeUpdate();

                if (filas > 0) {
                    JOptionPane.showMessageDialog(null, "Préstamo devuelto exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo devolver el préstamo.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al devolver el préstamo: " + ex.getMessage());
                ex.printStackTrace();
            }

            mostrarPrestamos();
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un préstamo para devolver.");
        }
    }

    private void mostrarPrestamos() {
        // Limpia todas las filas de la tabla mediante setRowCount()
        Tabla.setRowCount(0);
        String consulta = "SELECT * FROM Prestamos";

        // Crea un bloque try para ejecutar la consulta
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            // Itera sobre cada fila del ResultSet mediante while
            while (rs.next()) {
                // Se declara un array de objetos para almacenar los datos de cada préstamo
                Object[] prestamo = new Object[5];
                prestamo[0] = rs.getInt("id");
                prestamo[1] = rs.getString("libroID");
                prestamo[2] = rs.getInt("socioID");
                prestamo[3] = rs.getString("fechaInicio");
                prestamo[4] = rs.getString("fechaFin");
                // Añade la fila a la tabla mediante addRow()
                Tabla.addRow(prestamo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar préstamos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void mostrarPrestamosActuales() {
        // Limpia todas las filas de la tabla mediante setRowCount()
        Tabla.setRowCount(0);
        String consulta = "SELECT * FROM Prestamos WHERE fechaFin >= DATE(NOW());";

        // Crea un bloque try para ejecutar la consulta
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            // Itera sobre cada fila del ResultSet mediante while
            while (rs.next()) {
                // Se declara un array de objetos para almacenar los datos de cada préstamo
                Object[] campos = new Object[5];
                campos[0] = rs.getInt("id");
                campos[1] = rs.getString("libroID");
                campos[2] = rs.getInt("socioID");
                campos[3] = rs.getString("fechaInicio");
                campos[4] = rs.getString("fechaFin");
                // Añade la fila a la tabla mediante addRow()
                Tabla.addRow(campos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al mostrar préstamos actuales: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void contarLibrosPorSocio() {
        JPanel panelSocio = new JPanel(new GridLayout(2, 2));
        JTextField SocioID = new JTextField(10);
        panelSocio.add(new JLabel("Socio ID:"));
        panelSocio.add(SocioID);

        int idSocio = JOptionPane.showConfirmDialog(this, panelSocio, "Ingrese el ID del socio", JOptionPane.OK_CANCEL_OPTION);
        if (idSocio != JOptionPane.OK_OPTION) {
            return;
        }

        int socioID;
        try {
            socioID = Integer.parseInt(SocioID.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID del socio debe ser un número.");
            return;
        }

        String consultaNombre = "SELECT nombre, apellidos FROM Socios WHERE id = ?;";
        String nombreSocio = null;
        String apellidoSocio = null;

        // Crea un PreparedStatement con la consulta 
        try (PreparedStatement pstmt = conexion.prepareStatement(consultaNombre)) {
            // Establece el primer parámetro de la consulta con el id del socio
            pstmt.setInt(1, socioID);
            // Ejecuta la consulta mediante executeQuery()
            ResultSet rs = pstmt.executeQuery();
            // Itera sobre cada fila del ResultSet
            if (rs.next()) {
                nombreSocio = rs.getString("nombre");
                apellidoSocio = rs.getString("apellidos");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un socio con ID " + socioID);
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar socio: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        String consulta = "SELECT COUNT(*) FROM Prestamos WHERE SocioID = ?;";
        // Crea un PreparedStatement con la consulta 
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            // Establece el primer parámetro de la consulta con el id del socio
            pstmt.setInt(1, socioID);
            // Ejecuta la consulta mediante executeQuery()
            ResultSet rs = pstmt.executeQuery();
            // Itera sobre cada fila del ResultSet
            if (rs.next()) {
                int numero = rs.getInt(1);
                JOptionPane.showMessageDialog(this, "Número de libros prestados a " + nombreSocio + " " + apellidoSocio + ": " + numero);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al contar libros: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarLibrosVencidos() {
        // Limpia todas las filas de la tabla mediante setRowCount()
        Tabla.setRowCount(0);
        String consulta = "SELECT id, libroID, socioID, fechaInicio, fechaFin FROM Prestamos WHERE fechaFin < DATE(NOW());";

        // Crea un Statement para ejecutar la consulta y obtiene el ResultSet para su ejecución
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            // Itera sobre cada fila del ResultSet
            while (rs.next()) {
                // Se declara un array de objetos para almacenar los datos de cada préstamo
                Object[] campos = new Object[5];
                campos[0] = rs.getInt("id");
                campos[1] = rs.getString("libroID");
                campos[2] = rs.getInt("socioID");
                campos[3] = rs.getString("fechaInicio");
                campos[4] = rs.getString("fechaFin");
                // Añade la fila a la tabla mediante addRow()
                Tabla.addRow(campos);
            }
            if (Tabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay libros que hayan superado la fecha de fin de préstamo.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al mostrar libros vencidos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionPrestamos frame = new GestionPrestamos();
            frame.setVisible(true);
        });
    }
}
