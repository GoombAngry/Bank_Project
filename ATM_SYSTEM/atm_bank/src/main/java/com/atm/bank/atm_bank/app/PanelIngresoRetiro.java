package com.atm.bank.atm_bank.app;

import Utilidades.ControllerRequest;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.json.JSONObject;

public class PanelIngresoRetiro extends javax.swing.JPanel {

    private boolean typeOperation; // Si es true Ingreso si no Retiro
    private PanelUsuario panelUsuario; // Referencia al panel padre

    public PanelIngresoRetiro() {
        initComponents();
    }

    public PanelIngresoRetiro(boolean typeOperation, PanelUsuario p) {
        this();
        this.typeOperation = typeOperation;
        this.labelTipoOperacion.setText((typeOperation) ? "Ingreso" : "Retiro");
        this.jLabel1.setText((typeOperation) ? "Marca la cantidad a ingresar:" : "Marca la cantidad a retirar:");
        this.btnOperacion.setText((typeOperation) ? "Ingresar" : "Retirar");
        this.panelUsuario = p;
        this.inputCantidadOperacion.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTipoOperacion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        inputCantidadOperacion = new javax.swing.JTextField();
        btnOperacion = new javax.swing.JButton();
        btnAtrasOpearcion = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        labelTipoOperacion.setFont(new java.awt.Font("Liberation Sans", 0, 36)); // NOI18N
        labelTipoOperacion.setForeground(new java.awt.Color(0, 0, 0));
        labelTipoOperacion.setText("Retiro");

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Marca la cantidad a retirar:");

        inputCantidadOperacion.setFocusable(false);

        btnOperacion.setText("Retirar");
        btnOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOperacionActionPerformed(evt);
            }
        });

        btnAtrasOpearcion.setText("Atras");
        btnAtrasOpearcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasOpearcionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(labelTipoOperacion))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnAtrasOpearcion, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(186, 186, 186)
                        .addComponent(btnOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(inputCantidadOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(labelTipoOperacion)
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inputCantidadOperacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtrasOpearcion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtrasOpearcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasOpearcionActionPerformed
        this.panelUsuario.closeOPeracionEnPantallaCajero();
    }//GEN-LAST:event_btnAtrasOpearcionActionPerformed

    private void btnOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOperacionActionPerformed
        float cantidad;
        try {
            cantidad = Float.parseFloat(this.inputCantidadOperacion.getText());
            if (cantidad > 0) {
                JSONObject response = ControllerRequest.realizarOperacion(this.panelUsuario.getJwtToken(), this.typeOperation, cantidad, this.panelUsuario.getIdentificador(), this.panelUsuario.getId_identificador());
                if (response != null) {
                    if (response.has("codeResponse") && response.getInt("codeResponse") == 200) {
                        // Operacion realizada con exito
                        JOptionPane.showMessageDialog(this, "Se realizo correctamente la opearcion!.",
                                "Éxito!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Error al procesar la operacion
                        JOptionPane.showMessageDialog(this, "Se ha producido un error al procesar la operacion!.",
                                "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Se ha producido un error al realizar la operacion!.",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "¡Advertencia! Indique una cantidad superior a 0", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
            this.panelUsuario.closeOPeracionEnPantallaCajero();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "¡Advertencia! Indique una cantidad superior a 0", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnOperacionActionPerformed

    public JTextField getCampoCantidad() {
        return this.inputCantidadOperacion;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtrasOpearcion;
    private javax.swing.JButton btnOperacion;
    private javax.swing.JTextField inputCantidadOperacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelTipoOperacion;
    // End of variables declaration//GEN-END:variables
}
