/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jardines;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author aleja
 */
public class PDF {
    // Método para exportar gráficas a PDF
    public void exportarPDF(List<JFreeChart> graficas, String rutaArchivo) {
        try (PDDocument document = new PDDocument()) {
            // para cada grafica creo una página
            for (JFreeChart grafica : graficas) {
                PDPage page = new PDPage();
                document.addPage(page);
                // convierte la gráfica a una imagen
                BufferedImage bufferedImage = grafica.createBufferedImage(500, 300);
                File tempFile = File.createTempFile("temp", ".png");
                ImageIO.write(bufferedImage, "PNG", tempFile);
                // Crea un objeto PDImageXObject a partir del archivo temporal
                PDImageXObject image = PDImageXObject.createFromFile(tempFile.getAbsolutePath(), document);
                // e añade la imagen al PDF
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.drawImage(image, 50, 500); 
                }
                tempFile.delete();
            }
            // se guarda el pdf
            document.save(rutaArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}