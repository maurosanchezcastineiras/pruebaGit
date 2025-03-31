/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db4ojorge;

/**
 *
 * @author aleja
 */
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ext.Db4oIOException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class db4o {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Descargar Datos SQL");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton buttonDescargar = new JButton("Descargar Datos");
        buttonDescargar.setBounds(75, 40, 150, 30);
        frame.add(buttonDescargar);

        buttonDescargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreBaseDeDatos = JOptionPane.showInputDialog(frame, "Ingrese el nombre de la base de datos:");
                if (nombreBaseDeDatos == null || nombreBaseDeDatos.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "El nombre de la base de datos no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nombreTabla = JOptionPane.showInputDialog(frame, "Ingrese el nombre de la tabla:");
                if (nombreTabla == null || nombreTabla.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "El nombre de la tabla no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                descargarDatosSQL(nombreBaseDeDatos, nombreTabla);
            }
        });

        frame.setVisible(true);
    }

    public static void descargarDatosSQL(String nombreBaseDeDatos, String nombreTabla) {
        String rutaAppData = System.getenv("AppData");
        File directorio = new File(rutaAppData + "\\softgenius\\4o");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        File archivoDB4O = new File(directorio, nombreTabla + ".softgenius");

        boolean archivoCreado = false;

        if (archivoDB4O.exists()) {
            System.out.println("El archivo " + nombreTabla + ".softgenius ya existe.");
            return;
        }

        String consultaSQL = "SELECT * FROM " + nombreTabla;

        try (Connection conn = DriverManager.getConnection(URL + nombreBaseDeDatos, USER, PASSWORD);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(consultaSQL)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumnas = metaData.getColumnCount();

            System.out.println("Procesando datos...");

            while (resultSet.next()) {
                for (int i = 1; i <= numColumnas; i++) {
                    String nombreColumna = metaData.getColumnName(i);
                    Object valorColumna = resultSet.getObject(i);
                    System.out.println("Insertando datos: " + nombreColumna + " = " + valorColumna);
                    insertarDatosDB4O(nombreTabla, nombreColumna, valorColumna);
                    archivoCreado = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error con la conexión a la base de datos :(");
            e.printStackTrace();
        }

        if (archivoCreado) {
            System.out.println("El archivo " + nombreTabla + ".softgenius ha sido creado correctamente.");
        } else {
            System.out.println("No se creó ningún archivo.");
        }
    }

    private static void insertarDatosDB4O(String nombreTabla, String nombreColumna, Object valorColumna) {
        // Crea una nueva instancia de 'File' que concatena el valor de 'nombreTabla' y la extensión del archivo
        String carpetaAppData = System.getenv("AppData");
        String rutaArchivoDB4O = carpetaAppData + "\\softgenius\\4o\\" + nombreTabla + ".softgenius";

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
