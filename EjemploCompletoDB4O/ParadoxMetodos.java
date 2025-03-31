/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db4ojorge;

// Se importan todas las librerías necesarias
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oIOException;
import com.db4o.query.Query;
import static db4ojorge.MetodosSQL.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Define la clase 'ParadoxMetodos'
 *
 * @author Jorge
 */
public final class ParadoxMetodos {

    // Define la variable 'url' de tipo String
    private final String url;
    // Define la variable final de tipo String 'URL'
    private static final String URL = "jdbc:mysql://localhost:3306/";
    // Define la variable final de tipo String 'USER' y se le asocia el valor 'root'
    private static final String USER = "root";
    // Define la variable final de tipo String 'PASSWORD'
    private static final String PASSWORD = "";

    /**
     * Declara el constructor
     */
    public ParadoxMetodos() {
        this.url = "jdbc:paradox:///";
    }

    /**
     * Declara el método que obtiene la información presente en una base de
     * datos y la pega en archivos paradox
     *
     * @param nombreBaseDatos - nombre de la base de datos
     */
    /*public void SQLtoPARADOX(String nombreBaseDatos) {
        try {
            // Mediante el método 'getConnection' se realiza la conexión
            Connection con = DriverManager.getConnection(URL + nombreBaseDatos, USER, PASSWORD);
            // Obtiene los metadatos de la base de datos mediante el método 'getMetaData()'
            DatabaseMetaData metaData = con.getMetaData();
            // Obtiene un conjunto de resultados mediante el método 'getTables()' que contiene información sobre las tablas en la base de datos
            ResultSet tablas = metaData.getTables(nombreBaseDatos, null, "%", new String[]{"TABLE"});

            // Itera sobre cada tabla en el conjunto de resultados
            while (tablas.next()) {
                // Obtiene el nombre de la tabla actual mediante el método 'getString()'
                String nombreTabla = tablas.getString("TABLE_NAME");
                // Obtiene los datos de la tabla actual en forma de array bidimensional
                String[][] datosTablaArray = obtenerDatosTabla(nombreBaseDatos, nombreTabla);
                String[] columnas;

                // Verifica si hay datos en la tabla actual
                if (datosTablaArray != null && datosTablaArray.length > 0) {
                    // Crea una nueva consulta SQL para seleccionar todas las columnas de la tabla actual
                    ResultSetMetaData rsMetaData = con.createStatement().executeQuery("SELECT * FROM " + nombreTabla).getMetaData();
                    // Obtiene el número de columnas en el conjunto de resultados de la consulta SQL
                    int numColumnas = rsMetaData.getColumnCount();
                    // Crea un array de cadenas para almacenar los nombres de las columnas de la tabla actual
                    columnas = new String[numColumnas];

                    // Itera sobre cada columna de la tabla actual...
                    for (int i = 1; i <= numColumnas; i++) {
                        // ... y almacena sus nombres en un array
                        columnas[i - 1] = rsMetaData.getColumnName(i);
                    }
                    // Llamada al método 'crearTablas()' que crea la base de datos en la ruta especificada
                    crearTablas(nombreTabla, "jdbc:paradox:///" + System.getenv("AppData") + "\\softgenius\\px\\", columnas);
                    for (int i = 0; i < datosTablaArray.length; i++) {
                        if (datosTablaArray[i] != null) {
                            insertarAParadox(nombreTabla, columnas, datosTablaArray[i]);
                        }
                    }
                }
            }
            // Cierra el conjunto de resultados
            tablas.close();
            // Cierra la conexión
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, "Error al transferir datos de SQL a Paradox", ex);
        }
    }*/
 /*public void SQLtoPARADOX(String nombreBaseDatos) {
        try {
            // Mediante el método 'getConnection' se realiza la conexión
            Connection con = DriverManager.getConnection(URL + nombreBaseDatos, USER, PASSWORD);
            // Obtiene los metadatos de la base de datos mediante el método 'getMetaData()'
            DatabaseMetaData metaData = con.getMetaData();
            // Obtiene un conjunto de resultados mediante el método 'getTables()' que contiene información sobre las tablas en la base de datos
            ResultSet tablas = metaData.getTables(nombreBaseDatos, null, "%", new String[]{"TABLE"});

            // Itera sobre cada tabla en el conjunto de resultados
            while (tablas.next()) {
                // Obtiene el nombre de la tabla actual mediante el método 'getString()'
                String nombreTabla = tablas.getString("TABLE_NAME");
                // Obtiene los datos de la tabla actual en forma de array bidimensional
                String[][] datosTablaArray = obtenerDatosTabla(nombreBaseDatos, nombreTabla);
                String[] columnas;

                // Verifica si hay datos en la tabla actual
                if (datosTablaArray != null && datosTablaArray.length > 0) {
                    // Crea una nueva consulta SQL para seleccionar todas las columnas de la tabla actual
                    ResultSetMetaData rsMetaData = con.createStatement().executeQuery("SELECT * FROM " + nombreTabla).getMetaData();
                    // Obtiene el número de columnas en el conjunto de resultados de la consulta SQL
                    int numColumnas = rsMetaData.getColumnCount();
                    // Crea un array de cadenas para almacenar los nombres de las columnas de la tabla actual
                    columnas = new String[numColumnas];

                    // Itera sobre cada columna de la tabla actual...
                    for (int i = 1; i <= numColumnas; i++) {
                        // ... y almacena sus nombres en un array
                        columnas[i - 1] = rsMetaData.getColumnName(i);
                    }
                    // Llamada al método 'crearTablas()' que crea la base de datos en la ruta especificada
                    crearTablas(nombreTabla, "jdbc:paradox:///" + System.getenv("AppData") + "\\softgenius\\px\\", columnas);
                    for (int i = 0; i < datosTablaArray.length; i++) {
                        if (datosTablaArray[i] != null) {
                            insertarAParadox(nombreTabla, columnas, datosTablaArray[i]);
                        }
                    }
                }
            }
            // Cierra el conjunto de resultados
            tablas.close();
            // Cierra la conexión
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, "Error al transferir datos de SQL a Paradox", ex);
        }
    }*/
    public void SQLtoPARADOX(String nombreBaseDatos) throws SQLException, IOException {
        // Obtener nombres de las tablas de la base de datos
        String[] nombresTablas = obtenerNombresTablas(nombreBaseDatos);
        String[] nombresColumnas;

        // Establece la conexión mediante el método 'getConnection()'
        Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, USER, PASSWORD);

