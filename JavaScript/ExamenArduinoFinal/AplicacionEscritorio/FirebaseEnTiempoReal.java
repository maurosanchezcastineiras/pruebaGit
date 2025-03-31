/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jardines;

/**
 *
 * @author aleja
 */
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FirebaseEnTiempoReal extends JFrame {

    private Firestore db;
    private JLabel humedadLabel, temperaturaLabel, nivelAguaLabel, humedadSueloLabel, indiceCalorLabel;
    private boolean primeraCarga = true;

    public FirebaseEnTiempoReal() {
        setTitle("Monitorización de Jardines en Tiempo Real");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        humedadLabel = new JLabel("Humedad: --");
        temperaturaLabel = new JLabel("Temperatura: --");
        nivelAguaLabel = new JLabel("Nivel Agua: --");
        humedadSueloLabel = new JLabel("Humedad Suelo: --");
        indiceCalorLabel = new JLabel("Índice Calor: --");
        panel.add(humedadLabel);
        panel.add(temperaturaLabel);
        panel.add(nivelAguaLabel);
        panel.add(humedadSueloLabel);
        panel.add(indiceCalorLabel);
        add(panel, BorderLayout.CENTER);
        cargarFirestore();
        escucharDatosEnTiempoReal();
    }

    // carga el json de firestore
    public void cargarFirestore() {
        // json de firestore
        try (InputStream json = getClass().getClassLoader().getResourceAsStream("ejemplo-219d9-firebase-adminsdk-5d81y-9e364be776.json")) {
            if (FirebaseApp.getApps().isEmpty()) {
                //xrea un objeto FirebaseOptions para la configuracion de firebase
                FirebaseOptions fb = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(json)).setDatabaseUrl("https://ejemplo-219d9.firebaseio.com").build();
                FirebaseApp.initializeApp(fb);
            }
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error con Firebase: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void escucharDatosEnTiempoReal() {
        Firestore firestore = getDb();
        firestore.collection("arduino").addSnapshotListener((querySnapshot, error) -> {
            if (querySnapshot != null) {
                if (primeraCarga) {
                    primeraCarga = false;
                    return;
                }
                // Itera sobre cada documento nuevo
                for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                    Map<String, Object> jardines = document.getData();
                    // Actualiza los label con los nuevos datos
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            humedadLabel.setText("Humedad: " + (jardines.containsKey("humedad")));
                            temperaturaLabel.setText("Temperatura: " + (jardines.containsKey("temperatura")));
                            nivelAguaLabel.setText("Nivel Agua: " + (jardines.containsKey("nivelAgua")));
                            humedadSueloLabel.setText("Humedad Suelo: " + (jardines.containsKey("humedadSuelo") ));
                            indiceCalorLabel.setText("Índice Calor: " + (jardines.containsKey("indiceCalor")));
                        }
                    });
                }
            }
        });
    }
    public Firestore getDb() {
        return db;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FirebaseEnTiempoReal app = new FirebaseEnTiempoReal();
            app.setVisible(true);
        });
    }
}
