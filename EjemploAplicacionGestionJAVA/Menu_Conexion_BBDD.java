/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda_videojuegos;

import com.sun.jdi.connect.spi.Connection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Federico
 */
public class Menu_Conexion_BBDD extends JPanel{
    private JTextField txtBaseDatos, txtUsuario, txtContraseña;
    private JComboBox desplegableTabla;
    private JButton btnProbarConexion, btnMostrarDatosTabla, btnAgregarDatos;
    
    private JTable tabla;
    
    
    public Menu_Conexion_BBDD() {
        initComponents();
        
        
    }

    private void initComponents() {
        this.setSize(570,250);
        setBackground(Color.BLUE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.txtBaseDatos = new JTextField();
        this.txtBaseDatos.setPreferredSize(new Dimension(100,15));
        this.txtBaseDatos.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                if (txtBaseDatos.getText().equals("")) {
                    String placeholder = "Nombre BBDD";
                    txtBaseDatos.setText(placeholder);
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtBaseDatos.getText().isEmpty()) {
                    String placeholder = "Nombre BBDD";
                    txtBaseDatos.setText(placeholder);
                    setForeground(Color.GRAY);
                }
            }
        });
        
        this.txtUsuario =  new JTextField();
        this.txtUsuario.setPreferredSize(new Dimension(100,15));
        
        
        
        this.txtContraseña = new JTextField();
        this.txtContraseña.setPreferredSize(new Dimension(100,15));
        
        this.desplegableTabla = new JComboBox();
        this.desplegableTabla.addItem("consolas");
        this.desplegableTabla.setPreferredSize(new Dimension(50, 15));
        
        this.btnProbarConexion = new JButton("PROBAR CONEXION");
        this.btnProbarConexion.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String URL= "jdbc:mysql://localhost:3306/"+txtBaseDatos.getText();
                String usuario = txtUsuario.getText();
                String contraseña = txtContraseña.getText();
                
                new Conexion(URL, usuario, contraseña).comprobarConexion();
            }
        });
        
        
        this.btnMostrarDatosTabla = new JButton("MOSTRAR");
        this.btnMostrarDatosTabla.setPreferredSize(new Dimension(600,25));
        this.btnMostrarDatosTabla.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String URL= "jdbc:mysql://localhost:3306/"+txtBaseDatos.getText();
                String usuario = txtUsuario.getText();
                String contraseña = txtContraseña.getText();
                
                
                String sql = "SELECT * FROM "+desplegableTabla.getSelectedItem().toString();
                Conexion c = new Conexion(URL, usuario, contraseña);
                
                c.consultarTabla(sql,tabla);
                
            }
        });
        
        
        this.btnAgregarDatos = new JButton("AGREGAR");
        this.btnAgregarDatos.setPreferredSize(new Dimension(600,25));
        this.btnAgregarDatos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelAgregar pa = new PanelAgregar();
                pa.setVisible(true);
            }
        });
        
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("ID");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("FECHA_LANZAMIENTO");
        modelo.addColumn("PRECIO");

        
        tabla = new JTable(modelo);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        this.add(this.txtBaseDatos);
        this.add(this.txtUsuario);
        this.add(this.txtContraseña);
        this.add(this.desplegableTabla);
        this.add(this.btnMostrarDatosTabla);
        this.add(this.btnProbarConexion);
        
        this.add(scrollPane);
        
        this.setVisible(true);
        
    }
    
    
    
}
