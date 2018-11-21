/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.Produto;
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
public class ProdutoDao {

    public boolean adiciona(Produto p) {
        Connection con = ConnectionFactory.getConnection();
        String sql = ("insert into trabalho.produto(nome_produto,descricao_produto,peso_produto, id_categoria) values (?,?,?,?)");
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, p.getNome_produto());
            stmt.setString(2, p.getDescricao_produto());
            stmt.setDouble(3, p.getPeso_produto());
            stmt.setInt(4, p.getId_categoria());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro" + e);
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

    public List<Produto> buscaProdutos() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        try {
            st = con.prepareStatement("SELECT * FROM trabalho.produto");
            rs = st.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setId_produto(rs.getInt("id_produto"));
                p.setNome_produto(rs.getString("nome_produto"));
                p.setDescricao_produto(rs.getString("descricao_produto"));
                p.setPeso_produto(rs.getDouble("peso_produto"));
                p.setId_categoria(rs.getInt("id_categoria"));
                produtos.add(p);
            }
            return produtos;
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
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public Produto buscaProduto(int id) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st;
        Produto p = new Produto();
        st = con.prepareStatement("SELECT * FROM trabalho.produto where id_produto = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        try {
            if (rs.next()) {
                p.setId_produto(rs.getInt("id_produto"));
                p.setNome_produto(rs.getString("nome_produto"));
                p.setDescricao_produto(rs.getString("descricao_produto"));
                p.setPeso_produto(rs.getDouble("peso_produto"));
                p.setId_categoria(rs.getInt("id_categoria"));
            }
            return p;
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
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public boolean remove(int id) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st;
        Produto p = new Produto();
        try {
            st = con.prepareStatement("delete FROM trabalho.produto WHERE id_produto = ?");
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro" + e);
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

    public boolean updateProd(Produto p) {
        Connection con = ConnectionFactory.getConnection();
        String sql = ("update trabalho.produto SET nome_produto=?,descricao_produto=?,peso_produto=?,id_categoria=? WHERE id_produto = ?");
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st = con.prepareStatement(sql);
            st.setString(1, p.getNome_produto());
            st.setString(2, p.getDescricao_produto());
            st.setDouble(3, p.getPeso_produto());
            st.setInt(4, p.getId_categoria());
            st.setInt(5, p.getId_produto());
            st.executeUpdate();
            st.getResultSet();
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
}
