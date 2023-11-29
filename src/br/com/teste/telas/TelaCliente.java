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
//a linha abaixo importa recursos 
import net.proteanit.sql.DbUtils;

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();

    }

    private void adicionar() {
        String sql = "insert into clientes(numeroprocesso,nome,CPF,RG,telefone,email,endereco,numerodacasa,complemento,bairro,cep,cidade) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtclinump.getText());
            pst.setString(2, txtclinome.getText());
            pst.setString(3, txtclicpf.getText());
            pst.setString(4, txtclirg.getText());
            pst.setString(5, txtclitel.getText());
            pst.setString(6, txtcliema.getText());
            pst.setString(7, txtcliend.getText());
            pst.setString(8, txtclin.getText());
            pst.setString(9, txtclicom.getText());
            pst.setString(10, txtclibai.getText());
            pst.setString(11, txtclicep.getText());
            pst.setString(12, txtclicidade.getText());
            //to string faz a conversão do combo box para string

            //validação dos campos obrigatórios
            if ((txtclinome.getText().isEmpty()) || (txtclicpf.getText().isEmpty()) || (txtcliend.getText().isEmpty()) || (txtclibai.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");

            } else {

                //a linha abaixo atualiza a tabela do usuario com os dados do formulario 
                // a estrutura abaixo e usada pára confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                // a linhaa baxio serve de apoio para o entendimento a logica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente cadastrato com sucesso");
                    limpar();

                }
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }
    }

    //metodo par pesquisar clientes pelo nome com filtro       
    private void pesquisar_cliente() {
        String sql = "select * from clientes where nome like ? ";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de pesquisa para o interoga
            //atencao ao "%" continuação da string sql
            pst.setString(1, clilocalizar.getText() + "%");
            rs = pst.executeQuery();
            // a linha abaixo usa a biblioteca para preencher a tabela
            tblclientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }
    }
