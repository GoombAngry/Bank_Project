package com.atm.bank.atm_bank.app;

import Utilidades.ControllerRequest;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONObject;

public class PanelUsuario extends javax.swing.JPanel {

    private String jwtToken;
    private JSONObject informacionCliente;
    private VentanaInicial ventanaInicial;
    private JPanel panelPantallaCajero;
    private JPanel panelOperacion;
    private String identificador;
    private int id_identificador;

    public PanelUsuario() {
        initComponents();
    }

    public PanelUsuario(String jwtToken, VentanaInicial v, String identificador, int id_indentificador) {
        this();
        this.jwtToken = jwtToken;
        this.ventanaInicial = v;
        this.identificador = identificador;
        this.id_identificador = id_indentificador;
        System.out.println("[*] - Se ha cargado el panel del usuario");
        System.out.println("Id Identificador -> " + this.id_identificador);
        System.out.println("Identificador -> " + this.identificador);
        // Cargamos informacion del cliente
        this.cargarInformacionCliente();
        // Guardamos el panel de la pantalla del cajero
        this.panelPantallaCajero = this.ventanaInicial.getPanelPantalla();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTittleBienvenida = new javax.swing.JLabel();
        labelNombreUsuario = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnIngresarOperacion = new javax.swing.JButton();
        btnVerMovimientosOperacion = new javax.swing.JButton();
        btnRetirarOperacion = new javax.swing.JButton();
        btnSalirOperacion = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(534, 268));

        labelTittleBienvenida.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        labelTittleBienvenida.setForeground(new java.awt.Color(0, 0, 0));
        labelTittleBienvenida.setText("Bienvenido,");

        labelNombreUsuario.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        labelNombreUsuario.setForeground(new java.awt.Color(0, 0, 0));
        labelNombreUsuario.setText("Nombre Apellidos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Menu Operaciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Liberation Sans", 0, 18))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(0, 0, 0));

        btnIngresarOperacion.setText("Ingresar");
        btnIngresarOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarOperacionActionPerformed(evt);
            }
        });

        btnVerMovimientosOperacion.setText("Ver Movimientos");
        btnVerMovimientosOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerMovimientosOperacionActionPerformed(evt);
            }
        });

        btnRetirarOperacion.setText("Retirar");
        btnRetirarOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetirarOperacionActionPerformed(evt);
            }
        });

        btnSalirOperacion.setText("Salir");
        btnSalirOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirOperacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnIngresarOperacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVerMovimientosOperacion, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRetirarOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIngresarOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRetirarOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVerMovimientosOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(labelTittleBienvenida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelNombreUsuario))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(76, 76, 76))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTittleBienvenida)
                    .addComponent(labelNombreUsuario))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRetirarOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetirarOperacionActionPerformed
        this.panelOperacion = new PanelIngresoRetiro(false, this);
        this.showOpearcionEnPantallaCajero(this.panelOperacion);
    }//GEN-LAST:event_btnRetirarOperacionActionPerformed

    private void btnSalirOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirOperacionActionPerformed
        ControllerRequest.desacreditarJWT(this.jwtToken);
        this.ventanaInicial.closeMenuCliente();
    }//GEN-LAST:event_btnSalirOperacionActionPerformed

    private void btnIngresarOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarOperacionActionPerformed
        this.panelOperacion = new PanelIngresoRetiro(true, this);
        this.showOpearcionEnPantallaCajero(this.panelOperacion);
    }//GEN-LAST:event_btnIngresarOperacionActionPerformed

    private void btnVerMovimientosOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerMovimientosOperacionActionPerformed
        this.panelOperacion = new PanelMovimientos(this);
        this.showOpearcionEnPantallaCajero(this.panelOperacion);
    }//GEN-LAST:event_btnVerMovimientosOperacionActionPerformed

    private void cargarInformacionCliente() {
        this.informacionCliente = ControllerRequest.getInformacionCliente(jwtToken);
        if (this.informacionCliente != null) {
            this.informacionCliente = this.informacionCliente.getJSONObject("body");
            System.out.println("[*] - Se ha cargado la informacion del cliente correctamente.");
            this.labelNombreUsuario.setText(informacionCliente.getString("nombre") + " " + informacionCliente.getString("apellidos").split(" ")[0]);
        } else {
            System.out.println("[!] - Error al cargar la informacion del cliente.");
        }
    }

    private void showOpearcionEnPantallaCajero(Component component) {
        if (component instanceof PanelIngresoRetiro) {
            System.out.println("[*] -  Acceso a Panel Ingreso/Retiro");
            this.ventanaInicial.setIsAccesoPorOperacion(true);
            this.ventanaInicial.setCampoControlCantidad(((PanelIngresoRetiro) (component)).getCampoCantidad());
        }
        this.setVisible(false);
        this.panelPantallaCajero.add(component);
        this.panelPantallaCajero.revalidate();
        this.panelPantallaCajero.repaint();
    }

    public void closeOPeracionEnPantallaCajero() {
        this.ventanaInicial.setIsAccesoPorOperacion(false);
        this.setVisible(true);
        this.panelPantallaCajero.remove(this.panelOperacion);
        this.panelPantallaCajero.revalidate();
        this.panelPantallaCajero.repaint();
        this.panelOperacion = null;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresarOperacion;
    private javax.swing.JButton btnRetirarOperacion;
    private javax.swing.JButton btnSalirOperacion;
    private javax.swing.JButton btnVerMovimientosOperacion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelNombreUsuario;
    private javax.swing.JLabel labelTittleBienvenida;
    // End of variables declaration//GEN-END:variables

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int getId_identificador() {
        return id_identificador;
    }

    public void setId_identificador(int id_identificador) {
        this.id_identificador = id_identificador;
    }

}
