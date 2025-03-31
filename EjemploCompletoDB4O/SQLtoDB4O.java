/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db4ojorge;

// Se importan todas las librerías necesarias
import com.db4o.Db4o;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Define la clase 'SQLtoDB4O'
 *
 * @author Jorge
 */
public class SQLtoDB4O {
    // Define la variable final de tipo String 'URL'
    private static final String URL = "jdbc:mysql://localhost:3306/";
    // Define la variable final de tipo String 'USER' y se le asocia el valor 'root'
    private static final String USER = "root";
    // Define la variable final de tipo String 'PASSWORD'
    private static final String PASSWORD = "";

    /**
     * Declara el método para mostrar todos los datos de la tabla almacenados en DB4O
     * 
     * @param urlTabla - variable que contiene el directorio del archivo
     */
    public static void mostrarDatosDB4O(String urlTabla) {
        // Utiliza el método 'openFile()' para crear o abrir un archivo db4o. Se concatena con la 
        // variable 'urlTabla' para poder elegir la tabla deseada
        ObjectContainer db = Db4o.openFile(urlTabla + ".softgenius");

        try {
            // Crear una consulta para obtener todos los objetos DatosTabla almacenados en la base de datos
            Query query = db.query();
            // Emplea el método 'constrain()' para limitar la consulta a objetos de la clase 'DatosTabla' 
            query.constrain(DatosTabla.class);
            // Mediante el método 'execute()' se ejecuta la consulta 
            ObjectSet<DatosTabla> resultado = query.execute();

            // Muestra los datos recuperados en la consola
            while (resultado.hasNext()) {
                // Itera sobre el siguiente valor de 'datos'
                DatosTabla datos = resultado.next();
                // Imprime por pantalla el nombre de la columna y su valor
                System.out.println(datos.getNombreColumna() + ": " + datos.getValorColumna());
                // Imprime un mensaje de error
                System.err.println();
            }
        } finally {
            // Cierra la base de datos
            db.close();
        }
    }

