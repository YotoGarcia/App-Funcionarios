package com.mycompany.project.view;

import com.mycompany.project.controller.FuncionarioController;
import com.mycompany.project.data.EstadosCivilesDao;
import com.mycompany.project.data.IdentificacionDao;
import com.mycompany.project.data.SexoDao;
import com.mycompany.project.domain.EstadosCiviles;
import com.mycompany.project.domain.Funcionario;
import com.mycompany.project.domain.Identificacion;
import com.mycompany.project.domain.Sexo;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Main extends javax.swing.JFrame {

    private final FuncionarioController funcionarioController;
    private final String[] COLUMNS = {"ID", "TIPO DOCUMENTO", "DOCUMENTO", "NOMBRES", "APELLIDOS", "DIRECCION", "TELEFONO", "ESTADO CIVIL", "SEXO"};
    private final static String SELECCIONE = "--SELECCIONE--";

    public Main() {
        initComponents();
        txtId.setEditable(false);
        funcionarioController = new FuncionarioController();
        listFuncionarios();
        addListener();
    }

    private void listFuncionarios() {
        cbxFuncionarios.removeAllItems();
        Funcionario selectFuncionario = new Funcionario();
        selectFuncionario.setNombres(SELECCIONE);
        selectFuncionario.setApellidos("");
        cbxFuncionarios.addItem(selectFuncionario);

        cbxTipoIdentificacion.removeAllItems();
        Identificacion selectIdentificacion = new Identificacion();
        selectIdentificacion.setDescripcion(SELECCIONE);
        cbxTipoIdentificacion.addItem(selectIdentificacion);

        cbxEstadoCivil.removeAllItems();
        EstadosCiviles selectEstadoCivil = new EstadosCiviles();
        selectEstadoCivil.setDescripcion(SELECCIONE);
        cbxEstadoCivil.addItem(selectEstadoCivil);

        cbxSexo.removeAllItems();
        Sexo selectSexo = new Sexo();
        selectSexo.setDescripcion(SELECCIONE);
        cbxSexo.addItem(selectSexo);

        DefaultTableModel defaultTableModel = new DefaultTableModel();
        for (String column : COLUMNS) {
            defaultTableModel.addColumn(column);
        }
        tblfuncionarios.setModel(defaultTableModel);

        try {

            List<Funcionario> funcionarios = funcionarioController.getListFuncionario();
            funcionarios.sort(Comparator.comparingInt(Funcionario::getId_funcionario));
            if (!funcionarios.isEmpty()) {
                defaultTableModel.setRowCount(funcionarios.size());
                int row = 0;
                for (Funcionario funcionario : funcionarios) {
                    defaultTableModel.setValueAt(funcionario.getId_funcionario(), row, 0);
                    defaultTableModel.setValueAt(funcionario.getNombreTipoIdentificacion(), row, 1);
                    defaultTableModel.setValueAt(funcionario.getNumero_identificacion(), row, 2);
                    defaultTableModel.setValueAt(funcionario.getNombres(), row, 3);
                    defaultTableModel.setValueAt(funcionario.getApellidos(), row, 4);
                    defaultTableModel.setValueAt(funcionario.getDireccion(), row, 5);
                    defaultTableModel.setValueAt(funcionario.getTelefono(), row, 6);
                    defaultTableModel.setValueAt(funcionario.getNombreEstadoCivil(), row, 7);
                    defaultTableModel.setValueAt(funcionario.getNombreSexo(), row, 8);
                    row++;
                    cbxFuncionarios.addItem(funcionario);

                }
            }

            IdentificacionDao identificacionDao = new IdentificacionDao();
            EstadosCivilesDao estadosCivilesDao = new EstadosCivilesDao();
            SexoDao sexoDao = new SexoDao();

            List<Identificacion> identificaciones = identificacionDao.getIdentificacion();
            cbxTipoIdentificacion.removeAllItems();
            cbxTipoIdentificacion.addItem(selectIdentificacion);
            for (Identificacion identificacion : identificaciones) {
                cbxTipoIdentificacion.addItem(identificacion);
            }

            List<Identificacion> identificaion1 = identificacionDao.getIdentificacion();
            cbxTipoIdentificacion1.removeAllItems();
            cbxTipoIdentificacion1.addItem(selectIdentificacion);
            for (Identificacion identificacion : identificaion1) {
                cbxTipoIdentificacion1.addItem(identificacion);
            }

            List<EstadosCiviles> estadosCiviles = estadosCivilesDao.getEstadosCiviles();
            cbxEstadoCivil.removeAllItems();
            cbxEstadoCivil.addItem(selectEstadoCivil);
            for (EstadosCiviles estadoCivil : estadosCiviles) {
                cbxEstadoCivil.addItem(estadoCivil);
            }

            List<EstadosCiviles> estadosCiviles1 = estadosCivilesDao.getEstadosCiviles();
            cbxEstadoCivil1.removeAllItems();
            cbxEstadoCivil1.addItem(selectEstadoCivil);
            for (EstadosCiviles estadoCivil : estadosCiviles1) {
                cbxEstadoCivil1.addItem(estadoCivil);
            }

            List<Sexo> sexos = sexoDao.getSexos();
            cbxSexo.removeAllItems();
            cbxSexo.addItem(selectSexo);
            for (Sexo sexo : sexos) {
                cbxSexo.addItem(sexo);
            }

            List<Sexo> sexos1 = sexoDao.getSexos();
            cbxSexo1.removeAllItems();
            cbxSexo1.addItem(selectSexo);
            for (Sexo sexo : sexos1) {
                cbxSexo1.addItem(sexo);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addListener() {
        cbxFuncionarios.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Funcionario selectedFun = (Funcionario) event.getItem();

                if (selectedFun.getNombres().equals(SELECCIONE)) {

                    borrarFilas();
                } else {

                    listarFilas(selectedFun);

                }
            }
        });
    }

    private void borrarFilas() {
        txtId.setText("");
        cbxTipoIdentificacion1.setSelectedItem(null);
        txtDocumento1.setText("");
        txtNombre1.setText("");
        txtApellido1.setText("");
        txtDireccion1.setText("");
        txtTelefono1.setText("");
        cbxEstadoCivil1.setSelectedItem(null);
        cbxSexo1.setSelectedItem(null);
    }

    private void listarFilas(Funcionario selectedFun) {
        txtId.setText(String.valueOf(selectedFun.getId_funcionario()));

        cbxTipoIdentificacion1.setSelectedItem(getIdentificacionById(selectedFun.getTipo_identificacion()));
        cbxTipoIdentificacion1.setEditable(false);
        txtDocumento1.setText(String.valueOf(selectedFun.getNumero_identificacion()));
        txtNombre1.setText(selectedFun.getNombres());
        txtApellido1.setText(selectedFun.getApellidos());
        txtDireccion1.setText(selectedFun.getDireccion());
        txtTelefono1.setText(selectedFun.getTelefono());
        cbxEstadoCivil1.setSelectedItem(getEstadoCivilById(selectedFun.getEstado_civil()));
        cbxSexo1.setSelectedItem(getSexoById(selectedFun.getId_sexo()));
    }

    private Identificacion getIdentificacionById(int id) {
        for (int i = 0; i < cbxTipoIdentificacion1.getItemCount(); i++) {
            Identificacion identificacion = (Identificacion) cbxTipoIdentificacion1.getItemAt(i);
            if (identificacion.getId() == id) {
                return identificacion;
            }
        }
        return null;
    }

    private EstadosCiviles getEstadoCivilById(int id) {
        for (int i = 0; i < cbxEstadoCivil1.getItemCount(); i++) {
            EstadosCiviles estadoCivil = (EstadosCiviles) cbxEstadoCivil1.getItemAt(i);
            if (estadoCivil.getId() == id) {
                return estadoCivil;
            }
        }
        return null;
    }

    private Sexo getSexoById(int id) {
        for (int i = 0; i < cbxSexo1.getItemCount(); i++) {
            Sexo sexo = (Sexo) cbxSexo1.getItemAt(i);
            if (sexo.getId() == id) {
                return sexo;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTPanels = new javax.swing.JTabbedPane();
        jPFun1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        cbxTipoIdentificacion = new javax.swing.JComboBox<>();
        cbxEstadoCivil = new javax.swing.JComboBox<>();
        cbxSexo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblfuncionarios = new javax.swing.JTable();
        jPFun2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cbxFuncionarios = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDocumento1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtNombre1 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtApellido1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDireccion1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTelefono1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        cbxTipoIdentificacion1 = new javax.swing.JComboBox<>();
        cbxEstadoCivil1 = new javax.swing.JComboBox<>();
        cbxSexo1 = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Gestión Funcionarios");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Digita los siguientes campos"));

        jLabel9.setText("NOMBRES");

        jLabel10.setText("APELLIDOS");

        jLabel11.setText("DIRECCION");

        jLabel13.setText("ESTADO CIVIL");

        jLabel14.setText("SEXO");

        jLabel15.setText("TIPO DOCUMENTO");

        jLabel2.setText("DOCUMENTO");

        btnSave.setBackground(new java.awt.Color(0, 153, 0));
        btnSave.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel6.setText("TELEFONO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cbxTipoIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)
                                        .addComponent(jLabel13))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(40, 40, 40)
                                        .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jLabel2)
                                .addGap(105, 105, 105)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addGap(50, 50, 50)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(cbxEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel2)
                    .addComponent(jLabel11)
                    .addComponent(jLabel6))
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTipoIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        tblfuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblfuncionarios);

        javax.swing.GroupLayout jPFun1Layout = new javax.swing.GroupLayout(jPFun1);
        jPFun1.setLayout(jPFun1Layout);
        jPFun1Layout.setHorizontalGroup(
            jPFun1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPFun1Layout.setVerticalGroup(
            jPFun1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFun1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTPanels.addTab("CREAR FUNCIONARIO", jPFun1);

        cbxFuncionarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFuncionariosActionPerformed(evt);
            }
        });

        jLabel3.setText("FUNCIONARIOS");

        jLabel22.setText("TIPO IDENTIFICACION");

        jLabel4.setText("DOCUMENTO");

        jLabel16.setText("NOMBRES");

        jLabel17.setText("APELLIDOS");

        jLabel18.setText("DIRECCION");

        jLabel19.setText("TELEFONO");

        jLabel20.setText("ESTADO CIVIL");

        jLabel21.setText("SEXO");

        jLabel5.setText("ID");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbxTipoIdentificacion1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(cbxEstadoCivil1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(cbxSexo1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jLabel4)
                        .addGap(61, 61, 61)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addComponent(jLabel19)
                        .addGap(64, 64, 64))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDocumento1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtApellido1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(229, 229, 229))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(cbxFuncionarios, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxFuncionarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDocumento1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxTipoIdentificacion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxEstadoCivil1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxSexo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73))
        );

        btnUpdate.setBackground(new java.awt.Color(51, 153, 0));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("ACTUALIZAR");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(204, 0, 51));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("ELIMINAR");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPFun2Layout = new javax.swing.GroupLayout(jPFun2);
        jPFun2.setLayout(jPFun2Layout);
        jPFun2Layout.setHorizontalGroup(
            jPFun2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPFun2Layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(btnUpdate)
                .addGap(219, 219, 219)
                .addComponent(btnDelete)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPFun2Layout.setVerticalGroup(
            jPFun2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFun2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPFun2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addGap(0, 45, Short.MAX_VALUE))
        );

        jTPanels.addTab("ACTUALIZAR FUNCIONARIO", jPFun2);

        getContentPane().add(jTPanels, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 930, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxFuncionariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFuncionariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxFuncionariosActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        Identificacion selectedTipoIdentificacion = (Identificacion) cbxTipoIdentificacion.getSelectedItem();
        if (selectedTipoIdentificacion == null || SELECCIONE.equals(selectedTipoIdentificacion.getDescripcion())) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione el tipo de documento");
            cbxTipoIdentificacion.requestFocus();
            return;
        }

        if (txtDocumento.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Digite el documento");
            txtDocumento.requestFocus();
            return;
        }

        if (txtNombres.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Digite los nombre");
            txtNombres.requestFocus();
            return;
        }

        if (txtApellidos.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Digite los apellidos");
            txtApellidos.requestFocus();
            return;
        }

        if (txtDireccion.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Digite la direccion");
            txtDireccion.requestFocus();
            return;
        }

        if (txtTelefono.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Digite el telefono");
            txtTelefono.requestFocus();
            return;
        }

        EstadosCiviles selectedEstadoCivil = (EstadosCiviles) cbxEstadoCivil.getSelectedItem();
        if (selectedEstadoCivil == null || SELECCIONE.equals(selectedEstadoCivil.getDescripcion())) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione el estado civil");
            cbxEstadoCivil.requestFocus();
            return;
        }

        Sexo selectedSexo = (Sexo) cbxSexo.getSelectedItem();
        if (selectedSexo == null || SELECCIONE.equals(selectedSexo.getDescripcion())) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione el sexo");
            cbxSexo.requestFocus();
            return;
        }

        try {

            Funcionario funcionario = new Funcionario();
            funcionario.setTipo_identificacion(((Identificacion) cbxTipoIdentificacion.getSelectedItem()).getId());
            funcionario.setNumero_identificacion(Integer.parseInt(txtDocumento.getText().trim()));
            funcionario.setNombres(txtNombres.getText().trim());
            funcionario.setApellidos(txtApellidos.getText().trim());
            funcionario.setDireccion(txtDireccion.getText().trim());
            funcionario.setTelefono(txtTelefono.getText().trim());
            funcionario.setEstado_civil(((EstadosCiviles) cbxEstadoCivil.getSelectedItem()).getId());
            funcionario.setId_sexo(((Sexo) cbxSexo.getSelectedItem()).getId());

            funcionarioController.create(funcionario);
            txtDocumento.setText("");
            txtNombres.setText("");
            txtApellidos.setText("");
            txtDireccion.setText("");
            txtTelefono.setText("");

            listFuncionarios();
            JOptionPane.showMessageDialog(null, "Funcionario creado con exito");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "No fue posible crear el funcionario");
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

        Identificacion selectedTipoIdentificacion = (Identificacion) cbxTipoIdentificacion1.getSelectedItem();
        if (selectedTipoIdentificacion == null || SELECCIONE.equals(selectedTipoIdentificacion.getDescripcion())) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione el tipo de documento");
            cbxTipoIdentificacion1.requestFocus();
            return;
        }

        if (txtDocumento1.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Digite el documento");
            txtDocumento1.requestFocus();
            return;
        }

        if (txtNombre1.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Digite los nombre");
            txtNombre1.requestFocus();
            return;
        }

        if (txtApellido1.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Digite los apellidos");
            txtApellido1.requestFocus();
            return;
        }

        if (txtDireccion1.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Digite la direccion");
            txtDireccion1.requestFocus();
            return;
        }

        if (txtTelefono1.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Digite el telefono");
            txtTelefono1.requestFocus();
            return;
        }

        EstadosCiviles selectedEstadoCivil = (EstadosCiviles) cbxEstadoCivil1.getSelectedItem();
        if (selectedEstadoCivil == null || SELECCIONE.equals(selectedEstadoCivil.getDescripcion())) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione el estado civil");
            cbxEstadoCivil1.requestFocus();
            return;
        }

        Sexo selectedSexo = (Sexo) cbxSexo1.getSelectedItem();
        if (selectedSexo == null || SELECCIONE.equals(selectedSexo.getDescripcion())) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione el sexo");
            cbxSexo1.requestFocus();
            return;
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setTipo_identificacion(((Identificacion) cbxTipoIdentificacion1.getSelectedItem()).getId());
        funcionario.setNumero_identificacion(Integer.parseInt(txtDocumento1.getText().trim()));
        funcionario.setNombres(txtNombre1.getText().trim());
        funcionario.setApellidos(txtApellido1.getText().trim());
        funcionario.setDireccion(txtDireccion1.getText().trim());
        funcionario.setTelefono(txtTelefono1.getText().trim());
        funcionario.setEstado_civil(((EstadosCiviles) cbxEstadoCivil1.getSelectedItem()).getId());
        funcionario.setId_sexo(((Sexo) cbxSexo1.getSelectedItem()).getId());

        int opt = JOptionPane.showConfirmDialog(null, "Desea actualizar el funcionario?", "confirmar salida", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opt == 0) {

            try {

                funcionarioController.updateFuncionario(Integer.parseInt(txtId.getText()), funcionario);

                txtId.setText("");
                cbxTipoIdentificacion1.setSelectedItem(null);
                txtDocumento1.setText("");
                txtNombre1.setText("");
                txtApellido1.setText("");
                txtDireccion1.setText("");
                txtTelefono1.setText("");
                cbxEstadoCivil1.setSelectedItem(null);
                cbxSexo1.setSelectedItem(null);

                listFuncionarios();
                JOptionPane.showMessageDialog(null, "Funcionario actualizado con éxito");

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "No fue posible actualizar el funcionario");
            }
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (txtId.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Sleccione un Funcionario de la lista");
            txtId.requestFocus();
            return;
        }

        int opt = JOptionPane.showConfirmDialog(null, "Desea eliminar el funcionario?", "Confirmar salida",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opt == 0) {
            try {

                funcionarioController.deleteFuncionario(Integer.parseInt(txtId.getText()));

                txtDocumento1.setText("");
                txtNombre1.setText("");
                txtApellido1.setText("");
                txtDireccion1.setText("");
                txtTelefono1.setText("");

                listFuncionarios();
                JOptionPane.showMessageDialog(null, "Funcionario eliminado con exito");

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "No fue posible eliminar el funcionario");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

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
                if ("Window".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<EstadosCiviles> cbxEstadoCivil;
    private javax.swing.JComboBox<EstadosCiviles> cbxEstadoCivil1;
    private javax.swing.JComboBox<Funcionario> cbxFuncionarios;
    private javax.swing.JComboBox<Sexo> cbxSexo;
    private javax.swing.JComboBox<Sexo> cbxSexo1;
    private javax.swing.JComboBox<Identificacion> cbxTipoIdentificacion;
    private javax.swing.JComboBox<Identificacion> cbxTipoIdentificacion1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPFun1;
    private javax.swing.JPanel jPFun2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTPanels;
    private javax.swing.JTable tblfuncionarios;
    private javax.swing.JTextField txtApellido1;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDireccion1;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtDocumento1;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTelefono1;
    // End of variables declaration//GEN-END:variables
}
