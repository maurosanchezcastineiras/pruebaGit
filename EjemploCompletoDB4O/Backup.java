/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db4ojorge;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Define la clase 'Backup'
 * 
 * @author Mauro
 */
public class Backup {

    /**
     * Inicio el constructor
     */
    public Backup() {
        // Ruta del archivo original
        String rutaBaseDatos1 = "metodopago.db4o";
         // Ruta del archivo copiado
        String rutaBaseDatos2 = "Backup/metodopago.db4o";

          // Llamada al método 'compararBasesDatos' y se almacena en la variable 'sonIguales'
        boolean sonIguales = compararBasesDatos(rutaBaseDatos1, rutaBaseDatos2);
         // Se inicia un bucle if...
        if (sonIguales) {
             // ...Si son iguales se imprime un mensaje indicándolo
            System.out.println("Las bases de datos son idénticas.");
        } else {
              // ...Si no son iguales se imprime un mensaje indicándolo
            System.out.println("Las bases de datos no son idénticas.");
        }
    }

    /**
     * Declara el método 'compararBasesDatos' que comparará byte a byte cada archivo
     * @param rutaBaseDatos1 - variable que contiene el directorio del archivo original
     * @param rutaBaseDatos2 - variable que contiene el directorio del archivo copia
     * @return - Devuelve un error
     */
    public static boolean compararBasesDatos(String rutaBaseDatos1, String rutaBaseDatos2) {
        try {
            // Llamada al método 'obtenerHashArchivo()' al que se le pasa la variable 'rutaBaseDatos1' 
            // y se almacena en la variable 'hash1'
            String hash1 = obtenerHashArchivo(rutaBaseDatos1);
             // Llamada al método 'obtenerHashArchivo()' al que se le pasa la variable 'rutaBaseDatos2' 
            // y se almacena en la variable 'hash2'
            String hash2 = obtenerHashArchivo(rutaBaseDatos2);
               // Mediante 'equals()' se compara el valor de 'hash1' y 'hash2'
            return hash1.equals(hash2);
        } catch (IOException | NoSuchAlgorithmException ex) {
            //ex.printStackTrace();
            return false;
        }
    }

    /**
     * Define el método 'obtenerHashArchivo' que creará el hash del archivo
     * @param rutaArchivo - variable que contiene la ruta del archivo
     * @return - Devuelve el valor de la variable 'hash'
     * @throws IOException
     * @throws NoSuchAlgorithmException 
     */
    private static String obtenerHashArchivo(String rutaArchivo) throws IOException, NoSuchAlgorithmException {
          // Se crea un objeto 'FileInputStream' al que se le pasa la variable 'rutaArchivo'
        try (InputStream is = new FileInputStream(rutaArchivo)) {
             // Se crea una instancia de 'MessageDigest' y con 'getInstance()' se le pasas como algoritmo "SHA-256". 
            // Con esto se calculará el hash del archivo
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
             // Se crea una nueva instancia de 'byte' a 8192 bytes y se almacena en la variable 'buffer'
            byte[] buffer = new byte[8192];
             // Se declara la variable de tipo int 'bytesRead'
            int bytesRead;
               // Con 'read()' se lee el valor de 'buffer' hasta que no haya más valores
            while ((bytesRead = is.read(buffer)) != -1) {
                  // Se actualiza 'digest' con el último valor de 'buffer' mediante 'update()'
                digest.update(buffer, 0, bytesRead);
            }
             // Se almacena la u´ltima instancia de 'digest' en la variabla 'hash'
            byte[] hash = digest.digest();
             // llamada al método 'Bytes'
            return Bytes(hash);
        }
    }

    /**
     * Declara el método 'Bytes' que recorrerá cada byte del archivo
     * @param bytes - lista que contiene todos los bytes
     * @return - Devuelve el valor de la variable 'resultado' en String
     */
    private static String Bytes(byte[] bytes) {
         // Se crea una instancia de 'StringBuilder' 'resultado'
        StringBuilder resultado = new StringBuilder();
        // Con un bucle for se itera sobre cada byte...
        for (byte b : bytes) {
            //... Se indica para reultado el formato de dos a dos caracteres
            resultado.append(String.format("%02x", b));
        }
        // Se devuelve el valor de 'resultado' en String
        return resultado.toString();
    }
}