/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.calculadora;

// Importación de la librería Scanner para poder leer los datos ingresados por el usuario.
import java.util.Scanner;

/**
 *
 * @author aleja
 */

// Define la clase Calculadora.
public class Calculadora {

    // Declaración del método main para poder ejecutar el programa correctamente.
    public static void main (String[] args) {
        
        // Creación de una nueva instancia de la clase Scanner para leer los datos ingresados por el usuario.
        Scanner scanner = new Scanner (System.in);
        
        // Imprime un mensaje que indica al usuario que debe ingresar el precio total.
        System.out.println("Ingrese el precio total del servivio:" );
        
        // Empleo del método "nextDouble()" de la clase Scanner que se utiliza para leer un valor de punto 
        // flotante de doble precisión que después se almacena en la variable "precioTotal". El número aquí 
        // ingresado será el coste total del servicio.
        double precioTotal = scanner.nextDouble();
        
        // Declaración del array "porcentaje" de números de punto flotante de doble precisión en el que hay 
        // ingresados 5 valores diferentes que representan los porcentajes a escoger.
        double[] porcentaje = {0.05, 0.10, 0.15, 0.20, 0.25};
        
        // Imprime el mensaje que indica al usuario que se van a mostrar los diferentes porcentajes que se 
        // pueden elegir. El caracter '\n' provoca un salto de línea y lo escrito posteriormente aparecerá 
        // en la siguiente línea cuando se ejecute el programa.
        System.out.print("Porcentaje de propina:\n " );
        
        // Implementación de un bucle for en el que se inicia una variable 'i' de tipo 'int' con valor inicial 
        // de '0'. Mientras 'i' sea menor que la longitud del array "porcentaje" esta se seguirá incrementando.
        for (int i = 0; i < porcentaje.length; i++) {
            
            // Imprime las diferentes opciones de porcentaje. Se introduce la variable 'i' más un punto y espacio 
            // para separarlo del porcentaje cuando se ejecute el programa. Después el array "porcentaje" se multiplica
            // por 100 para transformarlo de número decimal a porcentaje. Se agrega luego el símbolo '%' para indicar al
            // usuario que estos números se tratan de porcentajes.
            System.out.println((i + 1) + ". " + (porcentaje[i] * 100) + "%");
            
        }
        
        // Imprime una opción adicional que el usuario puede escoger. Se le suma 1 a los elementos del array "porcentaje" 
        // para ingresar una opción adicional (que en este caso es la sexta). Se añade un punto, un espacio y la palabra 
        // "Otro" para indicar al usuario que se trata de otra opción diferente a las indicadas anteriormente en el array.
        System.out.println((porcentaje.length + 1) + ". Otro");
        
        // Imprime el mensaje que solicita al usuario que ingrese una de las posibles opciones. Igual que antes, se le suma 
        // 1 a todos los elementos del array "porcentaje" para indicar hasta dónde llegan las opciones.
        System.out.print("Seleccione el porcentaje de propina (1 - " + (porcentaje.length + 1) + "): ");
        
        // Lee el valor entero ingresado y lo almacena en una variable llamada "opcion". Aquí se utiliza el método "nextInt()" 
        // de la clase Scanner para leer el valor entero introducido por el usuario. Este número será la opción elegida por éste.
        int opcion = scanner.nextInt();
        
        // Declaración de la variable "porcentajePropina" de tipo "double" (capacidad de almacenar valores de punto flotante de 
        // doble precisión). Se le asigna el valor '0.0' para indicar que el porcentaje inicial es del 0%.
        double porcentajePropina = 0.0;
        
        // Empleo de una estructura switch para dirigirse por los diferentes casos de la variable “opcion”.
        switch (opcion) {
            
            // Si el valor de la variable "opcion" es 1, el porcentaje de propina es de un 5%. Se utiliza la instrucción 
            // "break" para que el valor salga del bloque switch.
            case 1:
                porcentajePropina = 0.05;
                break;
                
            // Si el valor de la variable "opcion" es 2, el porcentaje de propina es de un 10%. Se utiliza la instrucción 
            // "break" para que el valor salga del bloque switch.
            case 2:
                porcentajePropina = 0.10;
                break;
                
            // Si el valor de la variable "opcion" es 3, el porcentaje de propina es de un 15%. Se utiliza la instrucción 
            // "break" para que el valor salga del bloque switch.
            case 3:
                porcentajePropina = 0.15;
                break;
                
            // Si el valor de la variable "opcion" es 4, el porcentaje de propina es de un 20%. Se utiliza la instrucción 
            // "break" para que el valor salga del bloque switch.
            case 4:
                porcentajePropina = 0.20;
                break;
                
            // Si el valor de la variable "opcion" es 5, el porcentaje de propina es de un 25%. Se utiliza la instrucción 
            // "break" para que el valor salga del bloque switch.
            case 5:
                porcentajePropina = 0.25;
                break;
                
            // Si el valor de la variable "opcion" es 6, el porcentaje de propina es establecido por el usuario.
            case 6:
                
                // Se le solicita al usuario el ingreso de otro porcentaje no presentado anteriormente.
                System.out.print("Ingrese un otro porcentaje diferente: ");
                
                // Empleo del método "nextDouble()" de la clase Scanner que se utiliza para leer un valor de punto flotante 
                // de doble precisión que después se almacena en la variable "porcentajePropina". El número aquí ingresado 
                // se divide entre 100 para convertirlo en decimal. Se utiliza la instrucción "break" para que el valor salga 
                // del bloque switch.
                porcentajePropina = scanner.nextDouble() / 100;
                
                break;
                
        }
        
        // Multiplica el precio total del servicio por el porcentaje de la propina para calcular la cantidad de propina. Este 
        // valor se almacena en la variable "propina" de tipo "double".
        double propina = precioTotal * porcentajePropina;
        
        // Suma el precio total del servicio más la propina para calcular el precio total incluyendo la propina. Este valor se
        // almacena en la variable "precioTotalConPropina" de tipo "double".
        double precioTotalConPropina = precioTotal + propina;
        
        // Imprime el mensaje que muestra el precio total del servicio. Aquí se toma el valor ingresado en la variable 
        // "precioTotal".
        System.out.println("Precio total del servicio:" + precioTotal + " €");
        
        // Imprime el mensaje que muestra la propina elegida tanto en forma de porcentaje como en euros. Aquí el valor de la 
        // variable "porcentajePropina" se multiplica por 100 para clacular el porcentaje.
        System.out.println("Propina elegida(" + (porcentajePropina * 100) + "%): " + propina + " €");
        
        // Imprime el mensaje que muestra el coste total del servicio incluyendo la propina. Se toma el valor ingresado en la 
        // variable "precioTotalConPropina".
        System.out.println("Precio total del servicio incluyendo la propina:" + precioTotalConPropina + " €");
        
        // Cierra el flujo del objeto Scanner que se ha utilizado para leer las entradas del usuaaario.
        scanner.close();
           
    }
}
