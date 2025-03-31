/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db4ojorge;

// Se importan todas las librerías necesarias
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ext.Db4oIOException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Define la clase 'descargarDatosSQL'
 *
 * @author Mauro
 */
public class descargarDatosSQL {

    // Define la variable final de tipo String 'URL'
    private static final String URL = "jdbc:mysql://localhost:3306/";
    // Define la variable final de tipo String 'USER' y se le asocia el valor 'root'
    private static final String USER = "root";
    // Define la variable final de tipo String 'PASSWORD'
    private static final String PASSWORD = "";

    /**
     * Declara el método 'descargarDatosSQL()'
     *
     * @param nombreBaseDeDatos - Contiene el nombre de la base de datos
     * @param nombreTabla - Contiene el nombre de la tabla
     */
    public static void descargarDatosSQL(String nombreBaseDeDatos, String nombreTabla) {
        // Crea una nueva instancia de 'File' que concatena el valor de 'nombreTabla' y la extensión del archivo
        String rutaAppData = System.getenv("AppData");
        File archivoDB4O = new File(rutaAppData + "\\softgenius\\4o\\" + nombreTabla + ".softgenius");

        boolean archivoCreado = false;

        // Si el archivo ya existe...
        if (archivoDB4O.exists()) {
            // ...Se muestra por pantalla un mensaje que lo indica
            System.out.println("El archivo " + nombreTabla + ".softgenius ya existe.");
            // Sale del método
            return;
        }

        // Consulta SQL que coge los datos de la tabla
        String consultaSQL = "SELECT * FROM " + nombreTabla;

        try {
            // Establece la conexión mediante el método 'getConnection()'
            Connection conn = DriverManager.getConnection(URL + nombreBaseDeDatos, USER, PASSWORD);
            // Crea una declaración SQL mediante el método 'createStatement()'
            Statement statement = conn.createStatement();
            // Ejecutar la consulta SQL y obtener el conjunto de resultados
            ResultSet resultSet = statement.executeQuery(consultaSQL);

            // Obtiene los metadatos de la fila para obtener el número de columnas
            ResultSetMetaData metaData = resultSet.getMetaData();
            // Mediante el método 'getColumnCount()' se obtiene el número de columnas
            int numColumnas = metaData.getColumnCount();

            // Mientras haya resultados...
            while (resultSet.next()) {
                // ...itera sobre el número de columnas
                for (int i = 1; i <= numColumnas; i++) {
                    // El método 'getColumnName()' obtiene el nombre de la columna en la posición 'i'
                    String nombreColumna = metaData.getColumnName(i);
                    // El método 'getObject()' obtiene el valor de la columna en la posición 'i'
                    Object valorColumna = resultSet.getObject(i);
                    // Llamada al método 'insertarDatosDB4O'
                    insertarDatosDB4O(nombreTabla, nombreColumna, valorColumna);
                    // Instancia la variable 'archivoCreado' en true
                    archivoCreado = true;
                }
            }

            // Cierra el 'resultSet' 
            resultSet.close();
            // Cierra el 'statement' 
            statement.close();
            // Cierra la 'conn' 
            conn.close();
        } catch (SQLException e) {
            // Imprime un mensaje de error
            System.out.println("Error con la conexión a la base de datos :(");
        }

        // Si el archivo ha sido creado...
        if (archivoCreado) {
            // Imprime un mensaje indicándolo
            System.out.println("El archivo " + nombreTabla + ".softgenius ha sido creado correctamente.");
        }
    }

    /**
     * Define el método 'insertarDatosDB4O'
     *
     * @param nombreTabla - variable que almacena el nombre de la tabla
     * @param nombreColumna - variable que almacena el nombre de la columna
     * @param valorColumna - variable que almacena el valor de la columna
     */
    private static void insertarDatosDB4O(String nombreTabla, String nombreColumna, Object valorColumna) {
        // Crea una nueva instancia de 'File' que concatena el valor de 'nombreTabla' y la extensión del archivo
        String carpetaAppData = System.getenv("AppData");
        String rutaArchivoDB4O = carpetaAppData + "\\softgenius\\4o" + nombreTabla + ".softgenius";

        File archivoDB4O = new File(rutaArchivoDB4O);
        // Crea una nueva instancia de 'DatosTabla'
        DatosTabla datos = new DatosTabla(nombreColumna, valorColumna);
        // Utiliza el método 'openFile()' para abrir el archivo db4o. Se concatena con la 
        // variable 'nombreTabla' para poder elegir la tabla deseada
        ObjectContainer db = Db4o.openFile(rutaArchivoDB4O);

        try {
            // Con el método 'store()'se almacenan el valor de la variable 'datos' en el objeto 'db'
            db.store(datos);
        } catch (Db4oIOException e) {
            e.printStackTrace();
        } finally {
            // Con el método 'close()' se cierra la base de datos
            db.close();
        }
    }
}
