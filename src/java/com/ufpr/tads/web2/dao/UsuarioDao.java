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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sa
 */
public class UsuarioDao {

    public boolean adiciona(UsuarioBean usuario) {
        Connection con = ConnectionFactory.getConnection();
        String sql = ("insert into trabalho.usuario (nome,tipo,cpf,email,tel,rua,nr_casa,complemento,cep,id_cidade,senha) values (?,?,?,?,?,?,?,?,?,?,?)");
        try {
            // prepared statement para inserção
            PreparedStatement stmt = con.prepareStatement(sql);
            // seta os valores
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getTipo());
            stmt.setString(3, usuario.getCpf().replaceAll("[.-]", ""));
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getTel());
            stmt.setString(6, usuario.getRua());
            stmt.setInt(7, usuario.getNr_casa());
            stmt.setString(8, usuario.getComplemento());
            stmt.setString(9, usuario.getCep());
            stmt.setInt(10, usuario.getId_cidade());
            stmt.setString(11, Criptografia.criptografar(usuario.getSenha()));
            // executa
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public List<UsuarioBean> buscarTodosFuncionarios() throws SQLException {
        List<UsuarioBean> resultados = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT * FROM trabalho.usuario where tipo != 'C';");
            CidadeDao cidadeDAO = new CidadeDao();
            EstadoDao estadoDAO = new EstadoDao();
            rs = st.executeQuery();
            while (rs.next()) {
                UsuarioBean ub = new UsuarioBean();
                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));
                ub.setTipo(rs.getString("tipo"));
                ub.setCpf(rs.getString("cpf"));
                ub.setEmail(rs.getString("email"));
                ub.setTel(rs.getString("tel"));
                ub.setRua(rs.getString("rua"));
                ub.setNr_casa(rs.getInt("nr_casa"));
                ub.setComplemento(rs.getString("complemento"));
                ub.setCep(rs.getString("cep"));
                ub.setCidade(cidadeDAO.buscaCidadePorId(rs.getInt("id_cidade")));
                ub.setEstado(estadoDAO.buscaEstadoPorIdC(rs.getInt("id_cidade")));
                resultados.add(ub);
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

    public List<UsuarioBean> buscarTodosClientes() throws SQLException {
        List<UsuarioBean> resultados = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT * FROM trabalho.usuario where tipo = 'C';");
            CidadeDao cidadeDAO = new CidadeDao();
            EstadoDao estadoDAO = new EstadoDao();
            rs = st.executeQuery();
            while (rs.next()) {
                UsuarioBean ub = new UsuarioBean();
                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));
                ub.setTipo(rs.getString("tipo"));
                ub.setCpf(rs.getString("cpf"));
                ub.setEmail(rs.getString("email"));
                ub.setTel(rs.getString("tel"));
                ub.setRua(rs.getString("rua"));
                ub.setNr_casa(rs.getInt("nr_casa"));
                ub.setComplemento(rs.getString("complemento"));
                ub.setCep(rs.getString("cep"));
                ub.setCidade(cidadeDAO.buscaCidadePorId(rs.getInt("id_cidade")));
                ub.setEstado(estadoDAO.buscaEstadoPorIdC(rs.getInt("id_cidade")));
                resultados.add(ub);
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

    public List<UsuarioBean> buscarTodos() throws SQLException {
        List<UsuarioBean> resultados = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT * FROM trabalho.usuario");
            CidadeDao cidadeDAO = new CidadeDao();
            EstadoDao estadoDAO = new EstadoDao();
            rs = st.executeQuery();
            while (rs.next()) {
                UsuarioBean ub = new UsuarioBean();
                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));
                ub.setTipo(rs.getString("tipo"));
                ub.setCpf(rs.getString("cpf"));
                ub.setEmail(rs.getString("email"));
                ub.setTel(rs.getString("tel"));
                ub.setRua(rs.getString("rua"));
                ub.setNr_casa(rs.getInt("nr_casa"));
                ub.setComplemento(rs.getString("complemento"));
                ub.setCep(rs.getString("cep"));
                ub.setCidade(cidadeDAO.buscaCidadePorId(rs.getInt("id_cidade")));
                ub.setEstado(estadoDAO.buscaEstadoPorIdC(rs.getInt("id_cidade")));
                resultados.add(ub);
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

    public boolean removeUser(int id) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        try {
            UsuarioBean ub = new UsuarioBean();
            st = con.prepareStatement("delete FROM trabalho.usuario WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Erro" + e);
                    return false;
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

    public UsuarioBean BuscaUser(int id) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st;
        CidadeDao cidadeDAO = new CidadeDao();
        EstadoDao estadoDAO = new EstadoDao();
        UsuarioBean ub = new UsuarioBean();
        st = con.prepareStatement("SELECT * FROM trabalho.usuario AS b INNER JOIN trabalho.cidade AS a ON a.id_cidade = b.id_cidade INNER JOIN trabalho.estado AS e ON a.id_estado = e.id_estado WHERE id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            ub.setId(rs.getInt("id"));
            ub.setNome(rs.getString("nome"));
            ub.setTipo(rs.getString("tipo"));
            ub.setCpf(rs.getString("cpf"));
            ub.setEmail(rs.getString("email"));
            ub.setTel(rs.getString("tel"));
            ub.setRua(rs.getString("rua"));
            ub.setNr_casa(rs.getInt("nr_casa"));
            ub.setComplemento(rs.getString("complemento"));
            ub.setCep(rs.getString("cep"));
            ub.setCidade(cidadeDAO.buscaCidadePorId(rs.getInt("id_cidade")));
            ub.setEstado(estadoDAO.buscaEstadoPorIdC(rs.getInt("id_cidade")));
        }
        return ub;
    }

    public boolean update(UsuarioBean c) {
        Connection con = ConnectionFactory.getConnection();
        String sql = ("update trabalho.usuario SET nome=?,tipo=?,cpf=?,email=?,tel=?,rua=?,nr_casa=?,complemento=?,cep=?,id_cidade = ? WHERE id = ?");
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st = con.prepareStatement(sql);
            st.setString(1, c.getNome());
            st.setString(2, c.getTipo());
            st.setString(3, c.getCpf().replaceAll("[.-]", ""));
            st.setString(4, c.getEmail());
            st.setString(5, c.getTel());
            st.setString(6, c.getRua());
            st.setInt(7, c.getNr_casa());
            st.setString(8, c.getComplemento());
            st.setString(9, c.getCep());
            st.setInt(10, c.getId_cidade());
//            st.setString(11, Criptografia.criptografar(c.getSenha()));
            st.setInt(11, c.getId());
            st.executeUpdate();
            st.getResultSet();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro" + ex);
            return false;
        } finally {
        }

    }

    public boolean updateC(UsuarioBean c) {
        Connection con = ConnectionFactory.getConnection();
        String sql = ("update trabalho.usuario SET nome=?,tel=?,rua=?,nr_casa=?,complemento=?,cep=?,id_cidade = ?,senha=? WHERE id = ?");
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st = con.prepareStatement(sql);
            st.setString(1, c.getNome());
            st.setString(2, c.getTel());
            st.setString(3, c.getRua());
            st.setInt(4, c.getNr_casa());
            st.setString(5, c.getComplemento());
            st.setString(6, c.getCep());
            st.setInt(7, c.getId_cidade());
            st.setString(8, Criptografia.criptografar(c.getSenha()));
            st.setInt(9, c.getId());
            st.executeUpdate();
            st.getResultSet();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro" + ex);
            return false;
        } finally {
        }

    }

}
