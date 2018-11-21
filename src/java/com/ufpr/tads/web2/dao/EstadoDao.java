/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.EstadoBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sa
 */
public class EstadoDao {

    public List<EstadoBean> buscaEstados() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<EstadoBean> estados = new ArrayList<>();
        try {
            st = con.prepareStatement("select id_estado, nome_estado, sigla_estado from trabalho.estado");
            rs = st.executeQuery();
            while (rs.next()) {
                EstadoBean estado = new EstadoBean();
                estado.setId(rs.getInt("id_estado"));
                estado.setNome(rs.getString("nome_estado"));
                estado.setSigla(rs.getString("sigla_estado"));
                estados.add(estado);
            }
            return estados;
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

    public EstadoBean buscaEstadoPorId(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        EstadoBean estado = new EstadoBean();
        estado.setId(id);
        try {
            st = con.prepareStatement("select nome_estado, sigla_estado from trabalho.estado WHERE id_estado = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                estado.setNome(rs.getString("nome_estado"));
                estado.setSigla(rs.getString("sigla_estado"));

                break;
            }
            return estado;
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

    public EstadoBean buscaEstadoPorIdC(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        EstadoBean estado = new EstadoBean();
//        estado.setId(id);
        try {
            st = con.prepareStatement("select * from trabalho.estado as a, trabalho.cidade as b WHERE b.id_cidade = ? and b.id_estado = a.id_estado");
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                estado.setId(rs.getInt("id_estado"));
                estado.setNome(rs.getString("nome_estado"));
                estado.setSigla(rs.getString("sigla_estado"));

                break;
            }
            return estado;
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
