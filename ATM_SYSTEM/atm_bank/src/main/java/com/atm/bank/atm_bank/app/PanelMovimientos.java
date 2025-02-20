package com.atm.bank.atm_bank.app;

import Utilidades.ControllerRequest;
import Utilidades.clases.MovimientoInfo;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.json.JSONArray;
import org.json.JSONObject;

public class PanelMovimientos extends javax.swing.JPanel {

    private PanelUsuario panelUsuario; // Referencia al panel padre
    private ArrayList<MovimientoInfo> listaMovimientos; // Guardamos los movimientos
    private int indexPagina = 1; // Para representa la pagina de los movimientos
    private int maxIndexPagina; // Para representa el tope de paginas
    private final int MAX_ITEMS = 7; // Numero de elementos que se mostrara por pagina
    private boolean[] stateButons; // Guardar los estado antes de mostrar la infor del movimiento
    public PanelMovimientos() {
        initComponents();
    }

    public PanelMovimientos(PanelUsuario p) {
        this();
        this.panelUsuario = p;
        this.jPanel2.setVisible(false);
        this.jTable1.setDefaultRenderer(Object.class, new RenderTable());
        this.index.setText(String.valueOf(indexPagina));
        this.cargarDatosTabla();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        panelMuestra = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnAtrasShowInfoMovimiento = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cantidadMovimiento = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        remitenteMovimiento = new javax.swing.JLabel();
        destinatarioMovimiento = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        btnLeft = new javax.swing.JButton();
        btnRight = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        index = new javax.swing.JLabel();

        jButton2.setText("jButton2");

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(534, 268));

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Movimientos");

        jButton1.setText("Atras");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        panelMuestra.setBackground(new java.awt.Color(204, 204, 204));
        panelMuestra.setPreferredSize(new java.awt.Dimension(522, 100));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Tipo", "Cantidad", "Accion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setPreferredSize(new java.awt.Dimension(522, 112));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnAtrasShowInfoMovimiento.setText("Atras");
        btnAtrasShowInfoMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasShowInfoMovimientoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Cantidad: ");

        cantidadMovimiento.setFont(new java.awt.Font("Liberation Sans", 0, 12)); // NOI18N
        cantidadMovimiento.setForeground(new java.awt.Color(0, 0, 0));
        cantidadMovimiento.setText("1.000.000,23");

        jLabel5.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Remitente: ");

        jLabel6.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Destinatario: ");

        remitenteMovimiento.setFont(new java.awt.Font("Liberation Sans", 0, 12)); // NOI18N
        remitenteMovimiento.setForeground(new java.awt.Color(0, 0, 0));
        remitenteMovimiento.setText("BANK-XXXXXXXXXXXX");

        destinatarioMovimiento.setFont(new java.awt.Font("Liberation Sans", 0, 12)); // NOI18N
        destinatarioMovimiento.setForeground(new java.awt.Color(0, 0, 0));
        destinatarioMovimiento.setText("BANK-XXXXXXXXXXXX");

