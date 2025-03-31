/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda_videojuegos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class Checkin {

    private int id;

    public Checkin(int id) {
        this.id = id;
        comprobarHora(); 
    }

    public LocalDate obtenerFechaDeEntrada() {
        LocalDate fechaEntrada = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/anmafe_games_bd", "root", "")) {
            String sql = "SELECT DATE(fecha_entrada) AS fecha FROM fichajes WHERE empleado_id=?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);

                try (ResultSet resultado = ps.executeQuery()) {
                    while (resultado.next()) {
                        fechaEntrada = resultado.getDate("fecha").toLocalDate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fechaEntrada;
    }

    public String obtenerHoraFichaje() {
        String horaFichaje = "";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/anmafe_games_bd", "root", "")) {
            String sql = "SELECT TIME(fecha_entrada) AS hora FROM fichajes WHERE empleado_id=?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);

                try (ResultSet resultado = ps.executeQuery()) {
                    while (resultado.next()) {
                        horaFichaje = resultado.getString("hora");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return horaFichaje;

    }

    public void comprobarHora() {
        LocalDate fechaEntrada = obtenerFechaDeEntrada();
        String horaFichaje = obtenerHoraFichaje();

        if (fechaEntrada != null && !fechaEntrada.toString().equals(horaFichaje)) {
            String motivo = JOptionPane.showInputDialog(null, "¿Por qué llegas tarde?");
            if (motivo != null) { 
                System.out.println("Motivo de llegada tarde: " + motivo);
                
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/anmafe_games_bd", "root", "")) {
                    String sql = "UPDATE fichajes SET comentarios = ? WHERE empleado_id = ? AND fecha_entrada = ?";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, motivo);
                        ps.setInt(2, id);
                        ps.setString(3, fechaEntrada.toString());
                        ps.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al actualizar la base de datos: " + e.getMessage());
                }
            } else {
                System.out.println("El usuario canceló la entrada del motivo.");
            }
        }
    }
}
