/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.servlets;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import com.ufpr.tads.web2.beans.Categoria;
import com.ufpr.tads.web2.beans.Produto;
import com.ufpr.tads.web2.beans.UsuarioBean;
import com.ufpr.tads.web2.facade.AtendimentoFacade;
import com.ufpr.tads.web2.facade.CategoriaFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sa
 */
@WebServlet(name = "ProdutoServlet", urlPatterns = {"/ProdutoServlet"})
public class ProdutoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            UsuarioBean logB = (UsuarioBean) session.getAttribute("loginBean");
            String action = request.getParameter("action");
            if (logB.getNome() == null || logB.getNome().isEmpty()) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                request.setAttribute("msg", "Usuário deve se autenticar para acessar o sistema");
                rd.forward(request, response);
            } else if ((isNullOrEmpty(action)) || action.equals("list")) {
                List<Produto> produtos = AtendimentoFacade.buscarProdutos();
                List<String> categorias = new ArrayList<>();
                for (Produto p : produtos) {
                    String cats = CategoriaFacade.buscarCategoria(p.getId_categoria()).getNome_categoria();
                    categorias.add(cats);
                }
                request.setAttribute("categorias", categorias);
                request.setAttribute("produtos", produtos);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/inicioProd.jsp");
                rd.forward(request, response);

            } else if (action.equals("remover")) {
                int id = Integer.parseInt(request.getParameter("id"));
                if (!AtendimentoFacade.removeProd(id)) {
                    request.setAttribute("msg", "O produto não pode ser excluido pois está vinculado em atendiemento(s)");
                } else {
                    request.setAttribute("msg", "produto removido com sucesso");
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/ProdutoServlet?action=list");
                rd.forward(request, response);

            } else if (action.equals("show")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Produto p = CategoriaFacade.buscarProduto(id);
                request.setAttribute("x", p);
                request.setAttribute("cat", CategoriaFacade.buscarCategoria(p.getId_categoria()).getNome_categoria());
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/visualizarProd.jsp");
                rd.forward(request, response);

            } else if (action.equals("formUpdate")) {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("form", "alterar");
                Produto p = AtendimentoFacade.buscarProduto(id);
                request.setAttribute("p", p);
                List<Categoria> categorias = CategoriaFacade.buscarCategorias();
                request.setAttribute("categorias", categorias);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/produtoForm.jsp");
                rd.forward(request, response);

            } else if (action.equals("update")) {
                Produto p = new Produto();
                String idS = request.getParameter("id_produto");
                int pId = Integer.parseInt(idS.trim());
                p.setId_produto(pId);
                p.setNome_produto(request.getParameter("nome_produto"));

                p.setDescricao_produto(request.getParameter("descricao_produto"));
                String peso = request.getParameter("peso_produto");
                p.setPeso_produto(Double.parseDouble(peso));
                int ca = Integer.parseInt(request.getParameter("categoria"));
                System.out.println(ca);
                p.setId_categoria(ca);
                
                if (!AtendimentoFacade.updateProd(p)) {
                    request.setAttribute("msg", "Houve um problema ao atualizar");
                } else {
                    request.setAttribute("msg", "Atualizado com sucesso");
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/ProdutoServlet?action=list");
                rd.forward(request, response);

            } else if (action.equals("formNew")) {
                List<Categoria> categorias = CategoriaFacade.buscarCategorias();
                request.setAttribute("categorias", categorias);
                request.setAttribute("form", null);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/produtoForm.jsp");
                rd.forward(request, response);

            } else if (action.equals("new")) {
                Produto p = new Produto();
                p.setNome_produto(request.getParameter("nome_produto"));
                p.setDescricao_produto(request.getParameter("descricao_produto"));
                String peso = request.getParameter("peso_produto");
                p.setPeso_produto(Double.parseDouble(peso));
                p.setId_categoria(Integer.parseInt(request.getParameter("categoria")));                
                if (!AtendimentoFacade.inserirProd(p)) {
                    request.setAttribute("msg", "Houve um problema ao salvar");
                } else {
                    request.setAttribute("msg", "Salvo com sucesso");
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/ProdutoServlet?action=list");
                rd.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
