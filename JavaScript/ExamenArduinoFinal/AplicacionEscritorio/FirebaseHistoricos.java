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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseHistoricos extends JFrame {

    private Firestore db;
    private int jardinId;
    private List<JFreeChart> graficas = new ArrayList<>();

    public FirebaseHistoricos(int jardinId) {
        this.jardinId = jardinId;
        setTitle("Datos de los jardines - Jardín " + jardinId);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(mainScrollPane);
        cargarFirestore();
        cargarDatos();

        // se pregunta al usuario si desea exportar a PDF
        int quieresExportarPDF = JOptionPane.showConfirmDialog(this,"¿Quieres exportar los datos en PDF?",null,JOptionPane.YES_NO_OPTION);
        if (quieresExportarPDF == JOptionPane.YES_OPTION) {
            exportarPDF();
        }
    }

    // carga el json de firestore
    public void cargarFirestore() {
        try (InputStream json = getClass().getClassLoader().getResourceAsStream("ejemplo-219d9-firebase-adminsdk-5d81y-9e364be776.json")) {
            if (FirebaseApp.getApps().isEmpty()) {
                //xrea un objeto FirebaseOptions para la configuracion de firebase
                FirebaseOptions fb = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(json)).setDatabaseUrl("https://ejemplo-219d9.firebaseio.com").build();
                FirebaseApp.initializeApp(fb);
            }
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al inicializar Firebase: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // carga los datos desde Firestore
    public void cargarDatos() {
        Firestore firestore = getDb();
        JPanel graficos = new JPanel();
        graficos.setLayout(new BoxLayout(graficos, BoxLayout.Y_AXIS));
        try {
            // filtra los documentos de la coleccion por jardin_id
            ApiFuture<QuerySnapshot> datos = firestore.collection("arduino").whereEqualTo("jardin_id", jardinId).get();
            QuerySnapshot querySnapshot = datos.get();
            // Itera sobre cada documento
            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                Map<String, Object> jardines = document.getData();
                // se crea un dataset para almacenar los datos 
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                // se añaden los datos al dataset
                String humedad = jardines.containsKey("humedad") ? String.valueOf(jardines.get("humedad")) : "0";
                String temperatura = jardines.containsKey("temperatura") ? String.valueOf(jardines.get("temperatura")) : "0";
                String nivelAgua = jardines.containsKey("nivelAgua") ? String.valueOf(jardines.get("nivelAgua")) : "0";
                String humedadSuelo = jardines.containsKey("humedadSuelo") ? String.valueOf(jardines.get("humedadSuelo")) : "0";
                String indiceCalor = jardines.containsKey("indiceCalor") ? String.valueOf(jardines.get("indiceCalor")) : "0";
                dataset.addValue(Double.parseDouble(humedad), "Humedad", "Valor");
                dataset.addValue(Double.parseDouble(temperatura), "Temperatura", "Valor");
                dataset.addValue(Double.parseDouble(nivelAgua), "Nivel Agua", "Valor");
                dataset.addValue(Double.parseDouble(humedadSuelo), "Humedad Suelo", "Valor");
                dataset.addValue(Double.parseDouble(indiceCalor), "Índice Calor", "Valor");
                /// gráfica 
                JFreeChart graficaDatos = ChartFactory.createBarChart(null,"Variables","Valores",dataset);
                // se añade la gráfica a la lista de gráficas
                graficas.add(graficaDatos);
                // Añadela gráfica al panel
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ChartPanel cp = new ChartPanel(graficaDatos);
                        graficos.add(cp);
                        revalidate();
                    }
                });
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        JScrollPane scrollPane = new JScrollPane(graficos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void exportarPDF() {
        JFileChooser destino = new JFileChooser();
        destino.setSelectedFile(new File("Datos del jardín " + jardinId + ".pdf"));
        int DESTINO = destino.showSaveDialog(this);

        if (DESTINO == JFileChooser.APPROVE_OPTION) {
            File guardar = destino.getSelectedFile();
            String rutaArchivo = guardar.getAbsolutePath();
            PDF pdf = new PDF();
            pdf.exportarPDF(graficas, rutaArchivo);
            JOptionPane.showMessageDialog(this, "PDF guardado en: " + rutaArchivo, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public Firestore getDb() {
        return db;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FirebaseHistoricos app = new FirebaseHistoricos(1);
            app.setVisible(true);
        });
    }
}
