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

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
        
    }
    // metodo para consultar usuarios
    private void consultar(){
        String sql = "select * from usuarios where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1,txtusuid.getText());
            rs=pst.executeQuery();
            if (rs.next()) {
               txtusunome.setText(rs.getString(2));
               txtusufone.setText(rs.getString(3));
               txtusulogin.setText(rs.getString(4));
               txtususenha.setText(rs.getString(5));
               // a linha abaixo se refere ao combo box
               cbousuperfil.setSelectedItem(rs.getString(6));
            }else{
                JOptionPane.showMessageDialog(null,"Usuário não cadastrato");
                // as lionhas abaixo limmpam os campos
                limpar();
               
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null,e);
        }
    }
    //metodo para adicionar usuarios
    private void adicionar(){
       String sql =  "insert into usuarios(iduser,usuario,fone,login,senha,perfil) values(?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1,txtusuid.getText());
            pst.setString(2,txtusunome.getText());
            pst.setString(3,txtusufone.getText());
            pst.setString(4,txtusulogin.getText());
            pst.setString(5,txtususenha.getText());
            pst.setString(6,cbousuperfil.getSelectedItem().toString());
            if ((txtusuid.getText().isEmpty())||(txtusunome.getText().isEmpty())||(txtusulogin.getText().isEmpty())||(txtususenha.getText().isEmpty())) {
                 JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios");
                
            } else {
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){
               JOptionPane.showMessageDialog(null,"Usuário cadastrato com sucesso");
                limpar();
                
            }
            }
            
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null,e);
        }
    }
      
    // criando o metodo para alterar dados do usuario
    
    private void alterar(){
        String sql = "update usuarios set usuario=?,fone=?,login=?,senha=?,perfil=? where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1,txtusunome.getText());
            pst.setString(2,txtusufone.getText());
            pst.setString(3,txtusulogin.getText());
            pst.setString(4,txtususenha.getText());
            pst.setString(5,cbousuperfil.getSelectedItem().toString());
            pst.setString(6,txtusuid.getText());
            
            if ((txtusuid.getText().isEmpty())||(txtusunome.getText().isEmpty())||(txtusulogin.getText().isEmpty())||(txtususenha.getText().isEmpty())) {
                 JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios");
                
            } else {
            
            //a linha abaixo atualiza a tabela do usuario com os dados do formulario 
            // a estrutura abaixo e usada pára confirmar a alteração dos dados na tabela
            int adicionado = pst.executeUpdate();
            // a linhaa baxio serve de apoio para o entendimento a logica
            //System.out.println(adicionado);
            if(adicionado > 0){
               JOptionPane.showMessageDialog(null,"Dados alterados com sucesso");
                limpar();
                
            }
            }
        } catch (Exception e) {
           JOptionPane.showConfirmDialog(null,e); 
        }
        
    }
    //metodo responsavel pela remoção de usuarios
    private void remover(){
        //a estrutura abaxio confirma a exclusão do usuario
        int confirma = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja remover este usuário?","Atenção",JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
            String sql = "delete from usuarios where iduser=?";
            try {
                pst=conexao.prepareStatement(sql);
                pst.setString(1,txtusuid.getText());
                int apagado = pst.executeUpdate();
                if(apagado>0){
                    JOptionPane.showMessageDialog(null,"Usuário removido com sucesso!");
                    limpar();
                }
                        
                
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null,e); 
            }
        }
        
    }
    private void limpar(){
                txtusuid.setText(null);
                txtusunome.setText(null);
                txtusufone.setText(null);
                txtusulogin.setText(null);
                txtususenha.setText(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtusuid = new javax.swing.JTextField();
        txtusunome = new javax.swing.JTextField();
        txtusufone = new javax.swing.JTextField();
        txtusulogin = new javax.swing.JTextField();
        txtususenha = new javax.swing.JTextField();
        cbousuperfil = new javax.swing.JComboBox<>();
        btnusucreate = new javax.swing.JButton();
        btnusuupdate = new javax.swing.JButton();
        btnusuread = new javax.swing.JButton();
        btnusudelete = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuarios");
        setToolTipText("");
        setMinimumSize(new java.awt.Dimension(548, 626));
        setPreferredSize(new java.awt.Dimension(548, 626));

        jLabel1.setText("*Id");

        jLabel2.setText("*Nome Usuario");

        jLabel3.setText("Fone");

        jLabel4.setText("*Senha");

        jLabel5.setText("*Login");

        jLabel6.setText("*Perfil");

        txtususenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtususenhaActionPerformed(evt);
            }
        });

        cbousuperfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin\t", "user" }));

        btnusucreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/adicionar.png"))); // NOI18N
        btnusucreate.setToolTipText("Adicionar");
        btnusucreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnusucreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnusucreateActionPerformed(evt);
            }
        });

        btnusuupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/anotação.png"))); // NOI18N
        btnusuupdate.setToolTipText("Update");
        btnusuupdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnusuupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnusuupdateActionPerformed(evt);
            }
        });

        btnusuread.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/lupa.png"))); // NOI18N
        btnusuread.setToolTipText("Pesquisar");
        btnusuread.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnusuread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnusureadActionPerformed(evt);
            }
        });

        btnusudelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/teste/icones/excluir.png"))); // NOI18N
        btnusudelete.setToolTipText("Excluir");
        btnusudelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnusudelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnusudeleteActionPerformed(evt);
            }
        });

        jLabel7.setText("*Campos Obrigatórios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtusunome, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtusufone, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtusulogin)
                                    .addComponent(txtususenha)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cbousuperfil, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtusuid, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnusucreate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnusuupdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnusuread)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnusudelete)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtusuid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtusunome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtusufone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtusulogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtususenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbousuperfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnusucreate)
                    .addComponent(btnusuupdate)
                    .addComponent(btnusuread)
                    .addComponent(btnusudelete))
                .addGap(51, 51, 51))
        );

        setBounds(0, 0, 548, 626);
    }// </editor-fold>//GEN-END:initComponents

    private void txtususenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtususenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtususenhaActionPerformed

    private void btnusuupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnusuupdateActionPerformed
        //chamando o metodo alterar
        alterar();   
    }//GEN-LAST:event_btnusuupdateActionPerformed

    private void btnusureadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnusureadActionPerformed
          //chamando o metodo consultar
        consultar();
    }//GEN-LAST:event_btnusureadActionPerformed

    private void btnusucreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnusucreateActionPerformed
        //chamando o metodo adicionar
        adicionar();
    }//GEN-LAST:event_btnusucreateActionPerformed

    private void btnusudeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnusudeleteActionPerformed
        //chamando o metodo excluir
        remover();
    }//GEN-LAST:event_btnusudeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnusucreate;
    private javax.swing.JButton btnusudelete;
    private javax.swing.JButton btnusuread;
    private javax.swing.JButton btnusuupdate;
    private javax.swing.JComboBox<String> cbousuperfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtusufone;
    private javax.swing.JTextField txtusuid;
    private javax.swing.JTextField txtusulogin;
    private javax.swing.JTextField txtusunome;
    private javax.swing.JTextField txtususenha;
    // End of variables declaration//GEN-END:variables
}
