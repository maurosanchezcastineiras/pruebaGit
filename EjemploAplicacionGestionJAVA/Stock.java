/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda_videojuegos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Federico
 */
public class Stock extends JFrame{
    
    JButton btnVolver;
    JPanel panelPrin, panel, panelDos;
    JComboBox<Object> tiendas, selectorCampos;
    JTable tabla;
    TableColumn columna;
    JScrollPane barra;
    ArrayList<String> productosTienda;
    
    int indice = 1;
    
    private VentanaInicioSesion sesion;
    
    public Stock(){
        initComponents();
        
    }

    private void initComponents() {
        
        this.setSize(877, 655);
        this.panelPrin = new JPanel();
//        this.panelPrin.setLayout(new BoxLayout(panelPrin,BoxLayout.Y_AXIS));
        this.panelPrin.setLayout(null);
        
        panelArriba();
        
        
        panelAbajo();
        
        
        
        
        this.add(this.panel);
        this.add(this.panelDos);
        
        this.add(this.panelPrin);
        this.setVisible(true);
    }

    private void panelArriba() {
        panel = new JPanel(new FlowLayout());
        this.btnVolver = new JButton("Volver");
        this.btnVolver.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        //Select de tiendas
        componentesCombo();
        
        
        
        
        
        panel.add(this.btnVolver);
//        panel.add(this.tiendas);
        this.panelPrin.add(panel);
    }

    private void componentesCombo() {
        this.tiendas = new JComboBox<>();
        mostrarTiendasTotales();
        this.panel.add(this.tiendas);
    }
    
    
    //Obtenemos el puesto del empleado
    private String comprobarUsuario(){
        
        String nombrePuesto = "";
        
        VentanaInicioSesion vtnSesion = VentanaInicioSesion.obtenerInstancia();
        System.out.println("Sesion: "+vtnSesion.getNombre());
        
        String usuario = vtnSesion.getNombre();
        
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/anmafe_games_bd2", "root", "")){
            String sql = "SELECT puesto FROM empleados where nombre = ? ;";
            
            try (PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, usuario);
                try(ResultSet resultado = ps.executeQuery()){
                    
                    while (resultado.next()) {
                        nombrePuesto = resultado.getString("puesto");

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
        
        return nombrePuesto;
    }
    
    //Mostramos las tiendas en las que tiene acceso el usuario
    private void mostrarTiendas(){
        
        System.out.println("Usuario: "+comprobarUsuario());
        if(comprobarUsuario() == "gestor"){
            mostrarTiendasTotales();
        }else if(comprobarUsuario()=="cajero"){
            
        }
    }

    //Select mostrar tiendas totales
    private void mostrarTiendasTotales() {
        
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/anmafe_games_bd2", "root", "")){
            String sql = "SELECT nombre FROM tiendas;";
            
            try (PreparedStatement ps = conn.prepareStatement(sql)){
                
                try(ResultSet resultado = ps.executeQuery()){
                    
                    while (resultado.next()) {
                       this.tiendas.addItem(resultado.getString("nombre"));
                        
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
    }

    
    private void mostrarTiendaPorEmpleadoTienda(){
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/anmafe_games_bd2", "root", "")){
            String sql = "SELECT tienda_id FROM empleados where tienda_id = 1;";
            
            try (PreparedStatement ps = conn.prepareStatement(sql)){
                
                try(ResultSet resultado = ps.executeQuery()){
                    
                    while (resultado.next()) {
                       this.tiendas.addItem(resultado.getString("nombre"));
                        
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
    }
    
    
    //Tabla pintada
    private void inventario() {
        
        try {
            this.tabla = new JTable();
        
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();


//            model.addColumn("ID");
//            model.addColumn("PRODUCTO_ID");
//            model.addColumn("TIENDA_ID");
//            model.addColumn("CANTIDAD");
//            model.addColumn("ESTADO");
//            model.addColumn("FECHA");
            for (int i = 0; i < tabla.getColumnCount() - 1; i++) {
                model.getColumnName(i);
            }



            this.tabla = new JTable(model);

            


            //Select de la tabla
        
        
            insertarDatosPorCampoProducto(tabla.getColumn("producto_id"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        
        
        
            
    }    

    //inicio el paneldos
    private void panelAbajo() {
        this.panelDos = new JPanel();
        this.panelDos.setLayout(new FlowLayout());
        
        inventario();
        
        this.panelDos.add(tabla);
        this.panelPrin.add(this.panelDos);
    }

    
    //Combobox registro
    private void insertarDatosPorCampoID() {
        JComboBox comboID = new JComboBox();
        
        
    }

    
    //Lleno el combobox con productos
    private void insertarDatosPorCampoProducto(TableColumn columna) {
        
        ArrayList<String> productosTienda = new ConsultasTablaStock().obtenerProductos();
        JComboBox productos = new JComboBox();
        for (int i = 0; i < productosTienda.size(); i++) {
            productos.addItem(productosTienda.get(i));
        }
        
        
        //Añadimos el combobox al modelo y ponemos un texto predefinido
        columna.setCellEditor(new DefaultCellEditor(productos));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Seleccionar producto");
        columna.setCellRenderer(renderer);
        
    }
    
    
    
}
