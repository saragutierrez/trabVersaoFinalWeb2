/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.servlets;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import com.ufpr.tads.web2.beans.Atendimento;
import com.ufpr.tads.web2.beans.Categoria;
import com.ufpr.tads.web2.beans.UsuarioBean;
import com.ufpr.tads.web2.facade.AtendimentoFacade;
import com.ufpr.tads.web2.facade.CategoriaFacade;
import com.ufpr.tads.web2.facade.UsuarioFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
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
@WebServlet(name = "FuncionarioServlet", urlPatterns = {"/FuncionarioServlet"})
public class FuncionarioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
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
                List<Atendimento> atendimentos = AtendimentoFacade.buscarAtendimentosNaoResolvidos();
                List<String> produtos = new ArrayList<>();
                List<String> clientes = new ArrayList<>();
                List<String> tempo = new ArrayList<>();
                List<String> tipoA = new ArrayList<>();
                Timestamp dataDeHoje = new Timestamp(System.currentTimeMillis());
                for (Atendimento a : atendimentos) {
                    String ms = null;
                    long i = dataDeHoje.getTime() - AtendimentoFacade.show(a.getId_atendimento()).getDataHora().getTime();
                    if (i >= 604800000) {
                        ms = "v";
                    } else {
                        ms = "a";
                    }
                    String p = AtendimentoFacade.buscarProduto(a.getId_produto()).getNome_produto();
                    String c = UsuarioFacade.show(a.getId_cliente()).getNome();
                    produtos.add(p);
                    clientes.add(c);
                    tempo.add(ms);
                    String t = AtendimentoFacade.buscarTipoAtendimento(a.getId_tipo_atendimento()).getNome_tipoAt();
                    tipoA.add(t);
                }
                request.setAttribute("atendimentos", atendimentos);//NaoResolvidos
                request.setAttribute("produtos", produtos);
                request.setAttribute("clientes", clientes);
                request.setAttribute("tempo", tempo);
                request.setAttribute("tipoA", tipoA);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/inicioFunc.jsp");
                rd.forward(request, response);

            } else if (action.equals("listCat")) {
                request.setAttribute("categorias", CategoriaFacade.buscarCategorias());
                request.setAttribute("form", null);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/catList.jsp");
                rd.forward(request, response);

            } else if (action.equals("show")) {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("c", CategoriaFacade.buscarCategoria(id));
                request.setAttribute("form", "visualizar");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/catForm.jsp");
                rd.forward(request, response);

            } else if (action.equals("catUpdate")) {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("form", "alterar");
                Categoria u = CategoriaFacade.buscarCategoria(id);
                request.setAttribute("c", u);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/catForm.jsp");
                rd.forward(request, response);

            } else if (action.equals("update")) {
                Categoria c = new Categoria();
                String idS = request.getParameter("id");
                int convId = Integer.parseInt(idS.trim());
                c.setId_categoria(convId);
                c.setNome_categoria(request.getParameter("nome"));
                
                if (!CategoriaFacade.updateCat(c)) {
                    request.setAttribute("msg", "Houve um erro na atualização da categoria");
                } else {
                    request.setAttribute("msg", "Categoria atualizada com sucesso");
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioServlet?action=listCat");
                rd.forward(request, response);
            } else if (action.equals("remove")) {
                int id = Integer.parseInt(request.getParameter("id"));
                if (!CategoriaFacade.remove(id)) {
                    request.setAttribute("msg", "A categoria possui produtos cadastrados");
                } else {
                    request.setAttribute("msg", "Categoria removida com sucesso");
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioServlet?action=listCat");
                rd.forward(request, response);
            } else if (action.equals("formNew")) {
                request.setAttribute("form", null);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/catForm.jsp");
                rd.forward(request, response);
            } else if (action.equals("new")) {
                Categoria c = new Categoria();
                c.setNome_categoria(request.getParameter("nome"));
                
                if (!CategoriaFacade.inserir(c)) {
                    request.setAttribute("msg", "Houve um problema ao salvar");
                } else {
                    request.setAttribute("msg", "Salvo com sucesso");
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioServlet?action=listCat");
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
            Logger.getLogger(FuncionarioServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FuncionarioServlet.class.getName()).log(Level.SEVERE, null, ex);
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