    /**
     * Declara el método para obtener todos los campos de una fila basada en el ID y el nombre de la tabla
     * 
     * @param id - almacena los valores del id
     * @param nombreBBDD - variable que almacena el nombre de la base de datos
     * @param nombreTabla - variable que almacena el nombre de la tabla
     * @return - Devuelve el valor de 'campos'
     */
    public static String[] obtenerCamposFila(int id, String nombreBBDD, String nombreTabla) {
        // Se crea una lista 'campos' y se instancia en nula
        String[] campos = null;

        // Consulta SQL para seleccionar todos los campos de la fila basada en el ID
        String consultaFila = "SELECT * FROM " + nombreTabla + " WHERE " + obtenerNombrePrimeraColumna(nombreBBDD, nombreTabla) + " = ?";

        try {
            // Establece la conexión mediante el método 'getConnection()'
            Connection conexion = DriverManager.getConnection(URL + nombreBBDD, USER, PASSWORD);
            // Crea una declaración SQL mediante el método 'prepareStatement()'
            PreparedStatement statement = conexion.prepareStatement(consultaFila);
            // Se establece mediante el método 'setInt()' la posición 1 y el id
            statement.setInt(1, id);
            // Ejecutar la consulta SQL y obtener el conjunto de resultados
            ResultSet resultSet = statement.executeQuery();

            // Obtiene los metadatos de la fila para obtener el número de columnas
            ResultSetMetaData metaData = resultSet.getMetaData();
            // Mediante el método 'getColumnCount()' se obtiene el número de columnas
            int numColumnas = metaData.getColumnCount();

            // Si hay resultados, inicializar el arreglo de campos
            if (resultSet.next()) {
                // Crea una nueva instancia de la lista en la que se almacena el valor de 'numColumnas'
                campos = new String[numColumnas];
                // Obtener los valores de cada columna y guardarlos en el arreglo
                for (int i = 1; i <= numColumnas; i++) {
                    campos[i - 1] = resultSet.getString(i);
                }
            }

            // Cierra el 'resultSet' 
            resultSet.close();
            // Cierra el 'statement' 
            statement.close();
            // Cierra la 'conn' 
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Devuelve el valor de 'campos'
        return campos;

    }

    /**
     * Declara el método para obtener el nombre de la primera columna de una tabla
     * 
     * @param BBDD - variable que contiene la base de datos
     * @param nombreTabla - variable que almacena el nombre de la tabla
     * @return - Devuelve el valor de 'nombrePrimeraColumna'
     */
    private static String obtenerNombrePrimeraColumna(String BBDD, String nombreTabla) {
        // Se instancia la variable 'nombrePrimeraColumna' en nula
        String nombrePrimeraColumna = null;

        // Consulta SQL para obtener el nombre de la primera columna de la tabla
        String consultaColumna = "SELECT COLUMN_NAME "
                + "FROM INFORMATION_SCHEMA.COLUMNS "
                + "WHERE TABLE_NAME = ? "
                + "ORDER BY ORDINAL_POSITION LIMIT 1";

        try {
            // Establece la conexión mediante el método 'getConnection()'
            Connection conn = DriverManager.getConnection(URL + BBDD, USER, PASSWORD);

            // Crear una declaración SQL mediante el método 'prepareStatement()'
            PreparedStatement statement = conn.prepareStatement(consultaColumna);
            // Mediante el 'setString' se establecen como parámetros la posición 1 y la variable 'nombreTabla'
            statement.setString(1, nombreTabla);

            // Ejecutar la consulta SQL mediante el método 'executeQuery()'
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Mediante el 'getString' se establece como parámetro la variable 'COLUMN_NAME'
                nombrePrimeraColumna = resultSet.getString("COLUMN_NAME");
            }

            // Cierra el 'resultSet' 
            resultSet.close();
            // Cierra el 'statement' 
            statement.close();
            // Cierra la 'conn' 
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Se devuelve el valor de la variable 'nombrePrimeraColumna'
        return nombrePrimeraColumna;
    }

    /**
     * Declara el método para obtener todos los campos de todas las filas de una tabla
     * 
     * @param nombreBBDD - variable que almacena el nombre de la base de datos
     * @param nombreTabla - variable que almacena el nombre de la tabla
     * @return - Devuelve el valor de 'datosTabla'
     */
    public static String[][] obtenerDatosTabla(String nombreBBDD, String nombreTabla) {
        // Se establece una lista de listas 'datosTabla' en nula
        String[][] datosTabla = null;

        // Consulta SQL para seleccionar todos los campos de todas las filas de la tabla
        String consultaTabla = "SELECT * FROM " + nombreTabla;

        try {
            // Establece la conexión mediante el método 'getConnection()'
            Connection conexion = DriverManager.getConnection(URL + nombreBBDD, USER, PASSWORD);
            // Crea una declaración SQL mediante el método 'createStatement()'
            Statement statement = conexion.createStatement();
            // Ejecuta la consulta SQL mediante el método 'executeQuery()' y se obtiene 
            // el conjunto de resultados
            ResultSet resultSet = statement.executeQuery(consultaTabla);

            // Obtiene metadatos de la tabla 
            ResultSetMetaData metaData = resultSet.getMetaData();
            // Mediante el método 'getColumnCount()' se obtiene el número de columnas
            int numColumnas = metaData.getColumnCount();

            // Inicializar la lista para almacenar los datos de la tabla
            List<String[]> listaDatos = new ArrayList<>();

            // Iterar sobre los resultados y almacenar los datos en la lista
            while (resultSet.next()) {
                // Se crea una nueva instancia de la lista en donde se almacena el valor 
                // de la variable 'numColumnas'
                String[] fila = new String[numColumnas];
                // Mediante el bucle for se itera sobre la lista
                for (int i = 1; i <= numColumnas; i++) {
                    // 
                    fila[i - 1] = resultSet.getString(i);
                }
                // Mediante el método 'add()' se añade el valor de la variable 'fila' a 'listaDatos'
                listaDatos.add(fila);
            }

            // Convierte la lista a una matriz de cadenas
            datosTabla = listaDatos.toArray(new String[0][]);

            // Cierra el 'resultSet' 
            resultSet.close();
            // Cierra el 'statement' 
            statement.close();
            // Cierra la 'conn' 
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Se devuelve el valor de la variable 'datosTabla'
        return datosTabla;
    }
}
