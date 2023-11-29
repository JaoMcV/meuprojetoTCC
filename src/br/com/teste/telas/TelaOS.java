/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.teste.telas;

/**
 *
 * @author João
 */
import java.sql.*;
import br.com.teste.dal.ModuloConexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private String tipo;
    private String forma_pagamento;

    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void pesquisar_cliente() {
        String sql = " select idcliente as ID, nome as Nome, telefone as Telefone from clientes where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtclipesquisar.getText() + "%");
            rs = pst.executeQuery();
            tblclientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }
    }

    public void setar_campos() {
        int setar = tblclientes.getSelectedRow(); // o campo 0 é o ID do cliente
        txtcliid.setText(tblclientes.getModel().getValueAt(setar, 0).toString());

    }

    private void emitir_OS() {
        String sql = "insert into OS(tipo,tipo_de_servico,total,valor_entrada,forma_pagamento,numero_de_parcelas,valor_parcelas,obs,idcliente) values (?,?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboosservico.getSelectedItem().toString());
            // .replace substitui a virgula pelo ponto 
            pst.setString(3, txtostotal.getText().replace(",", "."));
            pst.setString(4, txtosentrada.getText().replace(",", "."));
            pst.setString(5, forma_pagamento);
            pst.setString(6, cboosparcelas.getSelectedItem().toString());
            pst.setString(7, txtosvalorpar.getText().replace(",", "."));
            pst.setString(8, txtosobss.getText());
            pst.setString(9, txtcliid.getText());
            // validação dos campos obrigatorios
            if ((txtcliid.getText().isEmpty()) || (cboosservico.getSelectedItem().equals(" ")) || (txtostotal.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preenchar todas os campos obrigatórios");
            } else {
                int adicionar = pst.executeUpdate();
                if (adicionar > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso");
                    bntosadicionar.setEnabled(false);
                    bntospesquisar.setEnabled(false);
                    bntosimprimir.setEnabled(true);
                   

                }
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }
    }

    // metodo para pesquisar uma Os
    private void pesquisar_OS() {
        

        String num_os = JOptionPane.showInputDialog("Número da OS");
        String sql = "select os,date_format(data_os,'%d/%m/%Y - %H:%i'),tipo,tipo_de_servico,total,valor_entrada,forma_pagamento,numero_de_parcelas,valor_parcelas,obs,idcliente from OS where os =" + num_os;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtosid.setText(rs.getString(1));
                txtosdata.setText(rs.getString(2));
                // setando os redio button 
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("Ordem de Serviço")) {
                    rbtOs.setSelected(true);
                    tipo = "Ordem de Serviço";
                } else {
                    rbtOr.setSelected(true);
                    tipo = "Orçamento";
                }
                cboosservico.setSelectedItem(rs.getString(4));
                txtostotal.setText(rs.getString(5));
                txtosentrada.setText(rs.getString(6));
                String rbtForma_pagamento = rs.getString(7);
                if (rbtForma_pagamento.equals("A vista")) {
                    rbtosvista.setSelected(true);
                    forma_pagamento = "A vista";
                } else {
                    rbtosparcelado.setSelected(true);
                    forma_pagamento = "Parcelado";
                }
                cboosparcelas.setSelectedItem(rs.getString(8));
                txtosvalorpar.setText(rs.getString(9));
                txtosobss.setText(rs.getString(10));
                txtcliid.setText(rs.getString(11));
                // evitando oproblemas 
                bntosadicionar.setEnabled(false);
                bntospesquisar.setEnabled(false);
                txtclipesquisar.setEnabled(false);
                tblclientes.setVisible(false);
                //ativiando os botões
                bntosalterar.setEnabled(true);
                bntosremover.setEnabled(true);
                bntosimprimir.setEnabled(true);
                
                

            } else {
                JOptionPane.showMessageDialog(null, "OS não cadastrada");
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS inválida");
            
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }
    //metodo para alterar uma os

    private void alterar_os() {
        String sql = "update OS set tipo=?,tipo_de_servico=?,total=?,numero_de_parcelas=?,valor_parcelas=?, obs=? where os=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboosservico.getSelectedItem().toString());
            // .replace substitui a virgulo pelo ponto 
            pst.setString(3, txtostotal.getText().replace(",", "."));
            pst.setString(4, cboosparcelas.getSelectedItem().toString());
            pst.setString(5, txtosvalorpar.getText().replace(",", "."));
            pst.setString(6, txtosobss.getText());
            pst.setString(7, txtosid.getText());
            // validação dos campos obrigatorios
            if ((txtcliid.getText().isEmpty()) || (cboosservico.getSelectedItem().equals(" ")) || (txtostotal.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preenchar todas os campos obrigatórios");
            } else {
                int adicionar = pst.executeUpdate();
                if (adicionar > 0) {
                    JOptionPane.showMessageDialog(null, "OS alterada com sucesso");
                    limpar();

                }
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }

    }

    // metodo para excluir uma os
    private void excluir_os() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esta OS?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from OS where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtosid.getText());

                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS removida com sucesso!");
                    limpar();

                }

            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
        }

    }
    // limapr todos os campos e gerenciar os botoes os botoes

    private void limpar() {
        txtosid.setText(null);
        txtosdata.setText(null);
        txtcliid.setText(null);
        txtostotal.setText(null);
        txtosentrada.setText(null);
        txtosvalorpar.setText(null);
        txtosobss.setText(null);
        txtclipesquisar.setText(null);
        ((DefaultTableModel) tblclientes.getModel()).setRowCount(0);
        cboosservico.setSelectedItem(" ");
        cboosparcelas.setSelectedItem(" ");
        // habilitar os objetos
        bntospesquisar.setEnabled(true);
        bntosadicionar.setEnabled(true);
        txtclipesquisar.setEnabled(true);
        tblclientes.setVisible(true);
        //desabilitar os botoes
        bntosalterar.setEnabled(false);
        bntosremover.setEnabled(false);
        bntosimprimir.setEnabled(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtosid = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtosdata = new javax.swing.JTextPane();
        rbtOr = new javax.swing.JRadioButton();
        rbtOs = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtclipesquisar = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtcliid = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblclientes = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        cboosservico = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        rbtosvista = new javax.swing.JCheckBox();
        rbtosparcelado = new javax.swing.JCheckBox();
        txtostotal = new javax.swing.JTextField();
        cboosparcelas = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtosvalorpar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtosentrada = new javax.swing.JTextField();
        bntospesquisar = new javax.swing.JButton();
        bntosremover = new javax.swing.JButton();
        bntosadicionar = new javax.swing.JButton();
        bntosalterar = new javax.swing.JButton();
        bntosimprimir = new javax.swing.JButton();
        txtosobs = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtosobss = new javax.swing.JTextPane();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        setToolTipText("");
        setMinimumSize(new java.awt.Dimension(548, 626));
        setPreferredSize(new java.awt.Dimension(548, 626));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº OS");

        jLabel2.setText("Data");

        txtosid.setEditable(false);
        jScrollPane1.setViewportView(txtosid);

        txtosdata.setEditable(false);
        txtosdata.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jScrollPane2.setViewportView(txtosdata);

        buttonGroup1.add(rbtOr);
        rbtOr.setText("Orçamento");
        rbtOr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOs);
        rbtOs.setText("Ordem de Serviço");
        rbtOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsActionPerformed(evt);
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(26, 26, 26)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOr)
                        .addGap(18, 18, 18)
                        .addComponent(rbtOs)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOr)
                    .addComponent(rbtOs))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Aluno"));

        txtclipesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtclipesquisarKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(txtclipesquisar);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/2984_find_magnifying glass_search_zoom_icon.png"))); // NOI18N

        jLabel4.setText("*ID");

        txtcliid.setEditable(false);

        tblclientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone"
            }
        ));
        tblclientes.setToolTipText("");
        tblclientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblclientesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblclientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcliid, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcliid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel11.setText("*Tipo de Serviço");

        cboosservico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "1ª Habilitação Categoria A", "1ª Habilitação Categoria B", "1ª Habilitação Categoria A/B", "1ª Habilitação Categoria D" }));

        jLabel5.setText("*Valor  R$");

        buttonGroup2.add(rbtosvista);
        rbtosvista.setText(" A vista");
        rbtosvista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtosvistaActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbtosparcelado);
        rbtosparcelado.setText("Parcelado");
        rbtosparcelado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtosparceladoActionPerformed(evt);
            }
        });

        cboosparcelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "1x", "2x", "3x", "4x", "5x", "6x" }));

        jLabel6.setText("* Entrada R$");

        txtosvalorpar.setText("0,00");

        jLabel7.setText("* Valor das parcelas R$");

        txtosentrada.setText("0,00");

        bntospesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/lupa.png"))); // NOI18N
        bntospesquisar.setToolTipText("Pesquisar");
        bntospesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntospesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntospesquisarActionPerformed(evt);
            }
        });

        bntosremover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/excluir.png"))); // NOI18N
        bntosremover.setToolTipText("Excluir");
        bntosremover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntosremover.setEnabled(false);
        bntosremover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntosremoverActionPerformed(evt);
            }
        });

        bntosadicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/adicionar.png"))); // NOI18N
        bntosadicionar.setToolTipText("Adicionar");
        bntosadicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntosadicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntosadicionarActionPerformed(evt);
            }
        });

        bntosalterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/anotação.png"))); // NOI18N
        bntosalterar.setToolTipText("Update");
        bntosalterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntosalterar.setEnabled(false);
        bntosalterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntosalterarActionPerformed(evt);
            }
        });

        bntosimprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/impresssora.png"))); // NOI18N
        bntosimprimir.setToolTipText("Imprimir OS");
        bntosimprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntosimprimir.setEnabled(false);
        bntosimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntosimprimirActionPerformed(evt);
            }
        });

        txtosobs.setText("Observação ");

        jScrollPane5.setViewportView(txtosobss);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtostotal, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtosentrada, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(rbtosvista)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cboosservico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtosobs))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtosvalorpar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbtosparcelado)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboosparcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(bntosadicionar)
                        .addGap(12, 12, 12)
                        .addComponent(bntospesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bntosalterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bntosremover)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bntosimprimir)))
                .addGap(17, 17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cboosservico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtosobs))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtostotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtosentrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbtosvista)
                            .addComponent(rbtosparcelado)
                            .addComponent(cboosparcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtosvalorpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bntosremover)
                    .addComponent(bntosalterar)
                    .addComponent(bntospesquisar)
                    .addComponent(bntosadicionar)
                    .addComponent(bntosimprimir))
                .addGap(35, 35, 35))
        );

        setBounds(0, 0, 548, 626);
    }// </editor-fold>//GEN-END:initComponents

    private void rbtOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsActionPerformed
        // a linha abaixo atribui um texto a variavel ordem de servico se selecionada
        tipo = "Ordem de Serviço";
    }//GEN-LAST:event_rbtOsActionPerformed

    private void bntospesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntospesquisarActionPerformed
        //chamando o metodo consultar
        pesquisar_OS();
    }//GEN-LAST:event_bntospesquisarActionPerformed

    private void bntosremoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntosremoverActionPerformed
        //chamando o metodo excluir
        excluir_os();
    }//GEN-LAST:event_bntosremoverActionPerformed

    private void bntosadicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntosadicionarActionPerformed
        //chamando o metodo adicionar
        emitir_OS();
    }//GEN-LAST:event_bntosadicionarActionPerformed

    private void bntosalterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntosalterarActionPerformed
        //chamando o metodo alterar
        alterar_os();
    }//GEN-LAST:event_bntosalterarActionPerformed

    private void bntosimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntosimprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bntosimprimirActionPerformed

    private void txtclipesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtclipesquisarKeyReleased
        //chamando o metodo pessquisar aluno 
        pesquisar_cliente();
    }//GEN-LAST:event_txtclipesquisarKeyReleased

    private void tblclientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblclientesMouseClicked
        // chamando o metodo setar campos
        setar_campos();
    }//GEN-LAST:event_tblclientesMouseClicked

    private void rbtOrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrActionPerformed
        // atribuindo um texto a variavel tipo se selecionado
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOrActionPerformed

    private void rbtosvistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtosvistaActionPerformed
        // Tatribui um texto a variavel a vista se selecionado
        forma_pagamento = "A vista";

    }//GEN-LAST:event_rbtosvistaActionPerformed

    private void rbtosparceladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtosparceladoActionPerformed
        // atribui um texto a variavel parcelas se selecionado
        forma_pagamento = "Parcelado";
    }//GEN-LAST:event_rbtosparceladoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // ao abrir o frome amrcar o radio button orçamento
        rbtOr.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntosadicionar;
    private javax.swing.JButton bntosalterar;
    private javax.swing.JButton bntosimprimir;
    private javax.swing.JButton bntospesquisar;
    private javax.swing.JButton bntosremover;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboosparcelas;
    private javax.swing.JComboBox<String> cboosservico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JRadioButton rbtOr;
    private javax.swing.JRadioButton rbtOs;
    private javax.swing.JCheckBox rbtosparcelado;
    private javax.swing.JCheckBox rbtosvista;
    private javax.swing.JTable tblclientes;
    private javax.swing.JTextField txtcliid;
    private javax.swing.JTextPane txtclipesquisar;
    private javax.swing.JTextPane txtosdata;
    private javax.swing.JTextField txtosentrada;
    private javax.swing.JTextPane txtosid;
    private javax.swing.JLabel txtosobs;
    private javax.swing.JTextPane txtosobss;
    private javax.swing.JTextField txtostotal;
    private javax.swing.JTextField txtosvalorpar;
    // End of variables declaration//GEN-END:variables

}