        jLabel7.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Descripcion: ");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Liberation Sans", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(remitenteMovimiento))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(destinatarioMovimiento))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnAtrasShowInfoMovimiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cantidadMovimiento))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(152, 218, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(remitenteMovimiento)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(destinatarioMovimiento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cantidadMovimiento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAtrasShowInfoMovimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelMuestraLayout = new javax.swing.GroupLayout(panelMuestra);
        panelMuestra.setLayout(panelMuestraLayout);
        panelMuestraLayout.setHorizontalGroup(
            panelMuestraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
            .addGroup(panelMuestraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelMuestraLayout.setVerticalGroup(
            panelMuestraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
            .addGroup(panelMuestraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/left-arrow.png"))); // NOI18N
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        btnRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/right-arrow.png"))); // NOI18N
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        index.setText("100");
        index.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelMuestra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(index)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRight, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel1)))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMuestra, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnRight, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))
                            .addComponent(btnLeft, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(index)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.panelUsuario.closeOPeracionEnPantallaCajero();
        DefaultTableModel t = new DefaultTableModel();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // Obtenemos las coordenas del elementos que hemos hecho click
        int columna = this.jTable1.getColumnModel().getColumnIndexAtX(evt.getX());
        int fila = evt.getY() / this.jTable1.getRowHeight();
        if (columna >= 0 && fila >= 0) {
            if (columna == 3) {
                int indexSelected;
                if (this.indexPagina > 1) {
                    indexSelected = (MAX_ITEMS * (this.indexPagina - 1)) + fila;
                } else {
                    indexSelected = fila;
                }
                MovimientoInfo selectedMovimiento = this.listaMovimientos.get(indexSelected);
                mostrarInfoMovimiento(selectedMovimiento);
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        if (this.indexPagina - 1 >= 1) {
            this.indexPagina -= 1;
            this.actulizarIndice();
            if (this.indexPagina == 1) {
                this.btnLeft.setEnabled(false);
            }
            if (this.indexPagina < this.maxIndexPagina && !this.btnRight.isEnabled()) {
                this.btnRight.setEnabled(true);
            }
            showDataForIndex();
        }
    }//GEN-LAST:event_btnLeftActionPerformed

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        if (this.indexPagina + 1 <= this.maxIndexPagina) {
            if (this.indexPagina + 1 > 1 && !this.btnLeft.isEnabled()) {
                this.btnLeft.setEnabled(true);
            }
            this.indexPagina += 1;
            this.actulizarIndice();
            if (indexPagina == maxIndexPagina) {
                this.btnRight.setEnabled(false);
            }
            showDataForIndex();
        }
    }//GEN-LAST:event_btnRightActionPerformed

    private void btnAtrasShowInfoMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasShowInfoMovimientoActionPerformed
        this.jPanel2.setVisible(false);
        this.jScrollPane1.setVisible(true);
        this.btnLeft.setEnabled(this.stateButons[0]);
        this.btnRight.setEnabled(this.stateButons[1]);
        this.jButton1.setEnabled(true);
        this.cantidadMovimiento.setText("");
        this.remitenteMovimiento.setText("");
        this.destinatarioMovimiento.setText("");
        this.jTextArea1.setText("");
    }//GEN-LAST:event_btnAtrasShowInfoMovimientoActionPerformed

    private void actulizarIndice() {
        this.index.setText(String.valueOf(this.indexPagina));
    }

    private void cargarDatosTabla() {
        // Limpiamos la tabla antes de insertar nuevos datos
        removeAllJTable();
        // Obtenemos los datos y los guardamos en una lista
        JSONObject response = ControllerRequest.getMovimientosByIdentificador(this.panelUsuario.getJwtToken(), this.panelUsuario.getIdentificador(), this.panelUsuario.getId_identificador());
        if (response != null) {
            this.listaMovimientos = new ArrayList();
            JSONArray movimientos = response.getJSONArray("body");
            for (int i = 0; i < movimientos.length(); i++) {
                listaMovimientos.add(extractInfo(movimientos.getJSONObject(i)));
            }

            System.out.println("Total de movimientos obtenidos -> " + listaMovimientos.size());
            this.maxIndexPagina = Integer.parseInt(String.valueOf(Math.ceil((double) (listaMovimientos.size()) / 7.0)).split("\\.")[0]);
            System.out.println("Paginas Totales -> " + this.maxIndexPagina);
        } else {
            System.out.println("[! - Error al obtener los movimientos");
        }
        this.btnLeft.setEnabled(false);
        if (this.indexPagina == 1) {
            this.btnRight.setEnabled(false);
        }
        if (this.maxIndexPagina > 1) {
            this.btnRight.setEnabled(true);
        }
        showDataForIndex();
    }

    private void showDataForIndex() {
        // Limpiamos la tabla antes de insertar nuevos datos
        removeAllJTable();
        // Calculamos los indices a recorrer
        int min = MAX_ITEMS * (this.indexPagina - 1), max = (MAX_ITEMS * this.indexPagina) - 1;
        DefaultTableModel t = (DefaultTableModel) this.jTable1.getModel();
        try {
            for (int i = min; i <= max; i++) {
                MovimientoInfo iter = this.listaMovimientos.get(i);
                System.out.println("Mostrando id -> " + iter.getId_movimiento());
                Object[] data = {iter.getFechaMovimiento(), iter.getTipoMovimiento(), iter.getCantidad(), new JButton("Ver mas")};
                t.addRow(data);
            }
        } catch (Exception e) {
            System.out.println("Execpcion");
        }
    }

    private MovimientoInfo extractInfo(JSONObject jsonObject) {
        MovimientoInfo n = new MovimientoInfo();
        if (ControllerRequest.isBankId(this.panelUsuario.getIdentificador())) {
            n.setId_movimiento(jsonObject.getInt("id_movimiento_cuenta_bancaria"));
        } else {
            n.setId_movimiento(jsonObject.getInt("id_movimiento_tarjeta_bancaria"));
        }
        n.setTipoMovimiento(jsonObject.getString("tipo_movimiento"));
        n.setCantidad(jsonObject.getBigDecimal("cantidad"));
        String[] modifiedFecha = jsonObject.getString("fecha_movimiento").split("T")[0].split("-");
        n.setFechaMovimiento(modifiedFecha[2] + "-" + modifiedFecha[1] + "-" + modifiedFecha[0]);
        n.setDescripcion(jsonObject.getString("descripcion"));
        n.setRemitente(jsonObject.getString("remitente_identificador"));
        n.setDestinatario(jsonObject.getString("destinatario_identificador"));
        return n;
    }

    // Metodo para limpiar todos los registros de la JTable
    private void removeAllJTable() {
        DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
        if (model.getRowCount() > 0) {
            model.setRowCount(0);
        }
    }

    // Metodo para mostar el panel de informacion de un movimiento
    private void mostrarInfoMovimiento(MovimientoInfo m) {
        this.stateButons = new boolean[]{this.btnLeft.isEnabled(),this.btnRight.isEnabled()};
        this.jScrollPane1.setVisible(false);
        this.jPanel2.setVisible(true);
        this.btnLeft.setEnabled(false);
        this.btnRight.setEnabled(false);
        this.jButton1.setEnabled(false);
        this.cantidadMovimiento.setText(m.getCantidad().toString());
        this.remitenteMovimiento.setText(m.getRemitente());
        this.destinatarioMovimiento.setText(m.getDestinatario());
        this.jTextArea1.setText(m.getDescripcion());
    }

    class RenderTable extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JButton) {
                return (JButton) value;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtrasShowInfoMovimiento;
    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JLabel cantidadMovimiento;
    private javax.swing.JLabel destinatarioMovimiento;
    private javax.swing.JLabel index;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel panelMuestra;
    private javax.swing.JLabel remitenteMovimiento;
    // End of variables declaration//GEN-END:variables
}
