/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import java.sql.Connection;
import com.ufpr.tads.web2.beans.CidadeBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sa
 */
public class CidadeDao {

    public CidadeBean buscaCidadePorId(int id) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        CidadeBean cidade = new CidadeBean();
        ResultSet rs = null;
        try {
            st = con.prepareStatement("select * from trabalho.cidade WHERE id_cidade = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                cidade.setId(rs.getInt("id_cidade"));
                cidade.setNome(rs.getString("nome_cidade"));
                break;
            }
            return cidade;
        } catch (SQLException e) {
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public List<CidadeBean> buscaCidadesPorEstado(int idEstado) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<CidadeBean> cidades = new ArrayList<>();
        try {
            st = con.prepareStatement("select id_cidade, nome_cidade from trabalho.cidade where id_estado = ?");
            st.setInt(1, idEstado);
            rs = st.executeQuery();
            while (rs.next()) {
                CidadeBean cidade = new CidadeBean();
                cidade.setId(rs.getInt("id_cidade"));
                cidade.setNome(rs.getString("nome_cidade"));
                cidades.add(cidade);
            }
            return cidades;
        } catch (SQLException e) {
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
}
