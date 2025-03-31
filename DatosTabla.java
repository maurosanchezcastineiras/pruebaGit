/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db4ojorge;

// Se importan todas las librerías necesarias
import java.io.Serializable;

/**
 * Define la clase 'DatosTabla'
 * 
 * @author Mauro
 */
public class DatosTabla implements Serializable {
    // Define la variable de tipo String 'nombreColumna'
    private String nombreColumna;
    // Define la variable de tipo Object 'valorColumna'
    private Object valorColumna;

    /**
     * Declara el constructor
     * 
     * @param nombreColumna - variable que almacena el nombre de la columna
     * @param valorColumna - variable que almacena el valor de la columna
     */
    public DatosTabla(String nombreColumna, Object valorColumna) {
        // Instancia la variable 'nombreColumna'
        this.nombreColumna = nombreColumna;
        // Instancia la variable 'valorColumna'
        this.valorColumna = valorColumna;
    }

    /**
     * Declara el método 'getNombreColumna()'
     * 
     * @return - Devuelve el valor de la variable 'nombreColumna'
     */
    public String getNombreColumna() {
        return nombreColumna;
    }

    /**
     * Declara el método 'getValorColumna()'
     * @return - Devuelve el valor de la variable 'valorColumna'
     */
    public Object getValorColumna() {
        return valorColumna;
    }
}