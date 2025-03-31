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
public class GestionPersonal extends JFrame {

    static Connection conexion;
    static DefaultTableModel Tabla;
    static JTable tablaPersonal;

    public GestionPersonal() {
        setTitle("Gestión de los trabajadores");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("img\\logo.png");
        setIconImage(logo.getImage());

        JPanel panel = new JPanel();
        JButton botonMostrar = new JButton("Mostrar personal");
        JButton botonAlta = new JButton("Introducir personal");
        JButton botonBaja = new JButton("Eliminar personal");
        JButton botonModificar = new JButton("Modificar personal");
        JButton botonBuscarTrabajador = new JButton("Buscar por trabajador");
        JButton botonBuscarPorRol = new JButton("Buscar por rol");

        JTextField buscarTrabajador = new JTextField(10);
        JTextField buscarPorRol = new JTextField(10);

        panel.add(botonMostrar);
        panel.add(botonAlta);
        panel.add(botonBaja);
        panel.add(botonModificar);
        panel.add(new JLabel("Buscar trabajador:"));
        panel.add(buscarTrabajador);
        panel.add(botonBuscarTrabajador);
        panel.add(new JLabel("Buscar por rol:"));
        panel.add(buscarPorRol);
        panel.add(botonBuscarPorRol);
        add(panel, BorderLayout.NORTH);

        Tabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellidos", "Rol"}, 0);
        tablaPersonal = new JTable(Tabla);
        JScrollPane scrollPane = new JScrollPane(tablaPersonal);
        add(scrollPane, BorderLayout.CENTER);

        conexion = Mysql.Conexion();

        botonAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntroducirPersonal();
            }
        });

        botonBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EliminarPersonal();
            }
        });

        botonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarPersonal();
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPersonal();
            }
        });

        botonBuscarTrabajador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPersonalPorNombre(buscarTrabajador.getText());
            }
        });

        botonBuscarPorRol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientesPorRol(buscarPorRol.getText());
            }
        });
    }

    private void IntroducirPersonal() {

        int rol;
        String nombreInput = JOptionPane.showInputDialog("Nombre del trabajador:");
        String apellidosInput = JOptionPane.showInputDialog("Apellidos del trabajador:");
        String rolInput = JOptionPane.showInputDialog("Rol del trabajador:");

        if (nombreInput != null && !nombreInput.trim().isEmpty() && apellidosInput != null && !apellidosInput.trim().isEmpty()) {

            try {
                rol = parseInt(rolInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                return;
            }

            String consulta = "INSERT INTO trabajadores(nombre, apellidos, rol_asociado) VALUES (?, ?, ?)";
            int resultado = 0;
            // Crea un PreparedStatement con la consulta para insertar un nuevo libro en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Va estableciendo los parámetros
                pstmt.setString(1, nombreInput);
                pstmt.setString(2, apellidosInput);
                pstmt.setInt(3, rol);
                // Ejecuta la consulta 
                resultado = pstmt.executeUpdate();

                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "Trabajador añadido correctamente.");
                    mostrarPersonal();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo añadir el Trabajador.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar Trabajador: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos.");
        }
    }

    private void EliminarPersonal() {
        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaPersonal.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaPersonal.getValueAt(selectedRow, 0);
            String consulta = "DELETE FROM trabajadores WHERE id = ?";
            int devolver = 0;

            // Crea un PreparedStatement con la consulta para eliminar un libro en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Asigna el valor del id como primer parámetro en la consulta 
                pstmt.setInt(1, id);
                // Ejecuta la consulta
                devolver = pstmt.executeUpdate();

                if (devolver > 0) {
                    JOptionPane.showMessageDialog(null, "Trabajador eliminado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el Trabajador.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar Trabajador: " + ex.getMessage());
                ex.printStackTrace();
            }

            mostrarPersonal();

        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un Trabajador para eliminar.");
        }
    }

    private void modificarPersonal() {

        int nuevoRolTrabajador;

        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaPersonal.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaPersonal.getValueAt(selectedRow, 0);
            String nuevoNombreInput = JOptionPane.showInputDialog("Nuevo nombre:", tablaPersonal.getValueAt(selectedRow, 1));
            String nuevoPellidosInput   = JOptionPane.showInputDialog("Nuevos Apellidos:", tablaPersonal.getValueAt(selectedRow, 2));
            String nuevoRolInput = JOptionPane.showInputDialog("Nuevo Rol:", tablaPersonal.getValueAt(selectedRow, 3));

            if (nuevoNombreInput != null && !nuevoNombreInput.trim().isEmpty() && nuevoPellidosInput != null && !nuevoPellidosInput.trim().isEmpty()
                    && nuevoRolInput != null && !nuevoRolInput.trim().isEmpty()) {

                try {
                    nuevoRolTrabajador = parseInt(nuevoRolInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                    return;
                }

                String consulta = "UPDATE trabajadores SET nombre=?, apellidos=?, rol_asociado=? WHERE id=?";
                int devolver = 0;

                // Crea un PreparedStatement con la consulta para modificar un libro en la base de datos
                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setString(1, nuevoNombreInput);
                    pstmt.setString(2, nuevoPellidosInput);
                    pstmt.setInt(3, nuevoRolTrabajador);
                    pstmt.setInt(4, id);
                    // Ejecuta la consulta
                    devolver = pstmt.executeUpdate();

                    if (devolver > 0) {
                        JOptionPane.showMessageDialog(null, "Trabajadores modificado con éxito.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar el Trabajadores.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar cliente: " + ex.getMessage());
                    ex.printStackTrace();
                }

                mostrarPersonal();
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un Trabajador para modificar.");
        }
    }

    private void mostrarPersonal() {
        // Limpia todas las filas de la tabla mediante setRowCount()
        Tabla.setRowCount(0);
        String consulta = "SELECT * FROM trabajadores";

        // Crea un bloque try para ejecutar la consulta
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            // Itera sobre cada fila del ResultSet mediante while
            while (rs.next()) {
                // Se declara un array de objetos para almacenar los datos de cada libro
                Object[] trabajadores = new Object[6];
                trabajadores[0] = rs.getInt("id");
                trabajadores[1] = rs.getString("nombre");
                trabajadores[2] = rs.getString("apellidos");
                trabajadores[3] = rs.getInt("rol_asociado");
                // Añade la fila a la tabla mediante addRow()
                Tabla.addRow(trabajadores);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los Trabajadores: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void buscarPersonalPorNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            // Limpia la tabla antes de mostrar resultados mediante setRowCount()
            Tabla.setRowCount(0);
            String consulta = "SELECT * FROM trabajadores WHERE nombre = ?";

            // Crea un PreparedStatement con la consulta 
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Establece el primer parámetro de la consulta como un string con el título buscado 
                pstmt.setString(1, nombre);
                // Ejecuta la consulta con executeQuery()
                ResultSet rs = pstmt.executeQuery();

                // Itera sobre cada fila del ResultSet mediante while
                while (rs.next()) {
                    // Se declara un array de objetos para almacenar los datos de cada libro
                    Object[] trabajador = new Object[6];
                    trabajador[0] = rs.getInt("id");
                    trabajador[1] = rs.getString("nombre");
                    trabajador[2] = rs.getString("apellidos");
                    trabajador[3] = rs.getDouble("rol_asociado");
                    // Añade la fila a la tabla mediante addRow()
                    Tabla.addRow(trabajador);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar por trabakadores: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Escribe un trabakador para buscar.");
        }
    }

    private void buscarClientesPorRol(String rolInput) {

        int rol;

            try {
                rol = parseInt(rolInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                return;
            }
            // Limpia la tabla antes de mostrar resultados mediante setRowCount()
            Tabla.setRowCount(0);
            String consulta = "SELECT * FROM trabajadores WHERE rol_asociado = ?";

            // Crea un PreparedStatement con la consulta 
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Establece el primer parámetro de la consulta como un string con la editorial buscada
                pstmt.setInt(1, rol);
                // Ejecuta la consulta con executeQuery()
                ResultSet rs = pstmt.executeQuery();

                // Itera sobre cada fila del ResultSet mediante while
                while (rs.next()) {
                    // Se declara un array de objetos para almacenar los datos de cada libro
                    Object[] trabajadores = new Object[6];
                    trabajadores[0] = rs.getInt("id");
                    trabajadores[1] = rs.getString("nombre");
                    trabajadores[2] = rs.getString("apellidos");
                    trabajadores[3] = rs.getInt("rol_asociado");
                    // Añade la fila a la tabla mediante addRow()
                    Tabla.addRow(trabajadores);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar por rol: " + ex.getMessage());
                ex.printStackTrace();
            }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionPersonal frame = new GestionPersonal();
            frame.setVisible(true);
        });
    }
}

