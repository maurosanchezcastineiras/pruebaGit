/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jardines;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Double.parseDouble;
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
public class GestionArticulos extends JFrame {

    static Connection conexion;
    static DefaultTableModel Tabla;
    static JTable tablaArticulos;

    public GestionArticulos() {
        setTitle("Gestión de los articulos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("img\\logo.png");
        setIconImage(logo.getImage());

        JPanel panel = new JPanel();
        JButton botonMostrar = new JButton("Mostrar articulos");
        JButton botonAlta = new JButton("Introducir articulos");
        JButton botonBaja = new JButton("Eliminar articulos");
        JButton botonModificar = new JButton("Modificar articulos");
        JButton botonBuscarArticulo = new JButton("Buscar por articulo");
        JButton botonBuscarPorTipo = new JButton("Buscar por tipo");

        JTextField buscarArticulo = new JTextField(10);
        JTextField buscarPorTipo = new JTextField(10);

        panel.add(botonMostrar);
        panel.add(botonAlta);
        panel.add(botonBaja);
        panel.add(botonModificar);
        panel.add(new JLabel("Buscar artículo:"));
        panel.add(buscarArticulo);
        panel.add(botonBuscarArticulo);
        panel.add(new JLabel("Buscar por tipo:"));
        panel.add(buscarPorTipo);
        panel.add(botonBuscarPorTipo);
        add(panel, BorderLayout.NORTH);

        Tabla = new DefaultTableModel(new Object[]{"ID", "Artículo", "Precio", "Tipo"}, 0);
        tablaArticulos = new JTable(Tabla);
        JScrollPane scrollPane = new JScrollPane(tablaArticulos);
        add(scrollPane, BorderLayout.CENTER);

        conexion = Mysql.Conexion();

        botonAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntroducirArticulo();
            }
        });

        botonBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EliminarArticulo();
            }
        });

        botonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarArticulo();
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarArticulos();
            }
        });

        botonBuscarArticulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarArticuloPorNombre(buscarArticulo.getText());
            }
        });

        botonBuscarPorTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarArticulosPorTipo(buscarPorTipo.getText());
            }
        });
    }

    private void IntroducirArticulo() {
        double precio;
        int tipo;
        String articuloInput = JOptionPane.showInputDialog("Nombre del articulo:");
        String precioInput = JOptionPane.showInputDialog("Precio del artículo:");
        String tipoInput = JOptionPane.showInputDialog("Tipo del artículo:");

        if (articuloInput != null && !articuloInput.trim().isEmpty() && precioInput != null && !precioInput.trim().isEmpty() 
                && tipoInput != null && !tipoInput.trim().isEmpty()) {

            try {
                tipo = parseInt(tipoInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                return;
            }

            try {
                precio = parseDouble(precioInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                return;
            }
            
            String consulta = "INSERT INTO artículos (articulo, precio, clase_asociada) VALUES (?, ?, ?)";
            int resultado = 0;
            // Crea un PreparedStatement con la consulta para insertar un nuevo libro en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Va estableciendo los parámetros
                pstmt.setString(1, articuloInput);
                pstmt.setDouble(2, precio);
                pstmt.setInt(3, tipo);
                // Ejecuta la consulta 
                resultado = pstmt.executeUpdate();

                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "Artículo añadido correctamente.");
                    mostrarArticulos();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo añadir el Artículo.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar Artículo: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos.");
        }
    }

    private void EliminarArticulo() {
        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaArticulos.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaArticulos.getValueAt(selectedRow, 0);
            String consulta = "DELETE FROM articulos WHERE id = ?";
            int devolver = 0;

            // Crea un PreparedStatement con la consulta para eliminar un libro en la base de datos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Asigna el valor del id como primer parámetro en la consulta 
                pstmt.setInt(1, id);
                // Ejecuta la consulta
                devolver = pstmt.executeUpdate();

                if (devolver > 0) {
                    JOptionPane.showMessageDialog(null, "Artículo eliminado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el Artículo.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar Artículo: " + ex.getMessage());
                ex.printStackTrace();
            }

            mostrarArticulos();

        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un Artículo para eliminar.");
        }
    }

    private void modificarArticulo() {

        int nuevoTipoArticulo;
        double nuevoPrecio;

        // Obtiene el índice de la fila seleccionada en la tabla de libros
        int selectedRow = tablaArticulos.getSelectedRow();

        if (selectedRow >= 0) {
            // Obtiene el valor del id de la fila seleccionada y lo convierte a entero
            int id = (int) tablaArticulos.getValueAt(selectedRow, 0);
            String nuevoNombreInput = JOptionPane.showInputDialog("Nuevo artículo:", tablaArticulos.getValueAt(selectedRow, 1));
            String nuevoPrecioInput = JOptionPane.showInputDialog("Nuevo precio:", tablaArticulos.getValueAt(selectedRow, 2));
            String nuevoTipoInput = JOptionPane.showInputDialog("Nuevo tipo:", tablaArticulos.getValueAt(selectedRow, 3));

            if (nuevoNombreInput != null && !nuevoNombreInput.trim().isEmpty()&& nuevoPrecioInput != null && !nuevoPrecioInput.trim().isEmpty() && nuevoTipoInput != null && !nuevoTipoInput.trim().isEmpty()) {

                try {
                    nuevoTipoArticulo = parseInt(nuevoTipoInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                    return;
                }
                
                try {
                    nuevoPrecio = parseDouble(nuevoPrecioInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                    return;
                }

                String consulta = "UPDATE articulos SET articulo=?, precio=?, clase_asociada=? WHERE id=?";
                int devolver = 0;

                // Crea un PreparedStatement con la consulta para modificar un libro en la base de datos
                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setString(1, nuevoNombreInput);
                    pstmt.setDouble(2, nuevoPrecio);
                    pstmt.setInt(3, nuevoTipoArticulo);
                    pstmt.setInt(4, id);
                    // Ejecuta la consulta
                    devolver = pstmt.executeUpdate();

                    if (devolver > 0) {
                        JOptionPane.showMessageDialog(null, "Articulo modificado con éxito.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar el Articulo.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar Articulo: " + ex.getMessage());
                    ex.printStackTrace();
                }

                mostrarArticulos();
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un Articulo para modificar.");
        }
    }

    private void mostrarArticulos() {
        // Limpia todas las filas de la tabla mediante setRowCount()
        Tabla.setRowCount(0);
        String consulta = "SELECT * FROM articulos";

        // Crea un bloque try para ejecutar la consulta
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            // Itera sobre cada fila del ResultSet mediante while
            while (rs.next()) {
                // Se declara un array de objetos para almacenar los datos de cada libro
                Object[] articulo = new Object[6];
                articulo[0] = rs.getInt("id");
                articulo[1] = rs.getString("articulo");
                articulo[2] = rs.getDouble("precio");
                articulo[3] = rs.getInt("clase_asociada");
                // Añade la fila a la tabla mediante addRow()
                Tabla.addRow(articulo);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los Articulo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void buscarArticuloPorNombre(String articulo) {
        if (articulo != null && !articulo.trim().isEmpty()) {
            // Limpia la tabla antes de mostrar resultados mediante setRowCount()
            Tabla.setRowCount(0);
            String consulta = "SELECT * FROM articulos WHERE articulo = ?";

            // Crea un PreparedStatement con la consulta 
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Establece el primer parámetro de la consulta como un string con el título buscado 
                pstmt.setString(1, articulo);
                // Ejecuta la consulta con executeQuery()
                ResultSet rs = pstmt.executeQuery();

                // Itera sobre cada fila del ResultSet mediante while
                while (rs.next()) {
                    // Se declara un array de objetos para almacenar los datos de cada libro
                    Object[] Articulo = new Object[6];
                    Articulo[0] = rs.getInt("id");
                    Articulo[1] = rs.getString("articulo");
                    Articulo[2] = rs.getString("precio");
                    Articulo[3] = rs.getDouble("clase_asociada");
                    // Añade la fila a la tabla mediante addRow()
                    Tabla.addRow(Articulo);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar por articulo: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Escribe un articulo para buscar.");
        }
    }

    private void buscarArticulosPorTipo(String tipoInput) {

        int tipo;

            try {
                tipo = parseInt(tipoInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números.");
                return;
            }
            // Limpia la tabla antes de mostrar resultados mediante setRowCount()
            Tabla.setRowCount(0);
            String consulta = "SELECT * FROM articulos WHERE clase_asociada = ?";

            // Crea un PreparedStatement con la consulta 
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                // Establece el primer parámetro de la consulta como un string con la editorial buscada
                pstmt.setInt(1, tipo);
                // Ejecuta la consulta con executeQuery()
                ResultSet rs = pstmt.executeQuery();

                // Itera sobre cada fila del ResultSet mediante while
                while (rs.next()) {
                    // Se declara un array de objetos para almacenar los datos de cada libro
                    Object[] articulos = new Object[6];
                    articulos[0] = rs.getInt("id");
                    articulos[1] = rs.getString("articulo");
                    articulos[2] = rs.getString("precio");
                    articulos[3] = rs.getInt("clase_asociada");
                    // Añade la fila a la tabla mediante addRow()
                    Tabla.addRow(articulos);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar por tipo: " + ex.getMessage());
                ex.printStackTrace();
            }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionArticulos frame = new GestionArticulos();
            frame.setVisible(true);
        });
    }
}