// metodo para setar os campos do formulario com o conteudo da tabela

    public void setar_campos() {
        int setar = tblclientes.getSelectedRow(); // o campo 0 é o ID do cliente
        txtcliid.setText(tblclientes.getModel().getValueAt(setar, 0).toString());
        txtclinump.setText(tblclientes.getModel().getValueAt(setar, 1).toString());
        txtclinome.setText(tblclientes.getModel().getValueAt(setar, 2).toString());
        txtclicpf.setText(tblclientes.getModel().getValueAt(setar, 3).toString());
        txtclirg.setText(tblclientes.getModel().getValueAt(setar, 4).toString());
        txtclitel.setText(tblclientes.getModel().getValueAt(setar, 5).toString());
        txtcliema.setText(tblclientes.getModel().getValueAt(setar, 6).toString());
        txtcliend.setText(tblclientes.getModel().getValueAt(setar, 7).toString());
        txtclin.setText(tblclientes.getModel().getValueAt(setar, 8).toString());
        txtclicom.setText(tblclientes.getModel().getValueAt(setar, 9).toString());
        txtclibai.setText(tblclientes.getModel().getValueAt(setar, 10).toString());
        txtclicep.setText(tblclientes.getModel().getValueAt(setar, 11).toString());
        txtclicidade.setText(tblclientes.getModel().getValueAt(setar, 12).toString());
       

        // a linha abaixo dezabvilita o botao adiconar
        adicionarcli.setEnabled(false);

    }

    private void alterar() {
        String sql = "update clientes set numeroprocesso=?,nome=?,CPF=?,RG=?,telefone=?,email=?,endereco=?,numerodacasa=?,complemento=?,bairro=?,cep=?,cidade=? where idcliente=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtclinump.getText());
            pst.setString(2, txtclinome.getText());
            pst.setString(3, txtclicpf.getText());
            pst.setString(4, txtclirg.getText());
            pst.setString(5, txtclitel.getText());
            pst.setString(6, txtcliema.getText());
            pst.setString(7, txtcliend.getText());
            pst.setString(8, txtclin.getText());
            pst.setString(9, txtclicom.getText());
            pst.setString(10, txtclibai.getText());
            pst.setString(11, txtclicep.getText());
            pst.setString(12, txtclicidade.getText());
            
            pst.setString(13, txtcliid.getText());
            //to string faz a conversão do combo box para string
           

            if ((txtclinome.getText().isEmpty()) || (txtclicpf.getText().isEmpty()) || (txtcliend.getText().isEmpty()) || (txtclibai.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");

            } else {

                //a linha abaixo atualiza a tabela do cliente com os dados do formulario 
                // a estrutura abaixo e usada pára confirmar a alteração dos dados na tabela
                int adicionado = pst.executeUpdate();
                // a linhaa baxio serve de apoio para o entendimento a logica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente alterados  com sucesso");
                    limpar();
                    adicionarcli.setEnabled(true);

                }
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }

    }

    private void remover() {
        //a estrutura abaxio confirma a exclusão do usuario
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from clientes where idcliente=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtcliid.getText());

                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
                   limpar();
                    adicionarcli.setEnabled(true);
                }

            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
        }

    }
    //mmetodo para limpar os campos do formulario 

    private void limpar() {
        clilocalizar.setText(null);
        txtcliid.setText(null);
        txtclinump.setText(null);
        txtclinome.setText(null);
        txtclicpf.setText(null);
        txtclirg.setText(null);
        txtclitel.setText(null);
        txtcliema.setText(null);
        txtcliend.setText(null);
        txtclin.setText(null);
        txtclicom.setText(null);
        txtclibai.setText(null);
        txtclicep.setText(null);
        txtclicidade.setText(null);
       
        // esta linha limpa os campos da tabela 
        ((DefaultTableModel) tblclientes.getModel()).setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        adicionarcli = new javax.swing.JButton();
        alterarcli = new javax.swing.JButton();
        removercli = new javax.swing.JButton();
        txtclinump = new javax.swing.JTextField();
        txtclinome = new javax.swing.JTextField();
        txtclicpf = new javax.swing.JTextField();
        txtcliend = new javax.swing.JTextField();
        txtclitel = new javax.swing.JTextField();
        txtcliema = new javax.swing.JTextField();
        txtclin = new javax.swing.JTextField();
        txtclicom = new javax.swing.JTextField();
        txtclirg = new javax.swing.JTextField();
        txtclibai = new javax.swing.JTextField();
        txtclicep = new javax.swing.JTextField();
        txtclicidade = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        clilocalizar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblclientes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtcliid = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro de Alunos");
        setMinimumSize(new java.awt.Dimension(548, 626));
        setPreferredSize(new java.awt.Dimension(548, 626));

        jLabel2.setText("Nº Processo");

        jLabel3.setText("*Nome");

        jLabel4.setText("*CPF");

        jLabel5.setText("RG");

        jLabel6.setText("*Endereço");

        jLabel7.setText("Telefone");

        jLabel8.setText("E-mail");

        jLabel9.setText("Nº");

        jLabel10.setText("Complemento");

        jLabel12.setText("*Bairro");

        jLabel13.setText("Cep");

        jLabel15.setText("Cidade");

        adicionarcli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/adicionar.png"))); // NOI18N
        adicionarcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarcliActionPerformed(evt);
            }
        });

        alterarcli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/anotação.png"))); // NOI18N
        alterarcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterarcliActionPerformed(evt);
            }
        });

        removercli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/excluir.png"))); // NOI18N
        removercli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removercliActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/2984_find_magnifying glass_search_zoom_icon.png"))); // NOI18N

        clilocalizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                clilocalizarKeyReleased(evt);
            }
        });

        tblclientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int conIndex){
                return false;

            }

        };
        tblclientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Processo", "Nome", "Serviço"
            }
        ));
        tblclientes.setFocusable(false);
        tblclientes.getTableHeader().setReorderingAllowed(false);
        tblclientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblclientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblclientes);

        jLabel1.setText("ID");

        txtcliid.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(clilocalizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtclinump, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtclinome))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(adicionarcli)
                                .addGap(18, 18, 18)
                                .addComponent(alterarcli)
                                .addGap(18, 18, 18)
                                .addComponent(removercli))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtclicpf, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtclirg, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtclitel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtcliema))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtclin, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(62, 62, 62)
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtclicom, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtcliend, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtclibai, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtclicep, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtclicidade, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcliid, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtclinump, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(clilocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcliid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtclinome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtclicpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtclirg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtclitel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtcliema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtcliend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(txtclin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtclicom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtclibai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtclicep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtclicidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removercli)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(adicionarcli)
                        .addComponent(alterarcli)))
                .addGap(32, 32, 32))
        );

        setBounds(0, 0, 541, 626);
    }// </editor-fold>//GEN-END:initComponents

    private void removercliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removercliActionPerformed
        // chamando o metodo remover
        remover();
    }//GEN-LAST:event_removercliActionPerformed

    private void adicionarcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarcliActionPerformed
        //metodo para adicionar clientes
        adicionar();
    }//GEN-LAST:event_adicionarcliActionPerformed

    private void alterarcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterarcliActionPerformed
        //metodo para alterar dados do cliente
        alterar();
    }//GEN-LAST:event_alterarcliActionPerformed

    private void clilocalizarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clilocalizarKeyReleased
        //o evento abaixo e doi tipo enquanto estiver digitando vai colocando abaixo 
        pesquisar_cliente();
    }//GEN-LAST:event_clilocalizarKeyReleased

    private void tblclientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblclientesMouseClicked
        //evento que sera usado para setar os campos das tabela com mouse
        setar_campos();
    }//GEN-LAST:event_tblclientesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionarcli;
    private javax.swing.JButton alterarcli;
    private javax.swing.JTextField clilocalizar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removercli;
    private javax.swing.JTable tblclientes;
    private javax.swing.JTextField txtclibai;
    private javax.swing.JTextField txtclicep;
    private javax.swing.JTextField txtclicidade;
    private javax.swing.JTextField txtclicom;
    private javax.swing.JTextField txtclicpf;
    private javax.swing.JTextField txtcliema;
    private javax.swing.JTextField txtcliend;
    private javax.swing.JTextField txtcliid;
    private javax.swing.JTextField txtclin;
    private javax.swing.JTextField txtclinome;
    private javax.swing.JTextField txtclinump;
    private javax.swing.JTextField txtclirg;
    private javax.swing.JTextField txtclitel;
    // End of variables declaration//GEN-END:variables
}
