/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.Criptografia;
import com.ufpr.tads.web2.beans.UsuarioBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author sa
 */
public class LoginDao {
    public UsuarioBean BuscaUser(String email, String senha) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st;
        UsuarioBean user = new UsuarioBean();
        String s = Criptografia.criptografar(senha);
        st = con.prepareStatement("SELECT nome,id,tipo FROM trabalho.usuario WHERE email = ? and senha = ?");
        st.setString(1, email);
        st.setString(2, s);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            user.setNome(rs.getString(1));
            user.setId(rs.getInt(2));
            user.setTipo(rs.getString(3));
        }
        return user;
    }
}
