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
public class GestionRangosTemperatura extends JFrame {

    static Connection conexion;
    static DefaultTableModel Tabla;
    static JTable tablaRangosTemperatura;

    public GestionRangosTemperatura() {
        setTitle("Gestión de los rangos de la temperatura");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("img\\logo.png");
        setIconImage(logo.getImage());

        JPanel panel = new JPanel();
        JButton botonMostrar = new JButton("Mostrar rangos de la temperatura");
        JButton botonBaja = new JButton("Eliminar rangos de la temperatura");
        JButton botonModificar = new JButton("Modificar rangos de la temperatura");

        panel.add(botonMostrar);
        panel.add(botonBaja);
        panel.add(botonModificar);
        add(panel, BorderLayout.NORTH);

        Tabla = new DefaultTableModel(new Object[]{"ID", "Temperatura mínima ideal", "Temperatura máxima ideal"}, 0);
        tablaRangosTemperatura = new JTable(Tabla);
        JScrollPane scrollPane = new JScrollPane(tablaRangosTemperatura);
        add(scrollPane, BorderLayout.CENTER);

        conexion = Mysql.Conexion();

        botonBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EliminarRangosTemperatura();
            }
        });

        botonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarRangosTemperatura();
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRangosTemperatura();
            }
        });
    }

    private void EliminarRangosTemperatura() {
        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaRangosTemperatura.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaRangosTemperatura.getValueAt(selectedRow, 0);
            String consulta = "DELETE FROM rangosTemperatura WHERE id = ?";
            int devolver = 0;

            // Crea un PreparedStatement con la consulta para eliminar un libro en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Asigna el valor del id como primer parámetro en la consulta 
                pstmt.setInt(1, id);
                // Ejecuta la consulta
                devolver = pstmt.executeUpdate();

                if (devolver > 0) {
                    JOptionPane.showMessageDialog(null, "Rangos eliminado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar los rangos.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar los rangos: " + ex.getMessage());
                ex.printStackTrace();
            }

            mostrarRangosTemperatura();

        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un rango para eliminar.");
        }
    }

    private void modificarRangosTemperatura() {

        double minima;
        double maxima;

        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaRangosTemperatura.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaRangosTemperatura.getValueAt(selectedRow, 0);
            String nuevoMinimoInput = JOptionPane.showInputDialog("Nuevo rango mínimo:", tablaRangosTemperatura.getValueAt(selectedRow, 1));
            String nuevoMaximoInput   = JOptionPane.showInputDialog("Nuevos rango máximo:", tablaRangosTemperatura.getValueAt(selectedRow, 2));

            if (nuevoMinimoInput != null && !nuevoMinimoInput.trim().isEmpty() && nuevoMaximoInput != null && !nuevoMaximoInput.trim().isEmpty()) {

                try {
                    minima = parseInt(nuevoMinimoInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                    return;
                }

                try {
                    maxima = parseInt(nuevoMaximoInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                    return;
                }
                
                String consulta = "UPDATE rangosTemperatura SET idealMinimo=?, idealMaximo=? WHERE id=?";
                int devolver = 0;

                // Crea un PreparedStatement con la consulta para modificar un libro en la base de datos
                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setDouble(1, minima);
                    pstmt.setDouble(2, maxima);
                    pstmt.setInt(4, id);
                    // Ejecuta la consulta
                    devolver = pstmt.executeUpdate();

                    if (devolver > 0) {
                        JOptionPane.showMessageDialog(null, "Rangos modificados con éxito.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar los rangos.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar los rangos: " + ex.getMessage());
                    ex.printStackTrace();
                }

                mostrarRangosTemperatura();
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un rango para modificar.");
        }
    }

    private void mostrarRangosTemperatura() {
        // Limpia todas las filas de la tabla mediante setRowCount()
        Tabla.setRowCount(0);
        String consulta = "SELECT * FROM rangosTemperatura";

        // Crea un bloque try para ejecutar la consulta
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            // Itera sobre cada fila del ResultSet mediante while
            while (rs.next()) {
                // Se declara un array de objetos para almacenar los datos de cada libro
                Object[] rangosTemperaturas = new Object[6];
                rangosTemperaturas[0] = rs.getInt("id");
                rangosTemperaturas[1] = rs.getDouble("idealMinima");
                rangosTemperaturas[2] = rs.getDouble("idealMaxima");
                // Añade la fila a la tabla mediante addRow()
                Tabla.addRow(rangosTemperaturas);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los rangos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionRangosTemperatura frame = new GestionRangosTemperatura();
            frame.setVisible(true);
        });
    }
}

