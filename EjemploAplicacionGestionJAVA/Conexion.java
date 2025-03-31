/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda_videojuegos;

import com.mysql.cj.protocol.Resultset;
import com.sun.jdi.connect.spi.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Federico
 */
public class Conexion {

//    private String url; 
//    private String usuario;
//    private String contraseÃ±a;
    private Connection conn = null;

    private String URL, usuario, clave;

    public Conexion(String URL, String usuario, String clave) {
        this.URL = URL;
        this.usuario = usuario;
        this.clave = clave;
    }

    /**
     * Comprueba si se pudo realizar la conexión y mostrará
     * el resultado en una ventana emergente
     */
    public void comprobarConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection conn = DriverManager.getConnection(URL, usuario, clave);

            if (conn != null) {
                JOptionPane.showMessageDialog(null, "Conexion exitosa");
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar: " + e.getMessage());
        }
    }
    
    /**
     * Crea un modelo con datos de acuerdo a la consulta sql
     * y lo añade a la tabla que pide como parámetro
     * 
     * @param sql String, consulta que ejecutará el método
     * @param tabla JTable, tabla donde se mostrarán los datos resultantes de la consulta 
     */
    public void consultarTabla(String sql, JTable tabla) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection(URL, usuario, clave);

            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet resultset = pst.executeQuery(sql);

            //Agrgar los datos a la tabla
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

            modelo.setRowCount(0);

            while (resultset.next()) {
                modelo.addRow(new Object[]{
                    resultset.getInt("id"),
                    resultset.getString("nombre"),
                    resultset.getString("fecha_lanzamiento"),
                    resultset.getInt("precio")
                });
            }

            conn.close();

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SQLException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage());
        }
    }

    
    public void agregarRegistro(String tabla, String[] nombresColumnas, Object[] valores) throws SQLException, ClassNotFoundException {
//        public void insertar(String tabla, String[] columnas, Object[] valores) throws SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        java.sql.Connection conn = DriverManager.getConnection(URL, usuario, clave);

        //Con este trozo obtenemos los nombres de las columnas
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet columnas = metaData.getColumns(null, null, tabla, null);

        // Contar el número de columnas
        int count = 0;
        while (columnas.next()) {
            count++;
        }

        // Volver a obtener los metadatos para reiniciar el cursor
        columnas = metaData.getColumns(null, null, tabla, null);

        //columnas = new String[count];

        int i = 0;
        while (columnas.next()) {
            //columnas[i] = nombresColumnas.getString("COLUMN_NAME");
            i++;
        }

        //Con este trozo de código creamos la sentencia sql
//        StringBuilder consulta = new StringBuilder("INSERT INTO ");
//        consulta.append(tabla).append(" (");
//        for (int i = 0; i < nombresColumnas.length; i++) {
//            consulta.append(columnas[i]);
//            if (i < nombresColumnas[i].length - 1) {
//                consulta.append(", ");
//            }
//        }
//        consulta.append(") VALUES (");
//        for (int i = 0; i < valores.length; i++) {
//            consulta.append("?");
//            if (i < valores.length - 1) {
//                consulta.append(", ");
//            }
//        }
//        consulta.append(")");
//
//        PreparedStatement pst = conn.prepareStatement(consulta.toString());
//        for (int i = 0; i < valores.length; i++) {
//            pst.setObject(i + 1, valores[i]);
//        }
//
//        pst.executeUpdate();
//        pst.close();
    }

}
