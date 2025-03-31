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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aleja
 */
public class GestionNivelAgua extends JFrame {

    static Connection conexion;
    static DefaultTableModel Tabla;
    static JTable tablaNivelAgua;

    public GestionNivelAgua() {
        setTitle("Gestión del nivel del agua");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("img\\logo.png");
        setIconImage(logo.getImage());

        JPanel panel = new JPanel();
        JButton botonMostrar = new JButton("Mostrar nivel mínimo de agua");
        JButton botonBaja = new JButton("Eliminar nivel mínimo de agua");
        JButton botonModificar = new JButton("Modificar nivel mínimo de agua");

        panel.add(botonMostrar);
        panel.add(botonBaja);
        panel.add(botonModificar);
        add(panel, BorderLayout.NORTH);

        Tabla = new DefaultTableModel(new Object[]{"ID", "Nivel mínimo ideal de agua"}, 0);
        tablaNivelAgua = new JTable(Tabla);
        JScrollPane scrollPane = new JScrollPane(tablaNivelAgua);
        add(scrollPane, BorderLayout.CENTER);

        conexion = Mysql.Conexion();

        botonBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EliminarNivelMinimoAgua();
            }
        });

        botonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarNivelMinimoAgua();
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarNivelMinimoAgua();
            }
        });
    }

    private void EliminarNivelMinimoAgua() {
        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaNivelAgua.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaNivelAgua.getValueAt(selectedRow, 0);
            String consulta = "DELETE FROM nivelAgua WHERE id = ?";
            int devolver = 0;

            // Crea un PreparedStatement con la consulta para eliminar un libro en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Asigna el valor del id como primer parámetro en la consulta 
                pstmt.setInt(1, id);
                // Ejecuta la consulta
                devolver = pstmt.executeUpdate();

                if (devolver > 0) {
                    JOptionPane.showMessageDialog(null, "nivel mínimo eliminado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el nivel mínimo.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar el nivel mínimo: " + ex.getMessage());
                ex.printStackTrace();
            }

            mostrarNivelMinimoAgua();

        } else {
            JOptionPane.showMessageDialog(null, "Selecciona el nivel mínimo para eliminar.");
        }
    }

    private void modificarNivelMinimoAgua() {

        double minima;

        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaNivelAgua.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaNivelAgua.getValueAt(selectedRow, 0);
            String nuevoMinimoInput = JOptionPane.showInputDialog("Nuevo nivel mínimo de agua:", tablaNivelAgua.getValueAt(selectedRow, 1));

            if (nuevoMinimoInput != null && !nuevoMinimoInput.trim().isEmpty()) {

                try {
                    minima = parseInt(nuevoMinimoInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                    return;
                }
                
                String consulta = "UPDATE nivelAgua SET nivelMinimo=? WHERE id=?";
                int devolver = 0;

                // Crea un PreparedStatement con la consulta para modificar un libro en la base de datos
                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setDouble(1, minima);
                    pstmt.setInt(4, id);
                    // Ejecuta la consulta
                    devolver = pstmt.executeUpdate();

                    if (devolver > 0) {
                        JOptionPane.showMessageDialog(null, "nivel mínimo modificados con éxito.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar los el nivel mínimo.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar el nivel mínimo: " + ex.getMessage());
                    ex.printStackTrace();
                }

                mostrarNivelMinimoAgua();
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona el nivel mínimo para modificar.");
        }
    }

    private void mostrarNivelMinimoAgua() {
        // Limpia todas las filas de la tabla mediante setRowCount()
        Tabla.setRowCount(0);
        String consulta = "SELECT * FROM nivelAgua";

        // Crea un bloque try para ejecutar la consulta
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            // Itera sobre cada fila del ResultSet mediante while
            while (rs.next()) {
                // Se declara un array de objetos para almacenar los datos de cada libro
                Object[] nivelAgua = new Object[6];
                nivelAgua[0] = rs.getInt("id");
                nivelAgua[1] = rs.getDouble("NivelMínimo");
                // Añade la fila a la tabla mediante addRow()
                Tabla.addRow(nivelAgua);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar el nivel mínimo de agua: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionNivelAgua frame = new GestionNivelAgua();
            frame.setVisible(true);
        });
    }
}

