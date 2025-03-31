/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.bbddconexion;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class BBDDConexion extends JFrame {

    static Connection conexion;
    static DefaultTableModel Tabla;
    static JTable tabla;
    static String nombre;
    static String fund;
    static ResultSet resultado;

    public BBDDConexion() {
        setTitle("Gestor de Amigos XML");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JButton boton1 = new JButton("Abre la tabla grupos");
        JButton botonActualizar = new JButton("Actualizar");
        JButton botonAnadir = new JButton("Añadir");
        JButton botonEliminar = new JButton("Eliminar");
 
        panel.add(boton1);
        panel.add(botonActualizar);
        panel.add(botonAnadir);
        panel.add(botonEliminar);
        add(panel, BorderLayout.NORTH);

        Tabla = new DefaultTableModel(new Object[]{"Nombre", "Fundacion"}, 0);
        tabla = new JTable(Tabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conexion("discografia");
                resultado = seleccionar("select * from grupos");
                mostrar(resultado);
            }
        });

        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabla.getSelectedRow();
                if (selectedRow >= 0) {
                    String newName = JOptionPane.showInputDialog("Nuevo nombre:");
                    String newFund = JOptionPane.showInputDialog("Nueva fundación:");
                    if (newName != null && newFund != null) {
                        String consulta = "update grupos set nombre='" + newName + "', fundacion='" + newFund + "' where nombre='" + tabla.getValueAt(selectedRow, 0) + "'";
                        actualizar(consulta);
                        resultado = seleccionar("select * from grupos");
                        mostrar(resultado);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una fila para modificar.");
                }
            }
        });

        botonAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = JOptionPane.showInputDialog("Nombre del grupo:");
                String fund = JOptionPane.showInputDialog("Fundación del grupo:");
                if (nombre != null && fund != null) {
                    String consulta = "insert into grupos (nombre, fundacion) values ('" + nombre + "', '" + fund + "')";
                    insertar(consulta);
                    resultado = seleccionar("select * from grupos");
                    mostrar(resultado);
                }
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabla.getSelectedRow();
                if (selectedRow >= 0) {
                    String consulta = "delete from grupos where nombre='" + tabla.getValueAt(selectedRow, 0) + "'";
                    borrar(consulta);
                    resultado = seleccionar("select * from grupos");
                    mostrar(resultado);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar.");
                }
            }
        });
    }

    public static void conexion(String bbdd) {
        try {
            String url1 = "jdbc:mysql://localhost:3306/discografia";
            String user = "root";
            String password = "";
            conexion = DriverManager.getConnection(url1, user, password);
            if (conexion != null) {
                System.out.println("Conectado a discográfica ... ");
            }
        } catch (SQLException ex) {
            System.out.println("error");
            ex.printStackTrace();
        }
    }

    public static ResultSet seleccionar(String consulta) {
        ResultSet devolverResultSet = null;
        try {
            Statement stm = conexion.createStatement();
            devolverResultSet = stm.executeQuery(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(BBDDConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return devolverResultSet;
    }

    public static void mostrar(ResultSet result) {
        try {
            Tabla.setRowCount(0); 
            ResultSetMetaData md = result.getMetaData();
            int colum = md.getColumnCount();

            while (result.next()) {
                Object[] row = new Object[2]; 

                row[0] = result.getString("nombre"); 
                row[1] = result.getInt("fundacion"); 

                Tabla.addRow(row); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(BBDDConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int insertar(String consulta) {
        int devolver = 0;
        try {
            Statement statement = conexion.createStatement();
            devolver = statement.executeUpdate(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(BBDDConexion.class.getName()).log(Level.SEVERE, null, ex);
            devolver = -1;
        }
        return devolver;
    }

    public static int actualizar(String consulta) {
        int devolver = 0;
        try {
            Statement stm = conexion.createStatement();
            devolver = stm.executeUpdate(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(BBDDConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return devolver;
    }

    public static int borrar(String consulta) {
        int devolver = 0;
        try {
            Statement stm = conexion.createStatement();
            devolver = stm.executeUpdate(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(BBDDConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return devolver;
    }

    public static void conexionCerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BBDDConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BBDDConexion().setVisible(true);
            }
        });
    }
}
