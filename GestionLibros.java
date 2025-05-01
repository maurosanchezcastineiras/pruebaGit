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
public class GestionLibros extends JFrame {

    static Connection conexion;
    static DefaultTableModel Tabla;
    static JTable tablaLibros;

    public GestionLibros() {
        setTitle("Gestión de Libros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("img\\logo.png");
        setIconImage(logo.getImage());

        JPanel panel = new JPanel();
        JButton botonMostrar = new JButton("Mostrar Libros");
        JButton botonAlta = new JButton("Dar de Alta");
        JButton botonBaja = new JButton("Dar de Baja");
        JButton botonModificar = new JButton("Modificar");
        JButton botonBuscarTitulo = new JButton("Buscar por Título");
        JButton botonBuscarEditorial = new JButton("Buscar por Editorial");

        JTextField buscarTitulo = new JTextField(10);
        JTextField buscarEditorial = new JTextField(10);

        panel.add(botonMostrar);
        panel.add(botonAlta);
        panel.add(botonBaja);
        panel.add(botonModificar);
        panel.add(new JLabel("Buscar Título:"));
        panel.add(buscarTitulo);
        panel.add(botonBuscarTitulo);
        panel.add(new JLabel("Buscar Editorial:"));
        panel.add(buscarEditorial);
        panel.add(botonBuscarEditorial);
        add(panel, BorderLayout.NORTH);

        Tabla = new DefaultTableModel(new Object[]{"ID", "Título", "Ejemplares", "Editorial", "Páginas", "Año"}, 0);
        tablaLibros = new JTable(Tabla);
        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        add(scrollPane, BorderLayout.CENTER);

        conexion = Conexion();

        botonAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darDeAltaLibro();
            }
        });

        botonBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darDeBajaLibro();
            }
        });

        botonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarLibro();
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarLibros();
            }
        });

        botonBuscarTitulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLibrosPorTitulo(buscarTitulo.getText());
            }
        });

        botonBuscarEditorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLibrosPorEditorial(buscarEditorial.getText());
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

    private void darDeAltaLibro() {

        int numEjemplares;
        int numPaginas;
        int anhoEdicion;

        String tituloInput = JOptionPane.showInputDialog("Título del libro:");
        String numEjemplaresInput = JOptionPane.showInputDialog("Número de ejemplares:");
        String editorialInput = JOptionPane.showInputDialog("Editorial del libro:");
        String numPaginasInput = JOptionPane.showInputDialog("Número de páginas:");
        String anhoEdicionInput = JOptionPane.showInputDialog("Año de edición:");

        if (tituloInput != null && !tituloInput.trim().isEmpty() && numEjemplaresInput != null && !numEjemplaresInput.trim().isEmpty() && editorialInput != null && !editorialInput.trim().isEmpty() && numPaginasInput != null && !numPaginasInput.trim().isEmpty() && anhoEdicionInput != null && !anhoEdicionInput.trim().isEmpty()) {

            try {
                numEjemplares = Integer.parseInt(numEjemplaresInput);
                numPaginas = Integer.parseInt(numPaginasInput);
                anhoEdicion = Integer.parseInt(anhoEdicionInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                return;
            }

            String consulta = "INSERT INTO Libros (titulo, numEjemplares, editorial, numPaginas, anhoEdicion) VALUES (?, ?, ?, ?, ?)";
            int resultado = 0;
            // Crea un PreparedStatement con la consulta para insertar un nuevo libro en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Va estableciendo los parámetros
                pstmt.setString(1, tituloInput);
                pstmt.setInt(2, numEjemplares);
                pstmt.setString(3, editorialInput);
                pstmt.setInt(4, numPaginas);
                pstmt.setInt(5, anhoEdicion);
                // Ejecuta la consulta 
                resultado = pstmt.executeUpdate();

                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "Libro añadido correctamente.");
                    mostrarLibros();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo añadir el libro.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar libro: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos.");
        }
    }

    private void darDeBajaLibro() {
        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaLibros.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaLibros.getValueAt(selectedRow, 0);
            eliminarPrestamosPorLibro(id);
            String consulta = "DELETE FROM Libros WHERE id = ?";
            int devolver = 0;

            // Crea un PreparedStatement con la consulta para eliminar un libro en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Asigna el valor del id como primer parámetro en la consulta 
                pstmt.setInt(1, id);
                // Ejecuta la consulta
                devolver = pstmt.executeUpdate();

                if (devolver > 0) {
                    JOptionPane.showMessageDialog(null, "Libro eliminado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el libro.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar libro: " + ex.getMessage());
                ex.printStackTrace();
            }

            mostrarLibros();

        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un libro para eliminar.");
        }
    }

    private void eliminarPrestamosPorLibro(int libroId) {
        String consulta = "DELETE FROM prestamos WHERE libroID = ?";
        // Crea un PreparedStatement con la consulta para eliminar un libro en la base de datos
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            // Asigna el valor del LibroID como primer parámetro en la consulta 
            pstmt.setInt(1, libroId);
            // Ejecuta la consulta
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar préstamos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void modificarLibro() {

        int nuevosEjemplares;
        int nuevasPaginas;
        int nuevoAnho;

        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaLibros.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaLibros.getValueAt(selectedRow, 0);
            String nuevoTituloInput = JOptionPane.showInputDialog("Nuevo título:", tablaLibros.getValueAt(selectedRow, 1));
            String nuevosEjemplaresInput = JOptionPane.showInputDialog("Nuevos ejemplares:", tablaLibros.getValueAt(selectedRow, 2));
            String nuevaEditorialInput = JOptionPane.showInputDialog("Nueva editorial:", tablaLibros.getValueAt(selectedRow, 3));
            String nuevasPaginasInput = JOptionPane.showInputDialog("Nuevas páginas:", tablaLibros.getValueAt(selectedRow, 4));
            String nuevoAnhoInput = JOptionPane.showInputDialog("Nuevo año de edición:", tablaLibros.getValueAt(selectedRow, 5));

            if (nuevoTituloInput != null && !nuevoTituloInput.trim().isEmpty() && nuevosEjemplaresInput != null && !nuevosEjemplaresInput.trim().isEmpty() && nuevaEditorialInput != null && !nuevaEditorialInput.trim().isEmpty() && nuevasPaginasInput != null && !nuevasPaginasInput.trim().isEmpty() && nuevoAnhoInput != null && !nuevoAnhoInput.trim().isEmpty()) {

                try {
                    nuevosEjemplares = Integer.parseInt(nuevosEjemplaresInput);
                    nuevasPaginas = Integer.parseInt(nuevasPaginasInput);
                    nuevoAnho = Integer.parseInt(nuevoAnhoInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                    return;
                }

                String consulta = "UPDATE Libros SET titulo=?, numEjemplares=?, editorial=?, numPaginas=?, anhoEdicion=? WHERE id=?";
                int devolver = 0;

                // Crea un PreparedStatement con la consulta para modificar un libro en la base de datos
                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setString(1, nuevoTituloInput);
                    pstmt.setInt(2, nuevosEjemplares);
                    pstmt.setString(3, nuevaEditorialInput);
                    pstmt.setInt(4, nuevasPaginas);
                    pstmt.setInt(5, nuevoAnho);
                    pstmt.setInt(6, id);
                    // Ejecuta la consulta
                    devolver = pstmt.executeUpdate();

                    if (devolver > 0) {
                        JOptionPane.showMessageDialog(null, "Libro modificado con éxito.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar el libro.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar libro: " + ex.getMessage());
                    ex.printStackTrace();
                }

                mostrarLibros();
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un libro para modificar.");
        }
    }

    private void mostrarLibros() {
        // Limpia todas las filas de la tabla mediante setRowCount()
        Tabla.setRowCount(0);
        String consulta = "SELECT * FROM Libros";

        // Crea un bloque try para ejecutar la consulta
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            // Itera sobre cada fila del ResultSet mediante while
            while (rs.next()) {
                // Se declara un array de objetos para almacenar los datos de cada libro
                Object[] libro = new Object[6];
                libro[0] = rs.getInt("id");
                libro[1] = rs.getString("titulo");
                libro[2] = rs.getInt("numEjemplares");
                libro[3] = rs.getString("editorial");
                libro[4] = rs.getInt("numPaginas");
                libro[5] = rs.getInt("anhoEdicion");
                // Añade la fila a la tabla mediante addRow()
                Tabla.addRow(libro);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar libros: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void buscarLibrosPorTitulo(String titulo) {
        if (titulo != null && !titulo.trim().isEmpty()) {
            // Limpia la tabla antes de mostrar resultados mediante setRowCount()
            Tabla.setRowCount(0);
            String consulta = "SELECT * FROM Libros WHERE titulo = ?";

            // Crea un PreparedStatement con la consulta 
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Establece el primer parámetro de la consulta como un string con el título buscado 
                pstmt.setString(1, titulo);
                // Ejecuta la consulta con executeQuery()
                ResultSet rs = pstmt.executeQuery();

                // Itera sobre cada fila del ResultSet mediante while
                while (rs.next()) {
                    // Se declara un array de objetos para almacenar los datos de cada libro
                    Object[] libro = new Object[6];
                    libro[0] = rs.getInt("id");
                    libro[1] = rs.getString("titulo");
                    libro[2] = rs.getInt("numEjemplares");
                    libro[3] = rs.getString("editorial");
                    libro[4] = rs.getInt("numPaginas");
                    libro[5] = rs.getInt("anhoEdicion");
                    // Añade la fila a la tabla mediante addRow()
                    Tabla.addRow(libro);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar por título: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Escribe un título para buscar.");
        }
    }

    private void buscarLibrosPorEditorial(String editorial) {
        if (editorial != null && !editorial.trim().isEmpty()) {
            // Limpia la tabla antes de mostrar resultados mediante setRowCount()
            Tabla.setRowCount(0);
            String consulta = "SELECT * FROM Libros WHERE editorial = ?";

            // Crea un PreparedStatement con la consulta 
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Establece el primer parámetro de la consulta como un string con la editorial buscada
                pstmt.setString(1, editorial);
                // Ejecuta la consulta con executeQuery()
                ResultSet rs = pstmt.executeQuery();

                // Itera sobre cada fila del ResultSet mediante while
                while (rs.next()) {
                    // Se declara un array de objetos para almacenar los datos de cada libro
                    Object[] campos = new Object[6];
                    campos[0] = rs.getInt("id");
                    campos[1] = rs.getString("titulo");
                    campos[2] = rs.getInt("numEjemplares");
                    campos[3] = rs.getString("editorial");
                    campos[4] = rs.getInt("numPaginas");
                    campos[5] = rs.getInt("anhoEdicion");
                    // Añade la fila a la tabla mediante addRow()
                    Tabla.addRow(campos);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar por editorial: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Escribe una editorial para buscar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionLibros frame = new GestionLibros();
            frame.setVisible(true);
        });
    }
}
