/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.Atendimento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sa
 */
public class AtendimentoDao {

    public void adiciona(Atendimento at) {
        Connection con = ConnectionFactory.getConnection();
        String sql = ("insert into trabalho.atendimento(dataHora,id_cliente,situacao,descricao,id_produto,id_tipoAt) values (?,?,?,?,?,?)");
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setTimestamp(1, new java.sql.Timestamp(at.getDataHora().getTime()));
            stmt.setInt(2, at.getId_cliente());
            stmt.setString(3, "aberto");
            stmt.setString(4, at.getDescricao());
            stmt.setInt(5, at.getId_produto());
            stmt.setInt(6, at.getId_tipo_atendimento());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {          
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }

//    public List<Atendimento> buscarAtendimentos(int id) {
//        List<Atendimento> resultados = new ArrayList<Atendimento>();
//        Connection con = new ConnectionFactory().getConnection();
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        try {
//            con = ConnectionFactory.getConnection();
//            st = con.prepareStatement("SELECT * FROM teste.tb_atendimento where id_usuario = ?");
//            st.setInt(1, id);
//            rs = st.executeQuery();
//            while (rs.next()) {
//               Atendimento a = new Atendimento();
//                a.setId_atendimento(rs.getInt("id_atendimento"));
//                a.setDt_hr_atendimento(rs.getTimestamp("dt_hr_atendimento"));
//                a.setDsc_atendimento(rs.getString("dsc_atendimento"));
//                a.setId_produto(rs.getInt("id_produto"));
//                a.setId_tipo_atendimento(rs.getInt("id_tipo_atendimento"));
//                a.setId_usuario(rs.getInt("id_usuario"));
//                a.setId_cliente(rs.getInt("id_cliente"));
//                a.setRes_atendimento(rs.getString("res_atendimento"));
//                resultados.add(a);
//            }
//            return resultados;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (st != null) {
//                try {
//                    st.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                }
//            }
//        }
//    }
    public boolean remove(int id) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st;
        try{
        st = con.prepareStatement("delete FROM trabalho.atendimento WHERE id_atendimento = ?");
        st.setInt(1, id);
        st.executeUpdate();
        st.close();
        return true;
        } catch (SQLException ex) {
            System.err.println("Erro" + ex);
            return false;
        } finally {          
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public boolean resolver(Atendimento a) {
        Connection con = ConnectionFactory.getConnection();
        String sql = ("update trabalho.atendimento SET solucao=?,situacao=? WHERE id_atendimento = ?");
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(sql);
            st = con.prepareStatement(sql);
            st.setString(1, a.getSolucao());
            st.setString(2, "finalizado");
            st.setInt(3, a.getId_atendimento());
            st.executeUpdate();
            st.getResultSet();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro" + ex);
            return false;
        } finally {            
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    public List<Atendimento> buscarAtendimentosNaoResolvidos() throws SQLException {
        List<Atendimento> resultados = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT * FROM trabalho.atendimento where situacao = 'aberto' order by dataHora asc;");
            rs = st.executeQuery();
            while (rs.next()) {
                Atendimento a = new Atendimento();
                a.setId_atendimento(rs.getInt("id_atendimento"));
                a.setDataHora(rs.getTimestamp("dataHora"));
                a.setId_cliente(rs.getInt("id_cliente"));
                a.setSituacao(rs.getString("situacao"));
                a.setDescricao(rs.getString("descricao"));
                a.setSolucao(rs.getString("solucao"));
                a.setId_produto(rs.getInt("id_produto"));
                a.setId_tipo_atendimento(rs.getInt("id_tipoAt"));
                resultados.add(a);
            }
            return resultados;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public List<Atendimento> buscarAtendimentosNaoResolvidosPorTipo(int id) throws SQLException {
        List<Atendimento> resultados = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT * FROM trabalho.atendimento where situacao = 'aberto' and id_tipoAt = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                Atendimento a = new Atendimento();
                a.setId_atendimento(rs.getInt("id_atendimento"));
                a.setDataHora(rs.getTimestamp("dataHora"));
                a.setId_cliente(rs.getInt("id_cliente"));
                a.setSituacao(rs.getString("situacao"));
                a.setDescricao(rs.getString("descricao"));
                a.setSolucao(rs.getString("descricao"));
                a.setId_produto(rs.getInt("id_produto"));
                a.setId_tipo_atendimento(rs.getInt("id_tipoAt"));
                resultados.add(a);
            }
            return resultados;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public List<Atendimento> buscarAtendimentosPorTipo(int id) throws SQLException {
        List<Atendimento> resultados = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT * FROM trabalho.atendimento where id_tipoAt = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                Atendimento a = new Atendimento();
                a.setId_atendimento(rs.getInt("id_atendimento"));
                a.setDataHora(rs.getTimestamp("dataHora"));
                a.setId_cliente(rs.getInt("id_cliente"));
                a.setSituacao(rs.getString("situacao"));
                a.setDescricao(rs.getString("descricao"));
                a.setSolucao(rs.getString("descricao"));
                a.setId_produto(rs.getInt("id_produto"));
                a.setId_tipo_atendimento(rs.getInt("id_tipoAt"));
                resultados.add(a);
            }
            return resultados;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public List<Atendimento> listAtendimentosCli(int id) throws SQLException, SQLException, SQLException {
        List<Atendimento> resultados = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT * FROM trabalho.atendimento where id_cliente = ? order by dataHora desc;");
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                Atendimento a = new Atendimento();
                a.setId_atendimento(rs.getInt("id_atendimento"));
                a.setDataHora(rs.getTimestamp("dataHora"));
                a.setId_cliente(rs.getInt("id_cliente"));
                a.setSituacao(rs.getString("situacao"));
                a.setDescricao(rs.getString("descricao"));
                a.setSolucao(rs.getString("descricao"));
                a.setId_produto(rs.getInt("id_produto"));
                a.setId_tipo_atendimento(rs.getInt("id_tipoAt"));
                resultados.add(a);
            }
            return resultados;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public List<Atendimento> buscarTodos() throws SQLException, SQLException, SQLException {
        List<Atendimento> resultados = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT * FROM trabalho.atendimento order by dataHora asc;");
            rs = st.executeQuery();
            while (rs.next()) {
                Atendimento a = new Atendimento();
                a.setId_atendimento(rs.getInt("id_atendimento"));
                a.setDataHora(rs.getTimestamp("dataHora"));
                a.setId_cliente(rs.getInt("id_cliente"));
                a.setSituacao(rs.getString("situacao"));
                a.setDescricao(rs.getString("descricao"));
                a.setSolucao(rs.getString("solucao"));
                a.setId_produto(rs.getInt("id_produto"));
                a.setId_tipo_atendimento(rs.getInt("id_tipoAt"));
                resultados.add(a);
            }
            return resultados;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public Atendimento buscaAtendimento(int id_atendimento) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        Atendimento a = new Atendimento();
        ResultSet rs = null;
        try {
            st = con.prepareStatement("SELECT * FROM trabalho.atendimento WHERE id_atendimento = ?");
            st.setInt(1, id_atendimento);
            rs = st.executeQuery();
            if (rs.next()) {
                a.setId_atendimento(rs.getInt("id_atendimento"));
                a.setDataHora(rs.getTimestamp("dataHora"));
                a.setId_cliente(rs.getInt("id_cliente"));
                a.setSituacao(rs.getString("situacao"));
                a.setDescricao(rs.getString("descricao"));
                a.setSolucao(rs.getString("solucao"));
                a.setId_produto(rs.getInt("id_produto"));
                a.setId_tipo_atendimento(rs.getInt("id_tipoAt"));
            }
            return a;            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {   
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
