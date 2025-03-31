/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jardines;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author aleja
 */
public class Mysql {
    
    public static Connection Conexion() {
        try {
            String url = "jdbc:mysql://dam2.colexio-karbo.com:3333/jardin_msanchez";
            String usuario = "dam2";
            String contrasena = "Ka3b0134679";

            return DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
