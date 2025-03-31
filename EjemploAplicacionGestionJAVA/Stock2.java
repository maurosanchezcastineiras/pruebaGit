/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda_videojuegos;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Federico
 */
public class Stock2 extends JFrame{
    JButton btnVolver, btnMostrar, btnAñadirProductos;
    JPanel panelPrin, panel, panelDos;
    JComboBox<Object> cbTiendas, cbTiendasTabla, selectorCampos;
    JTable tabla;
    TableColumn columna;
    JScrollPane barra;
    ArrayList<String> productosTienda;
    DefaultTableModel modelo;
    
    private VentanaInicioSesion sesion;
    
    public  Stock2(){
        initComponents();
    }
    
    private void initComponents() {
        
        this.setSize(877, 655);
        this.panelPrin = new JPanel();
        this.panelPrin.setBackground(Color.red);
        this.panelPrin.setLayout(new BoxLayout(panelPrin,BoxLayout.Y_AXIS));
        //Panel arriba
        panel();
        
        
        //Panel abajo
        panelDos();
        
        
        
        this.add(this.panelPrin);
        this.setVisible(true);
    }
    
    //Panel de arriba
    private void panel() {
        this.panel = new JPanel(new FlowLayout());
        this.btnVolver = new JButton("Volver");
        this.cbTiendas = new JComboBox<>();  // Asegurar inicialización antes de uso
        mostrarTiendas();

        this.panel.add(this.btnVolver);
        this.panelPrin.add(this.panel);
    }
    
    private void mostrarTiendas(){
        ConsultasTablaStock c = new ConsultasTablaStock();
        if (c.comprobarUsuario().equals( "gestor")) {
            tiendasTotales();
        } else {
        }
    }

    private void tiendasTotales() {
        this.cbTiendas = new JComboBox<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/anmafe_games_bd", "root", "")){
            String sql = "SELECT nombre FROM tiendas;";
            
            try (PreparedStatement ps = conn.prepareStatement(sql)){
                
                try(ResultSet resultado = ps.executeQuery()){
                    
                    while (resultado.next()) {
                       this.cbTiendas.addItem(resultado.getString("nombre"));
                        
                    }
                    
                }catch(Exception e){
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.panel.add(this.cbTiendas);
    }
    
    
    //Creamos el panelDos lo inicializamos y aññadios la tabla
    private void panelDos() {
        this.panelDos = new JPanel();
        this.panelDos.setBackground(Color.red);
        this.panelDos.setLayout(new FlowLayout());
        
        
        mostrarTablaInventario();
        
        
    }

    private void mostrarTablaInventario() {
        
        //Creamos el modelo
        
        modelo = new DefaultTableModel();
        
        //Añadimos las cabeceras de la tabla al modelo

        ArrayList<String> cabecerasTablaInventario = new ConsultasTablaStock().obtenerCabecerasTabla();

        for (int i = 0; i <= cabecerasTablaInventario.size() - 1; i++) {
            modelo.addColumn(cabecerasTablaInventario.get(i));
            System.out.println(cabecerasTablaInventario.get(i));
        }
        
        mostrarDatos(modelo);
        añadir();
        
        this.tabla = new JTable(modelo);
        
        
        cbProductos();
        
        cbTiendasTabla();
        
        this.panelPrin.add(this.panelDos);
    }

    private void mostrarDatos(DefaultTableModel modelo) {
        this.btnMostrar = new JButton("Mostrar Datos");
        this.btnMostrar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultasTablaStock().mostrardatos(tabla,modelo);
                
                
                
                
            }
        });
        
        this.panelDos.add(btnMostrar);
    }
    
    private void añadir() {
        this.btnAñadirProductos = new JButton();
        this.btnAñadirProductos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
    }

    private void cbProductos() {
        //Añdir cb por celda
        ArrayList<String> nombresProductos = new ConsultasTablaStock().obtenerProductos();

        JComboBox cbProductos = new JComboBox();
        for (int i = 0; i < nombresProductos.size(); i++) {
            cbProductos.addItem(nombresProductos.get(i));
        }

        TableColumn colProdrutos = tabla.getColumnModel().getColumn(1); // índice de la columna 'Ciudad'
        colProdrutos.setCellEditor(new DefaultCellEditor(cbProductos));
        
        JScrollPane barra = new JScrollPane(tabla);
        
        
        this.panelDos.add(barra);
        
    }
    
    private void cbTiendasTabla() {
        //Añdir cb por celda
        ArrayList<String> nombresTiendas = new ConsultasTablaStock().obtenerTiendasTabla();

        this.cbTiendasTabla = new JComboBox<>();
        for (int i = 0; i < nombresTiendas.size(); i++) {
            cbTiendasTabla.addItem(nombresTiendas.get(i));
        }

        TableColumn colProdrutos = tabla.getColumnModel().getColumn(2); // índice de la columna 'Ciudad'
        colProdrutos.setCellEditor(new DefaultCellEditor(cbTiendasTabla));
        
        JScrollPane barra = new JScrollPane(tabla);
        
        
        this.panelDos.add(barra);
        
    }

    
    
}
