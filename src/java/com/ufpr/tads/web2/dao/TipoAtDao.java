/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.TipoAtendimento;
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
public class TipoAtDao {

    public List<TipoAtendimento> buscaTiposAt() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<TipoAtendimento> tiposAt = new ArrayList<>();
        try {
            st = con.prepareStatement("Select * from trabalho.tipo_atendimento;");
            rs = st.executeQuery();
            while (rs.next()) {
                TipoAtendimento ta = new TipoAtendimento();
                ta.setId_tipoAt(rs.getInt("id_tipoAt"));
                ta.setNome_tipoAt(rs.getString("nome_tipoAt"));
                tiposAt.add(ta);
            }
            return tiposAt;
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

    public TipoAtendimento buscaTiposAt(int id) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st;
        TipoAtendimento ta = new TipoAtendimento();
        st = con.prepareStatement("SELECT * FROM trabalho.tipo_atendimento WHERE id_tipoAt = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
          ta.setId_tipoAt(rs.getInt("id_tipoAt"));
          ta.setNome_tipoAt(rs.getString("nome_tipoAt"));
        }
        return ta;
    }

}
