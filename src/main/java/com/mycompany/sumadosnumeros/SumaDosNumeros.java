/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sumadosnumeros;

import java.util.Scanner;

/**
 *
 * @author aleja
 */

public class SumaDosNumeros {
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("Calculadora de suma de dos números");
        
        System.out.print("Ingresa el primer número: ");
        
        int num1 = scanner.nextInt();

        
        System.out.print("Ingresa el segundo número: ");
        
        int num2 = scanner.nextInt();

        int suma = num1 + num2;

        System.out.println("La suma de " + num1 + " y " + num2 + " es igual a: " + suma);

        scanner.close();
    }
}