package com.atm.bank.atm_bank.app;

import Utilidades.ControllerRequest;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.util.*;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.text.*;
import org.json.JSONObject;

public class VentanaInicial extends javax.swing.JFrame {

    private String jwtToken;
    private boolean isAccesoPorTarjetaSelect;
    private boolean isAccesoPorOperacion;
    private String pinTarjeta;
    private String identificador;
    private int id_identificador;
    private PanelUsuario panelUsuario;
    // Campo del Panel Ingreso/Retiro
    private JTextField campoControlCantidad;

    public VentanaInicial() {
        initComponents();
        // Posicionamos la ventana en el centro de la pantalla
        this.setLocationRelativeTo(null);
        // Cargamos el panel de login
        this.loadComponentsPanelLogin();
    }

    // Cargar Botonera Login del Panel
    private void loadPanelButtonsLogin() {
        // Agregamos el layout al panel de los botones
        this.panelBotones.setLayout(new GridLayout(4, 3));
        this.panelBotones.setPreferredSize(new Dimension(500, 240));
        Integer[] n = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        List<Integer> numbers = new ArrayList<>(Arrays.asList(n));
        Collections.shuffle(numbers); // Mezclamos los numeros
        Dimension sizeButtons = new Dimension(85, 40);
        for (int i = 0; i < 12; i++) {
            if (i == 11 || i == 9) {
                JButton accionButton = new JButton();
                accionButton.setName((i == 9) ? "delete" : "pointer");
                accionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if (((JButton) (ae.getSource())).getName().equalsIgnoreCase("delete")) {
                            if (isAccesoPorTarjetaSelect) {
                                if (passwordPantalla.getText().length() != 0) {
                                    passwordPantalla.setText(passwordPantalla.getText().substring(0, passwordPantalla.getText().length() - 1));
                                    pinTarjeta = pinTarjeta.substring(0, pinTarjeta.length() - 1);
                                    System.out.println("Pin -> " + pinTarjeta);
                                }
                            }
                            if (isAccesoPorOperacion) {
                                // Controlar eliminacion del campo del panel operacion [Ingreso/Retiro]
                                String textoCampo = campoControlCantidad.getText();
                                System.out.println(textoCampo);
                                if (textoCampo.length() != 0) {
                                    textoCampo = textoCampo.substring(0, textoCampo.length() - 1);
                                    System.out.println(textoCampo);
                                    campoControlCantidad.setText(textoCampo);
                                }
                            }
                        } else {
                            if (isAccesoPorOperacion) {
                                // Pulso la coma flotante (Mas adelante completamos la funcionalidad)
                                String textoCampo = campoControlCantidad.getText();
                                if (textoCampo.length() == 0) {
                                    // Campo vacio al pulsar la coma
                                    campoControlCantidad.setText("0.");
                                    textoCampo = "0.";
                                }
                                if (Pattern.compile("^[^.]*$").matcher(textoCampo).matches()) {
                                    campoControlCantidad.setText(campoControlCantidad.getText() + ".");
                                }
                            }
                        }
                    }
                });
                accionButton.setPreferredSize(sizeButtons);
                ImageIcon iconButton;
                if (i == 9) {
                    accionButton.setIcon(new ImageIcon(new ImageIcon(VentanaInicial.class.getResource("/images/deletedPin.png")).getImage().getScaledInstance(90, 60, Image.SCALE_SMOOTH)));
                    this.panelBotones.add(accionButton);
                } else {
                    accionButton.setText(".");
                    this.panelBotones.add(accionButton);
                }
            } else {
                JButton numberButton = new JButton();
                numberButton.setName((i == 10) ? String.valueOf(numbers.get(9)) : String.valueOf(numbers.get(i)));
                numberButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        JButton botonClick = ((JButton) (ae.getSource()));
                        if (isAccesoPorTarjetaSelect) {
                            if (passwordPantalla.getText().length() < 4) {
                                passwordPantalla.setText(passwordPantalla.getText() + "*");
                                pinTarjeta += botonClick.getName();
                            }
                        }
                        if (isAccesoPorOperacion) {
                            // Controlar inserccion para el panel operacion [INGRESO/RETIRO]
                            String texto = campoControlCantidad.getText();
                            System.out.println("Anterior -> " + texto);
                            if (texto.contains(".")) {
                                if ((texto.split("\\.").length == 1) || (texto.split("\\.").length == 2 && texto.split("\\.")[1].length() < 2)) {
                                    campoControlCantidad.setText(campoControlCantidad.getText() + botonClick.getName());
                                }
                            } else {
                                if (texto.length() == 0 && botonClick.getName().equals("0")) {
                                    campoControlCantidad.setText("0.");
                                }
                                campoControlCantidad.setText(campoControlCantidad.getText() + botonClick.getName());
                            }
                        }
                    }
                });
                numberButton.setPreferredSize(sizeButtons);
                if (i == 10) {
                    numberButton.setText(String.valueOf(numbers.get(9)));
                    this.panelBotones.add(numberButton);
                } else {
                    numberButton.setText(String.valueOf(numbers.get(i)));
                    this.panelBotones.add(numberButton);
                }

            }
            System.out.println("Boton con valor " + i + " agregado");
        }
    }

    // Cargamos todo el panel relacionado con el login
    private void loadComponentsPanelLogin() {
        this.btnCancelar.setEnabled(false);
        this.btnAceptar.setEnabled(false);
        this.panelLogin.setVisible(true);
        this.panelLoginPin.setVisible(false);
        this.panelLoginEspera.setVisible(true);
        this.jPanel5.setVisible(false);
        // Cargamos los botones del panel login
        this.loadPanelButtonsLogin();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JDialogUsarCartilla = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tittleJDialogUsarCartilla = new javax.swing.JLabel();
        textIbanJDialog = new javax.swing.JTextField();
        BtnJDialogUsarCartilla = new javax.swing.JButton();
        JDialogUsarTarjeta = new javax.swing.JDialog();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        inputNumeroTarjetaBancaria = new javax.swing.JTextField();
        BtnJDialogUsarTarjeta = new javax.swing.JButton();
        panelLogin = new javax.swing.JPanel();
        panelPantalla = new javax.swing.JPanel();
        panelLoginEspera = new javax.swing.JPanel();
        tittlePantalla = new java.awt.Label();
        panelLoginPin = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        passwordPantalla = new javax.swing.JLabel();
        panelAlignButtons = new javax.swing.JPanel();
        panelBotones = new javax.swing.JPanel();
        panelDeAcciones = new javax.swing.JPanel();
        BtnUsarCartillaa = new javax.swing.JButton();
        BtnUsarTarjeta = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        JDialogUsarCartilla.setModal(true);
        JDialogUsarCartilla.setResizable(false);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 0, 36)); // NOI18N
        jLabel1.setText("Acceso con Cartilla");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Acceso"));

        tittleJDialogUsarCartilla.setText("Indique el IBAN:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tittleJDialogUsarCartilla)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textIbanJDialog)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tittleJDialogUsarCartilla)
                    .addComponent(textIbanJDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        BtnJDialogUsarCartilla.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        BtnJDialogUsarCartilla.setText("Acceso");
        BtnJDialogUsarCartilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJDialogUsarCartillaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 105, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(94, 94, 94))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(BtnJDialogUsarCartilla, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(175, 175, 175))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnJDialogUsarCartilla, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout JDialogUsarCartillaLayout = new javax.swing.GroupLayout(JDialogUsarCartilla.getContentPane());
        JDialogUsarCartilla.getContentPane().setLayout(JDialogUsarCartillaLayout);
        JDialogUsarCartillaLayout.setHorizontalGroup(
            JDialogUsarCartillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDialogUsarCartillaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        JDialogUsarCartillaLayout.setVerticalGroup(
            JDialogUsarCartillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDialogUsarCartillaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        JDialogUsarTarjeta.setResizable(false);

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 0, 36)); // NOI18N
        jLabel2.setText("Acceso con Tarjeta");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Acceso"));

        jLabel3.setText("Numuero Tarjeta:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(inputNumeroTarjetaBancaria)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(inputNumeroTarjetaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BtnJDialogUsarTarjeta.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        BtnJDialogUsarTarjeta.setText("Acceso");
        BtnJDialogUsarTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJDialogUsarTarjetaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDialogUsarTarjetaLayout = new javax.swing.GroupLayout(JDialogUsarTarjeta.getContentPane());
        JDialogUsarTarjeta.getContentPane().setLayout(JDialogUsarTarjetaLayout);
        JDialogUsarTarjetaLayout.setHorizontalGroup(
            JDialogUsarTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDialogUsarTarjetaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDialogUsarTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDialogUsarTarjetaLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDialogUsarTarjetaLayout.createSequentialGroup()
                        .addGap(0, 49, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(74, 74, 74))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDialogUsarTarjetaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnJDialogUsarTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
        );
        JDialogUsarTarjetaLayout.setVerticalGroup(
            JDialogUsarTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDialogUsarTarjetaLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnJDialogUsarTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(802, 600));
        setResizable(false);

        panelLogin.setPreferredSize(new java.awt.Dimension(600, 500));

        panelPantalla.setBackground(new java.awt.Color(255, 255, 255));
        panelPantalla.setPreferredSize(new java.awt.Dimension(620, 500));
        panelPantalla.setLayout(new java.awt.GridBagLayout());

        panelLoginEspera.setBackground(new java.awt.Color(255, 255, 255));
        panelLoginEspera.setLayout(new java.awt.GridBagLayout());

        tittlePantalla.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        tittlePantalla.setText("Seleccione un metodo de acceso");
        panelLoginEspera.add(tittlePantalla, new java.awt.GridBagConstraints());

        panelPantalla.add(panelLoginEspera, new java.awt.GridBagConstraints());

        panelLoginPin.setBackground(new java.awt.Color(255, 255, 255));
        panelLoginPin.setLayout(new java.awt.GridBagLayout());

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Liberation Sans", 0, 28)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Introduzca Pin : ");
        panelLoginPin.add(jLabel4, new java.awt.GridBagConstraints());

        passwordPantalla.setBackground(new java.awt.Color(255, 255, 255));
        passwordPantalla.setFont(new java.awt.Font("Liberation Sans", 0, 28)); // NOI18N
        passwordPantalla.setForeground(new java.awt.Color(0, 0, 0));
        panelLoginPin.add(passwordPantalla, new java.awt.GridBagConstraints());

        panelPantalla.add(panelLoginPin, new java.awt.GridBagConstraints());

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout();
        flowLayout1.setAlignOnBaseline(true);
        panelAlignButtons.setLayout(flowLayout1);

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 522, Short.MAX_VALUE)
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        panelAlignButtons.add(panelBotones);

        panelDeAcciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Metodos Acceso"));

        BtnUsarCartillaa.setText("Usar Cartilla");
        BtnUsarCartillaa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUsarCartillaaActionPerformed(evt);
            }
        });

        BtnUsarTarjeta.setText("Usar Tarjeta");
        BtnUsarTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUsarTarjetaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDeAccionesLayout = new javax.swing.GroupLayout(panelDeAcciones);
        panelDeAcciones.setLayout(panelDeAccionesLayout);
        panelDeAccionesLayout.setHorizontalGroup(
            panelDeAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeAccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDeAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnUsarCartillaa, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(BtnUsarTarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelDeAccionesLayout.setVerticalGroup(
            panelDeAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeAccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BtnUsarCartillaa, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnUsarTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelAlignButtons, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addComponent(panelPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelDeAcciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelDeAcciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelAlignButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        getContentPane().add(panelLogin, java.awt.BorderLayout.CENTER);
        panelLogin.setVisible(false);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Boton para comprobar el IBAN en el JDialog Usar Cartilla
    private void BtnJDialogUsarCartillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJDialogUsarCartillaActionPerformed
        if (Pattern.compile("^BANK-\\d{14}$").matcher(textIbanJDialog.getText()).matches()) {
            JSONObject response = ControllerRequest.loginAtm(textIbanJDialog.getText(), "");
            if (response == null || response.getInt("codeResponse") == 404) {
                JOptionPane.showMessageDialog(null,
                        "Ocurrió un error al procesar la solicitud.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Recogemos el token de sesion
                this.jwtToken = response.getJSONObject("body").getString("token");
                this.id_identificador = response.getJSONObject("body").getInt("id_identificador");
                // Guardamos el identificador
                this.identificador = textIbanJDialog.getText();
                // Cerramos el JDialog
                this.JDialogUsarCartilla.dispose();
                // Mostramos el menu del cliente por la pantalla del cajero
                this.showMenuCliente();
            }
        } else {
            JOptionPane.showMessageDialog(null, "¡Advertencia! El IBAN ingresado es incorrecto. \n Ejemplo: BANK-11111111111111", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtnJDialogUsarCartillaActionPerformed

    private void BtnJDialogUsarTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJDialogUsarTarjetaActionPerformed
        if (Pattern.compile("^\\d{12}$").matcher(inputNumeroTarjetaBancaria.getText()).matches()) {
            this.isAccesoPorTarjetaSelect = true;
            this.pinTarjeta = "";
            this.identificador = this.inputNumeroTarjetaBancaria.getText();
            this.jPanel5.setVisible(true);
            this.panelLoginEspera.setVisible(false);
            this.panelLoginPin.setVisible(true);
            this.JDialogUsarTarjeta.dispose();
            this.btnCancelar.setEnabled(true);
            this.btnAceptar.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "¡Advertencia! El Numero de Tarjeta es incorrecto. \n Ejemplo: 123456789123", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtnJDialogUsarTarjetaActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if (this.isAccesoPorTarjetaSelect) {
            JSONObject response = ControllerRequest.loginAtm(identificador, pinTarjeta);
            if (response != null) {
                if (response.has("codeResponse")) {
                    if (response.getInt("codeResponse") == 201) {
                        //this.pintarMensajeStatus("Exito!", "Se logueo correctamente!.", true);
                        JOptionPane.showMessageDialog(this, "Se logueo correctamente!.",
                                "Éxito!", JOptionPane.INFORMATION_MESSAGE);
                        this.jwtToken = response.getJSONObject("body").getString("token");
                        this.id_identificador = response.getJSONObject("body").getInt("id_identificador");
                        this.jPanel5.setVisible(false);
                        showMenuCliente();
                    } else {
                        //this.pintarMensajeStatus("Error!", "Se ha producido un error!.", false);
                        JOptionPane.showMessageDialog(this, "Se ha producido un error!.",
                                "Error!", JOptionPane.ERROR_MESSAGE);
                        this.pinTarjeta = "";
                        this.passwordPantalla.setText("");
                        this.panelLoginEspera.setVisible(false);
                    }
                }
            } else {
                //this.pintarMensajeStatus("Error!", "Error de servicio!.", false);
                JOptionPane.showMessageDialog(this, "Error del servicio!.",
                        "Error!", JOptionPane.ERROR_MESSAGE);
                this.isAccesoPorTarjetaSelect = false;
                this.pinTarjeta = "";
                this.passwordPantalla.setText("");
                this.panelLoginEspera.setVisible(true);
                this.panelLoginPin.setVisible(false);
                this.btnCancelar.setEnabled(false);
                this.btnAceptar.setEnabled(false);
                this.panelPantalla.revalidate();
                this.panelPantalla.repaint();
            }
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.isAccesoPorTarjetaSelect = false;
        this.pinTarjeta = "";
        this.passwordPantalla.setText("");
        this.panelLoginEspera.setVisible(true);
        this.panelLoginPin.setVisible(false);
        this.jPanel5.setVisible(false);
        this.panelPantalla.revalidate();
        this.panelPantalla.repaint();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Funcion Metodo usar Tarjeta
    private void BtnUsarTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUsarTarjetaActionPerformed
        this.JDialogUsarTarjeta.setSize(435, 210);
        this.JDialogUsarTarjeta.setLocationRelativeTo(this);
        this.JDialogUsarTarjeta.setVisible(true);
        this.inputNumeroTarjetaBancaria.setText("XXXXXXXXXXXX");
        this.inputNumeroTarjetaBancaria.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                // 706826986816
                if (inputNumeroTarjetaBancaria.getText().length() < 12) {
                    char keyPress = ke.getKeyChar();
                    if (!Character.isDigit(keyPress)) {
                        ke.consume();
                    }
                } else {
                    ke.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        });
    }//GEN-LAST:event_BtnUsarTarjetaActionPerformed

//GEN-FIRST:event_BtnUsarCartillaActionPerformed
 
//GEN-LAST:event_BtnUsarCartillaActionPerformed

    private void BtnUsarCartillaaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUsarCartillaaActionPerformed
        this.JDialogUsarCartilla.setSize(520, 210);
        this.JDialogUsarCartilla.setLocationRelativeTo(this);
        this.textIbanJDialog.setText("BANK-XXXXXXXXXXXXXX");
        ((AbstractDocument) this.textIbanJDialog.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                super.insertString(fb, offset, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
                super.replace(fb, offset, length, string, attrs);
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                if (currentText.length() > 5) {
                    super.remove(fb, offset, length); // Permitir eliminación solo si la longitud es mayor a 5
                }
            }
        });
        this.textIbanJDialog.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                char c = ke.getKeyChar();
                // Por ejemplo, bloquear caracteres no alfanuméricos
                if (!Character.isDigit(c)) {
                    ke.consume();  // Si no es una letra o número, bloqueamos la tecla
                }
                SwingUtilities.invokeLater(() -> {
                    String texto = textIbanJDialog.getText();
                    if(texto.length() > 19){
                        textIbanJDialog.setText(texto.substring(0, texto.length()-1));
                        
                    }
                });
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        });
        this.JDialogUsarCartilla.setVisible(true);
    }//GEN-LAST:event_BtnUsarCartillaaActionPerformed

    // Metodo para mostrar el menu del cliente
    private void showMenuCliente() {
        this.panelUsuario = new PanelUsuario(this.jwtToken, this, this.identificador, this.id_identificador);
        this.isAccesoPorTarjetaSelect = false;
        this.pinTarjeta = "";
        this.passwordPantalla.setText("");
        this.panelPantalla.remove(this.panelLoginEspera);
        this.panelPantalla.remove(this.panelLoginPin);
        this.panelPantalla.add(this.panelUsuario);
        this.panelPantalla.revalidate();
        this.panelPantalla.repaint();
        this.BtnUsarCartillaa.setEnabled(false);
        this.BtnUsarTarjeta.setEnabled(false);
    }

    // Cerrar el menu del cliente
    public void closeMenuCliente() {
        this.panelPantalla.remove(this.panelUsuario);
        this.panelPantalla.add(this.panelLoginPin);
        this.panelPantalla.add(this.panelLoginEspera);
        this.panelLoginEspera.setVisible(true);
        this.panelLoginPin.setVisible(false);
        this.jPanel5.setVisible(false);
        this.BtnUsarCartillaa.setEnabled(true);
        this.BtnUsarTarjeta.setEnabled(true);
        this.panelPantalla.revalidate();
        this.panelPantalla.repaint();
        this.panelUsuario = null;
        this.identificador = "";
        this.jwtToken = "";
        this.id_identificador = 0;

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaInicial.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaInicial.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaInicial.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaInicial.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaInicial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnJDialogUsarCartilla;
    private javax.swing.JButton BtnJDialogUsarTarjeta;
    private javax.swing.JButton BtnUsarCartillaa;
    private javax.swing.JButton BtnUsarTarjeta;
    private javax.swing.JDialog JDialogUsarCartilla;
    private javax.swing.JDialog JDialogUsarTarjeta;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JTextField inputNumeroTarjetaBancaria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel panelAlignButtons;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelDeAcciones;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelLoginEspera;
    private javax.swing.JPanel panelLoginPin;
    private javax.swing.JPanel panelPantalla;
    private javax.swing.JLabel passwordPantalla;
    private javax.swing.JTextField textIbanJDialog;
    private javax.swing.JLabel tittleJDialogUsarCartilla;
    private java.awt.Label tittlePantalla;
    // End of variables declaration//GEN-END:variables
    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public boolean isIsAccesoPorOperacion() {
        return this.isAccesoPorOperacion;
    }

    public void setIsAccesoPorOperacion(boolean isAccesoPorOperacion) {
        this.isAccesoPorOperacion = isAccesoPorOperacion;
    }

    public JPanel getPanelPantalla() {
        return this.panelPantalla;
    }

    public JTextField getCampoControlCantidad() {
        return campoControlCantidad;
    }

    public void setCampoControlCantidad(JTextField campoControlCantidad) {
        this.campoControlCantidad = campoControlCantidad;
    }

}