        // Variable para contar las consultas ejecutadas
        int consultasEjecutadas = 0;

        // Ahora, trabajar cada una de las tablas
        for (int i = 0; i < nombresTablas.length; i++) {
            nombresColumnas = obtenerNombresColumnas(nombreBaseDatos, nombresTablas[i]);
            // Llamada al método 'crearTablas()' que crea la base de datos en la ruta especificada
            crearTablas(nombresTablas[i], "jdbc:paradox:///" + System.getenv("AppData") + "\\softgenius\\px\\", nombresColumnas);

            String consulta = "SELECT * FROM " + nombresTablas[i];
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumnas = metaData.getColumnCount();

            // Acumular filas para insertar en lotes
            List<String[]> filasAInsertar = new ArrayList<>();
            int numeroRepeticiones = 1;

            while (resultSet.next()) {
                String[] fila = new String[numColumnas];
                for (int j = 1; j <= numColumnas; j++) {
                    fila[j - 1] = resultSet.getString(j);
                }
                filasAInsertar.add(fila);

                if (filasAInsertar.size() >= 50) { // Límite de filas por lote
                    insertarAParadox10(nombresTablas[i], nombresColumnas, filasAInsertar.toArray(new String[0][]));
                    filasAInsertar.clear();
                    consultasEjecutadas++;
                    if (consultasEjecutadas >= 50) {
                        // Guardar el estado y salir del método
                        guardarProgreso(nombresTablas[i], resultSet.getRow());
                        resultSet.close();
                        statement.close();
                        conexion.close();
                        return; // Salir del método para reiniciar la ejecución
                    }
                }
            }
            if (!filasAInsertar.isEmpty()) {
                insertarAParadox10(nombresTablas[i], nombresColumnas, filasAInsertar.toArray(new String[0][]));
                consultasEjecutadas++;
                if (consultasEjecutadas >= 50) {
                    // Guardar el estado y salir del método
                    guardarProgreso(nombresTablas[i], resultSet.getRow());
                    resultSet.close();
                    statement.close();
                    conexion.close();
                    return; // Salir del método para reiniciar la ejecución
                }
            }
        }
        // Cierra la 'conn'
        conexion.close();
    }

    private void guardarProgreso(String nombreTabla, int row) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("progreso.txt"))) {
            writer.write("tabla=" + nombreTabla + "\n");
            writer.write("fila=" + row + "\n");
        }    }

    private Map<String, Integer> cargarProgreso() {
        Map<String, Integer> progreso = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("progreso.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    progreso.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return progreso;
    }

    /**
     * Declara el método 'obtenerDatosTabla()'
     *
     * @param nombreBBDD - nombre de la base de datos
     * @param nombreTabla - nombre de la tabla
     * @return - devuelve los datos de las tablas
     */
    private static String[][] obtenerDatosTabla(String nombreBBDD, String nombreTabla) {
        // Inicializa la matriz de datos de la tabla como nula
        String[][] datosTabla = null;
        // Consulta SQL para seleccionar todos los datos de la tabla especificada
        String consultaTabla = "SELECT * FROM " + nombreTabla;

        try {
            // Mediante el método 'getConnection' se realiza la conexión
            Connection conexion = DriverManager.getConnection(URL + nombreBBDD, USER, PASSWORD);
            // Crea una declaración sql mediante el método 'createStatement()'
            Statement statement = conexion.createStatement();
            // Ejecuta la consulta SQL mediante el método 'executeQuery()'
            ResultSet resultSet = statement.executeQuery(consultaTabla);

            // Obtiene los metadatos del conjunto de resultados mediante el método 'getMetaData()'
            ResultSetMetaData metaData = resultSet.getMetaData();
            // Mediante el método 'getColumnCount()' determina el número de columnas
            int numColumnas = metaData.getColumnCount();

            // Inicializa un array para almacenar los datos de la tabla.
            datosTabla = new String[100][numColumnas];
            // Inicializa un índice para realizar un seguimiento de la fila actual
            int filaIndex = 0;
            // Itera sobre cada fila de resultados 
            while (resultSet.next() && filaIndex < 100) {
                // Itera sobre cada columna de la fila actual
                for (int i = 1; i <= numColumnas; i++) {
                    // Almacena el valor de cada columna en la matriz de datos
                    datosTabla[filaIndex][i - 1] = resultSet.getString(i);
                }
                // Incrementa el índice del array
                filaIndex++;
            }

            // Cierra el resultSet
            resultSet.close();
            // Cierra el statement
            statement.close();
            // Cierra la conexión
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Devuelve el valor de la matriz
        return datosTabla;
    }

    /**
     * Declara el método que inserta datos en las tablas
     *
     * @param nombreTabla - tabla de la base de datos
     * @param columnas - columnas de una tabla
     * @param valoresAInsertar - valores de una tabla
     */
    private void insertarAParadox10(String nombreTabla, String[] columnas, String[][] valoresAInsertar) {
        try {
            // Establece la conexión con la base de datos Paradox mediante el método 'getConnection()'
            Connection con = DriverManager.getConnection("jdbc:paradox:///" + System.getenv("AppData") + "\\softgenius\\px", "", "");

            // Construye la consulta SQL para insertar los valores en la tabla
            String sql = "INSERT INTO " + nombreTabla + " (";

            // Colocar los nombres de las columnas
            for (int i = 0; i < columnas.length; i++) {
                // Si es el ultimo indice de la matriz, agregar un parentesis para cerrar
                if (i == columnas.length - 1) {
                    sql += columnas[i] + ") ";
                } else {
                    sql += columnas[i] + ", ";
                }
            }
            sql += "VALUES (";
            for (int i = 0; i < valoresAInsertar.length; i++) {
                for (int j = 0; j < valoresAInsertar[i].length; j++) {
                    // Si es el ultimo indice de la matriz, agregar un parentesis para cerrar
                    if (i == valoresAInsertar.length - 1) {
                        sql += "'" + valoresAInsertar[i][j] + "')";
                    } else {
                        sql += "'" + valoresAInsertar[i][j] + "', ";
                    }
                }
                sql += ", ";
            }

            //System.out.println(sql);
            // Colocar los valores
            // Crea una declaración SQL para ejecutar la consulta mediante el método 'createStatement()'
            Statement stmt = con.createStatement();
            // Ejecuta la consulta SQL mediante el método 'executeUpdate()'
            int insertCount = stmt.executeUpdate(sql);
            // Imprime la consulta SQL y el número de filas insertadas
            //System.out.println(sql + " : " + insertCount);
            // Cierra el objeto Statement 
            stmt.close();
            // Cierra la conexión a la base de datos 
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Declara el método que inserta datos en las tablas
     *
     * @param nombreTabla - tabla de la base de datos
     * @param columnas - columnas de una tabla
     * @param valoresAInsertar - valores de una tabla
     */
    private void insertarAParadox(String nombreTabla, String[] columnas, String[] valoresAInsertar) {
        try {
            // Establece la conexión con la base de datos Paradox mediante el método 'getConnection()'
            Connection con = DriverManager.getConnection("jdbc:paradox:///" + System.getenv("AppData") + "\\softgenius\\px", "", "");

            // Construye la consulta SQL para insertar los valores en la tabla
            String sql = "INSERT INTO " + nombreTabla + " (";

            // Colocar los nombres de las columnas
            for (int i = 0; i < columnas.length; i++) {
                // Si es el ultimo indice de la matriz, agregar un parentesis para cerrar
                if (i == columnas.length - 1) {
                    sql += columnas[i] + ") ";
                } else {
                    sql += columnas[i] + ", ";
                }
            }
            sql += "VALUES (";
            for (int i = 0; i < valoresAInsertar.length; i++) {
                // Si es el ultimo indice de la matriz, agregar un parentesis para cerrar
                if (i == valoresAInsertar.length - 1) {
                    sql += "'" + valoresAInsertar[i] + "')";
                } else {
                    sql += "'" + valoresAInsertar[i] + "', ";
                }
            }

            //System.out.println(sql);
            // Colocar los valores
            // Crea una declaración SQL para ejecutar la consulta mediante el método 'createStatement()'
            Statement stmt = con.createStatement();
            // Ejecuta la consulta SQL mediante el método 'executeUpdate()'
            int insertCount = stmt.executeUpdate(sql);
            // Imprime la consulta SQL y el número de filas insertadas
            //System.out.println(sql + " : " + insertCount);
            // Cierra el objeto Statement 
            stmt.close();
            // Cierra la conexión a la base de datos 
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Declara el método para crear una tabla
     *
     * @param tabla - tabla de una base de datos
     * @param url - directorio donde se crean las tablas
     * @param columnas - columnas de una tabla
     */
    private static void crearTablas(String tabla, String url, String[] columnas) {
        try {
            // Construye la consulta SQL para crear la tabla si no existe
            StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tabla + " (");
            // Itera sobre el array de columnas 
            for (int i = 0; i < columnas.length; i++) {
                // Añade el nombre de la columna y el tipo de dato (VARCHAR(255)).
                sql.append(columnas[i]).append(" VARCHAR(255)");
                // Si no es la última columna...
                if (i < columnas.length - 1) {
                    // ...añade una coma y un espacio para separar las columnas.
                    sql.append(", ");
                }
            }
            // Cierra la definición de la tabla
            sql.append(")");

            // Mediante el método 'getConnection' se realiza la conexión
            try (Connection con = DriverManager.getConnection("jdbc:paradox:///" + System.getenv("AppData") + "\\softgenius\\px\\", "", ""); // Crea un objeto Statement para ejecutar la consulta SQL.
                     Statement stmt = con.createStatement()) {
                // Ejecuta la consulta SQL mediante el método 'executeUpdate()' para crear la tabla
                int updateCount = stmt.executeUpdate(sql.toString());
                // Imprime la consulta SQL y el número de filas afectadas.
                System.out.println(sql + " : " + updateCount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, "Error al crear la tabla " + tabla, ex);
        }
    }

    /**
     * Declara el método para crear una tabla
     *
     * @param tabla - tabla que se quiere crear
     */
    public static void crearTabla(String tabla, String url) {
        try {
            // Consulta sql para crear la tabla
            String sql = "CREATE TABLE IF NOT EXISTS " + tabla + " (nombreColumna VARCHAR(255), valorColumna VARCHAR(255))";

            // Establece la conexión mediante el método 'getConection()'
            try (Connection con = DriverManager.getConnection(url, "", ""); //Crea la declararación sql mediante 'createStatement()'
                     Statement stmt = con.createStatement()) {
                // Mediante el método 'executeUpdate()' se realiza una actualización y se almacena en 'insertCount'
                int updateCount = stmt.executeUpdate(sql);
                // Imprime por pantalla el valor de la variabla 'updateCount'
                System.out.println(sql + " : " + updateCount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, "Error al crear la tabla " + tabla, ex);
        }
    }

    /**
     * Declara el método para borrar una tabla
     *
     * @param tabla - tabla concreta de la base de datos
     */
    public void borrarTabla(String tabla) {
        try {
            // Mediante el método 'getConnection' se realiza la conexión
            Connection con = DriverManager.getConnection(url, "", "");
            // Crea una declaración sql mediante el método 'createStatement()'
            Statement stmt = con.createStatement();
            // Mediante el método 'execute()' realiza la consulta y borra la tabla si ya existe
            stmt.execute("DROP TABLE IF EXISTS " + tabla);
            // Cierra el 'statement'
            stmt.close();
            // Cierra la conexión
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Declara el método 'insertarEnTabla'
     *
     * @param tabla - tabla concreta de la base de datos
     * @param columnasTabla - columnas de una tabla
     * @param valores - los nuevos datos ingresados en la tabla
     */
    public void insertarEnTabla(String directorio, String tabla, String columnasTabla, String valores) {
        try {
            // Consulta sql que inserta los valores en la tabla
            String sql = "INSERT INTO " + tabla + " (" + columnasTabla + ") VALUES (" + valores + ")";
            // Mediante el método 'getConnection' se realiza la conexión
            Connection con = DriverManager.getConnection(url + directorio, "", "");
            // Crea una declaración sql mediante el método 'createStatement()'
            Statement stmt = con.createStatement();

            // Mediante el método 'executeUpdate()' se realiza una actualización y se almacena en 'insertCount'
            int insertCount = stmt.executeUpdate(sql);
            // Imprime por pantalla el valor de la variabla 'updateCount'
            System.out.println(sql + " : " + insertCount);

            // Cierra el 'statement'
            stmt.close();
            // Cierra la conexión
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Declara el método para actualizar una tabla
     *
     * @param tabla - tabla concreta de la base de datos
     * @param columna - columna de la tabla
     * @param valor - el valor ingresado
     */
    public final void actualizarTabla(String tabla, String columna, String valor) {
        try {
            // Consulta sql para actualizar la tabla
            String sql = "update " + tabla + " set " + columna + " = " + valor + " where recno()%5=0";
            // Mediante el método 'getConnection' se realiza la conexión
            Connection con = DriverManager.getConnection(url, "", "");
            // Crea una declaración sql mediante el método 'createStatement()'
            Statement stmt = con.createStatement();

            // Mediante el método 'executeUpdate()' se realiza una actualización y se almacena en 'updateCount'
            int updateCount = stmt.executeUpdate(sql);
            // Imprime por pantalla el valor de la variabla 'updateCount'
            System.out.println(sql + " : " + updateCount);

            // Cierra el 'statement'
            stmt.close();
            // Cierra la conexión
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Declara el método para mostrar la información de una tabla
     *
     * @param nombreTabla - tabla concreta de la base de datos
     */
    public void leerTabla(String nombreTabla, String direccionCarpeta) {
        try {
            // Mediante el método 'getConnection' se realiza la conexión
            Connection con = DriverManager.getConnection(url + direccionCarpeta, "", "");
            // Crea una declaración sql mediante el método 'createStatement()'
            Statement stmt = con.createStatement();
            // Ejecuta la consulta SQL mediante el método 'executeQuery()'
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + nombreTabla);
            // Obtiene los metadatos de la fila para obtener el número de columnas
            ResultSetMetaData metaData = rs.getMetaData();
            // Mediante el método 'getColumnCount()' se obtiene el número de columnas
            int columnCount = metaData.getColumnCount();
            // Mediante un bucle for itera sobre cada columna en la fila actual
            for (int i = 1; i <= columnCount; i++) {
                // Muestra nombres de las columnas
                System.out.print(metaData.getColumnName(i) + " ");
            }
            // Imprime una nueva línea después de imprimir todas las columnas de la fila
            System.out.println();
            // Mediante un bucle while itera sobre cada fila en el ResultSet
            while (rs.next()) {
                // Mediante un bucle for itera sobre cada columna en la fila actual
                for (int i = 1; i <= columnCount; i++) {
                    //System.out.print(rs.getObject(i) + " ");
                    // Imprime el valor de la columna actual
                    System.out.print(rs.getObject(i) + " ");
                }
                // Imprime una nueva línea después de imprimir todas las columnas de la fila
                System.out.println();
            }
            // Cierra el 'resultSet' 
            rs.close();
            // Cierra el 'statement' 
            stmt.close();
            // Cierra la conexión 
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Declara el método para modificar una tabla
     *
     * @param tabla - tabla concreta de la base de datos
     * @param nuevaColumna - nueva columna
     * @param tipoColumna - tipo de columna
     */
    public void modificarTabla(String tabla, String nuevaColumna, String tipoColumna) {
        try {
            // Copiar los datos de la tabla original
            ArrayList<ArrayList<Object>> datosTabla = new ArrayList<>();
            // Mediante el método 'getConnection' se realiza la conexión
            Connection con = DriverManager.getConnection(url, "", "");
            // Crea una declaración sql mediante el método 'createStatement()'
            Statement stmt = con.createStatement();
            // Ejecuta la consulta SQL mediante el método 'executeQuery()'
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tabla);
            // Guardar no solamente los campos de la tabla, sino la propia estructura de la tabla
            // La llamada rs.getMetaData() devuelve un objeto ResultSetMetaData, que contiene información
            // sobre la estructura de los resultados almacenados en el ResultSet
            ResultSetMetaData metaData = rs.getMetaData();
            // Mediante el método 'getColumnCount()' se obtiene el número de columnas
            int columnCount = metaData.getColumnCount();
            // Mediante un bucle while itera sobre cada fila en el ResultSet
            while (rs.next()) {
                // Crea una fila para almacenar los datos de la tabla original
                ArrayList<Object> fila = new ArrayList<>();
                // Itera sobre las columnas de la tabla original
                for (int i = 1; i <= columnCount; i++) {
                    // Agrega los valores a la fila
                    fila.add(rs.getObject(i));
                }
                // Agrega la fila a la lista de datos de la tabla
                datosTabla.add(fila);
            }
            // Cierra el 'resultSet' 
            rs.close();
            // Cierra el 'statement' 
            stmt.close();
            // Cierra la conexión 
            con.close();

            // Borra la tabla original mediante una llamada al método 'borrarTabla()'
            borrarTabla(tabla);

            // Mediante una consulta sql crea la nueva tabla con la columna añadida
            String sql = "CREATE TABLE " + tabla + " (";
            // Mediante un bucle for itera sobre cada columna en la fila actual
            for (int i = 1; i <= columnCount; i++) {
                // Continúa con la consulta y agrega las columnas existentes a la definición de la tabla
                sql += metaData.getColumnName(i) + " " + metaData.getColumnTypeName(i) + ", ";
            }
            // Agrega la nueva columna a la definición de la tabla
            sql += nuevaColumna + " " + tipoColumna + ")";
            // Imprime la consulta
            System.out.println(sql);
            // Crea la tabla con la nueva definición mediante una llamada al método 'crearTablaCopia()'
            crearTablaCopia(sql);

            // Inserta los datos de la tabla original en la nueva tabla
            // Este for actua igual que un for-each
            // La variable fila recorrerá cada elemento de la colección datosTabla
            for (ArrayList<Object> fila : datosTabla) {
                // Construye la cadena de valores para la inserción
                String valores = "";
                // Mediante un bucle for itera sobre cada elemento en la fila
                for (Object valor : fila) {
                    // Verifica si ya hay valores en la cadena 'valores'
                    if (valores.length() > 0) {
                        // Si ya hay valores, agrega una coma y un espacio antes de agregar el próximo valor
                        valores += ", ";
                    }
                    // Maneja adecuadamente los valores de tipo String para la inserción
                    // La expresión valor instanceof String devuelve true si el objeto referenciado por la variable valor es una instancia de la clase String. Si es así, significa que el valor es una cadena de texto.
                    if (valor instanceof String) {
                        // Si es una cadena, agrega comillas simples alrededor del valor antes de agregarlo a 'valores'
                        valores += "'" + valor + "'";
                    } else {
                        // Agrega el valor directamente a 'valores'
                        valores += valor;
                    }
                }
                // Construye la sentencia SQL de inserción en la tabla
                String insertSql = "INSERT INTO " + tabla + " VALUES (" + valores + ", '')";
                // Inserta los datos en la nueva tabla mediante una llamada al método 'insertarEnTablaCopia()'
                insertarEnTablaCopia(insertSql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Declara el método para insertar valores en una copia de una tabla
     *
     * @param sql - Consulta sql
     */
    private void insertarEnTablaCopia(String sql) {
        try {
            // Mediante el método 'getConnection' se realiza la conexión
            Connection con = DriverManager.getConnection(url, "", "");
            // Crea una declaración sql mediante el método 'createStatement()'
            Statement stmt = con.createStatement();

            // Mediante el método 'executeUpdate()' se realiza una actualización y se almacena en 'insertCount'
            int insertCount = stmt.executeUpdate(sql);
            // Imprime por pantalla el valor de la variabla 'updateCount'
            System.out.println(sql + " : " + insertCount);
            // Cierra el 'statement' 
            stmt.close();
            // Cierra la conexión 
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Declara el método que crea una copia de una tabla
     *
     * @param sql - Consulta sql
     */
    private void crearTablaCopia(String sql) {
        try {
            // Mediante el método 'getConnection' se realiza la conexión
            Connection con = DriverManager.getConnection(url, "", "");
            // Crea una declaración sql mediante el método 'createStatement()'
            Statement stmt = con.createStatement();

            // Mediante el método 'executeUpdate()' se realiza una actualización y se almacena en 'updateCount'
            int updateCount = stmt.executeUpdate(sql);
            // Imprime por pantalla el valor de la variabla 'updateCount'
            System.out.println(sql + " : " + updateCount);
            // Cierra el 'statement' 
            stmt.close();
            // Cierra la conexión
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParadoxMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Declara el método que copia la información de un archivo db4o y la pega
     * en un archivo paradox
     *
     * @param tabla - tabla de una base de datos
     */
    public void DB4OtoPARADOX(String tabla) {
        try {
            String rutaAppData = System.getenv("AppData");
            // Definir la ruta del archivo DB4O utilizando la variable de entorno %AppData%
            String rutaArchivoDB4O = rutaAppData + "\\softgenius\\" + tabla + ".softgenius";

            // Abrir la conexión al archivo DB4O
            ObjectContainer db = Db4o.openFile(rutaArchivoDB4O);

            try {
                // Crear la base de datos Paradox si no existe
                crearTabla(tabla, "jdbc:paradox:///" + rutaAppData + "\\softgenius\\4o\\");
                // Crear una consulta para obtener todos los objetos DatosTabla almacenados en la base de datos
                Query query = db.query();
                // Emplea el método 'constrain()' para limitar la consulta a objetos de la clase 'DatosTabla' 
                query.constrain(DatosTabla.class);
                // Mediante el método 'execute()' se ejecuta la consulta 
                ObjectSet<DatosTabla> resultado = query.execute();

                // Recorrer los resultados y agregarlos a la base de datos Paradox
                while (resultado.hasNext()) {
                    // Obtener el siguiente objeto DatosTabla
                    DatosTabla datos = resultado.next();
                    // Insertar los datos en la tabla Paradox
                    insertarEnTabla(rutaArchivoDB4O, tabla, "nombreColumna, valorColumna", "'" + datos.getNombreColumna() + "', '" + datos.getValorColumna() + "'");
                }
            } finally {
                // Cierra la conexión al archivo DB4O
                db.close();

            }
        } catch (Db4oIOException ex) {
            Logger.getLogger(ParadoxMetodos.class
                    .getName()).log(Level.SEVERE, "Error de DB4O", ex);

        } catch (Exception ex) {
            Logger.getLogger(ParadoxMetodos.class
                    .getName()).log(Level.SEVERE, "Error desconocido", ex);
        }
    }
}
