/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.teste.dal;

import java.sql.*;

/**
 *
 * @author João
 */
public class ModuloConexao {
    public static Connection conector(){
        Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/dbteste?characterEncoding=utf-8";
        String user= "jj";
        String password = "Dvjvlv1998@";
        
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;    
        } catch (Exception e) {
            //a onde esta o erro
            //System.out.println(e);
            return null;
        }
    }
}
