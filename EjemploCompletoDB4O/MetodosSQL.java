/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db4ojorge;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge
 */
public class MetodosSQL {

    // Define la variable final de tipo String 'URL'
    private static final String URL = "jdbc:mysql://localhost:3306/";
    // Define la variable final de tipo String 'USER' y se le asocia el valor 'root'
    private static final String USER = "root";
    // Define la variable final de tipo String 'PASSWORD'
    private static final String PASSWORD = "";

    /**
     * Devuelve una matriz con los nombres de las tablas de la base de datos
     * dada.
     *
     * @param nombreBaseDatos Nombre de la base de datos
     * @return Matriz de nombres de las tablas
     */
    public static String[] obtenerNombresTablas(String nombreBaseDatos) throws SQLException {
        String[] nombresTablas;
        // Preparar conexion y statement
        Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, USER, PASSWORD);
        Statement sentencia = conexion.createStatement();
        // Ejecutar sentencia
        ResultSet resultado = sentencia.executeQuery("SHOW TABLES");
        // Contar el numero de tablas
        int numeroTablas = 0;
        while (resultado.next()) {
            numeroTablas++;
        }
        // Inicializar la matriz con dimensiones equivalentes al numero de tablas
        nombresTablas = new String[numeroTablas];
        // Volver a ejecutar la sentencia para recorrer el resultado de los nombres de las tablas
        resultado = sentencia.executeQuery("SHOW TABLES");
        // Iterar el resultado con el bucle
        int i = 0;
        while (resultado.next()) {
            //Agregar a la matriz el resultado actual
            nombresTablas[i] = resultado.getString(1);
            i++;
        }
        // Cierra el 'resultset'
        resultado.close();
        // Cierra el 'statement' 
        sentencia.close();
        // Cierra la 'conn' 
        conexion.close();
        return nombresTablas;
    }

    /**
     * Devuelve una matriz con los nombres de las tablas de la base de datos
     * dada.
     *
     * @param nombreBaseDatos Nombre de la base de datos donde se encuentra la
     * tabla
     * @param nombreTabla Nombre de la tabla que se quiere saber el nombre de
     * sus columnas
     * @return Matriz unidimensional de nombres de las columnas de una tabla
     */
    public static String[] obtenerNombresColumnas(String nombreBaseDatos, String nombreTabla) throws SQLException {
        String[] nombresColumnas = null;
        // Preparar conexion y statement
        Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, USER, PASSWORD);
        Statement sentencia = conexion.createStatement();
        DatabaseMetaData metaData = conexion.getMetaData();
        // Obtener el nombre de las columnas
        ResultSet resultado = metaData.getColumns(null, null, nombreTabla, null);

        // Declarar las dimensiones de la matriz con un numero equivalente al numero de columnas que hay en la tabla
        nombresColumnas = new String[obtenerNumeroColumnasTabla(nombreBaseDatos, nombreTabla)];
        // Declarar contador
        int i = 0;
        // Iterar sobre el resultado y guardarlo en la matriz
        while (resultado.next()) {
            nombresColumnas[i] = resultado.getString(4);
            //System.out.println(resultado.getString(4));
            i++;
        }

        // Cierra el 'resultset'
        resultado.close();
        // Cierra el 'statement' 
        sentencia.close();
        // Cierra la 'conn' 
        conexion.close();

        return nombresColumnas;
    }

    /**
     * Devuelve un numero entero correspondiente al numero de columnas de una
     * tabla
     *
     * @param nombreBaseDatos Nombre de la base de datos
     * @param nombreTabla Nombre de la tabla donde se van a contar las columnas
     * @return Numero entero correspondiente al numero de columnas
     */
    public static int obtenerNumeroColumnasTabla(String nombreBaseDatos, String nombreTabla) throws SQLException {
        // Preparar conexión y statement
        Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, USER, PASSWORD);
        Statement sentencia = conexion.createStatement();
        // Obtener metadatos de la base de datos
        DatabaseMetaData metaData = conexion.getMetaData();
        // Obtener el nombre de las columnas
        ResultSet resultado = metaData.getColumns(null, null, nombreTabla, null);

        // Contar el número de columnas en el resultado
        int numeroColumnas = 0;
        while (resultado.next()) {
            numeroColumnas++;
        }

        // Cierra el 'resultset'
        resultado.close();
        // Cierra el 'statement' 
        sentencia.close();
        // Cierra la 'conn' 
        conexion.close();

        return numeroColumnas;
    }
}
