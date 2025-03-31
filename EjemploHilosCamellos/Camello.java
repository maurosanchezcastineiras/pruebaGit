/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.camellos;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author aleja
 */
public class Camello extends Thread {

    JButton camello;
    static boolean Ganador = false;

    public Camello(JButton boton) {
        this.camello = boton;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (!Ganador) { 
            int x = camello.getX() + random.nextInt(0, 10);  
            int maxX = 400 - camello.getWidth(); 

            if (x >= maxX) {
                x = maxX;
                Ganador = true;
                JOptionPane.showMessageDialog(camello, camello.getText() + " ha ganado!");
                break;
            }

            camello.setLocation(x, camello.getY());

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Camello.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void obtenerCamello(JButton jButton, String imagenUrl) {
        BufferedImage imagen = null;
        try {
            imagen = ImageIO.read(new URL("file:///" + imagenUrl));
            Image scaledImage = imagen.getScaledInstance(jButton.getWidth(), jButton.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon icono = new ImageIcon(scaledImage);
            jButton.setIcon(icono);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
