/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.servlets;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import com.ufpr.tads.web2.beans.Atendimento;
import com.ufpr.tads.web2.beans.Produto;
import com.ufpr.tads.web2.beans.TipoAtendimento;
import com.ufpr.tads.web2.beans.UsuarioBean;
import com.ufpr.tads.web2.facade.AtendimentoFacade;
import com.ufpr.tads.web2.facade.UsuarioFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "AtendimentoServlet", urlPatterns = {"/AtendimentoServlet"})
public class AtendimentoServlet extends HttpServlet {

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
            } else {
//             if ((isNullOrEmpty(action)) || action.equals("list")) {
//                    List<Atendimento> atendimentos = AtendimentoFacade.buscarAtendimentos(idU);
//                    List<String> produtos = new ArrayList<>();
//                    List<String> clientes = new ArrayList<>();
//                    for (Atendimento a : atendimentos) {
//                        String p = AtendimentoFacade.buscarProduto(a.getId_produto()).getNome_produto();
//                        String c = ClientesFacade.show(a.getId_cliente()).getNome();
//                        produtos.add(p);
//                        clientes.add(c);
//                    }
//                    request.setAttribute("atendimentos", atendimentos);
//                    request.setAttribute("produtos", produtos);
//                    request.setAttribute("clientes", clientes);
//                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/atendimentoListar.jsp");
//                    rd.forward(request, response);
//                } else 
                if (action.equals("show")) {
                    int idA = Integer.parseInt(request.getParameter("id"));
                    Atendimento at = AtendimentoFacade.show(idA);
                    String produto = AtendimentoFacade.buscarProduto(at.getId_produto()).getNome_produto();
                    String cliente = UsuarioFacade.show(at.getId_cliente()).getNome();
                    String tipoA = AtendimentoFacade.buscarTipoAtendimento(at.getId_tipo_atendimento()).getNome_tipoAt();
                    request.setAttribute("x", at);
                    request.setAttribute("produto", produto);
                    request.setAttribute("cliente", cliente);
                    request.setAttribute("tipoA", tipoA);
                    request.setAttribute("resolver", null);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/atendimentoDetalhes.jsp");
                    rd.forward(request, response);
                } else if (action.equals("remover")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    if (!AtendimentoFacade.remove(id)) {
                        request.setAttribute("msg", "Erro ao excluir");
                    } else {
                        request.setAttribute("msg", "Removido com sucesso");
                    }
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ClientesServlet?action=list");
                    rd.forward(request, response);

                } else if (action.equals("formResolver")) {
                    int idA = Integer.parseInt(request.getParameter("id"));
                    Atendimento at = AtendimentoFacade.show(idA);
                    String produto = AtendimentoFacade.buscarProduto(at.getId_produto()).getNome_produto();
                    String cliente = UsuarioFacade.show(at.getId_cliente()).getNome();
                    String tipoA = AtendimentoFacade.buscarTipoAtendimento(at.getId_tipo_atendimento()).getNome_tipoAt();
                    request.setAttribute("x", at);
                    request.setAttribute("produto", produto);
                    request.setAttribute("cliente", cliente);
                    request.setAttribute("tipoA", tipoA);
                    request.setAttribute("resolver", "resolver");
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/atendimentoDetalhes.jsp");
                    rd.forward(request, response);

                } else if (action.equals("resolver")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Atendimento a = AtendimentoFacade.show(id);
                    a.setSolucao(request.getParameter("solucao"));
                    if (!AtendimentoFacade.resolver(a)) {
                        request.setAttribute("msg", "Erro ao resolver");
                    } else {
                        request.setAttribute("msg", "Resolvido com sucesso");
                    }                    
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioServlet?action=list");
                    rd.forward(request, response);

                } else if (action.equals("formNew")) {
                    try {
                        List<Produto> produtos = AtendimentoFacade.buscarProdutos();
                        List<TipoAtendimento> ta = AtendimentoFacade.buscarTiposAtendimento();
                        UsuarioBean u = UsuarioFacade.show(logB.getId());
                        Timestamp dataDeHoje = new Timestamp(System.currentTimeMillis());
                        String s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataDeHoje);
                        request.setAttribute("produtos", produtos);
                        request.setAttribute("dataDeHoje", s);
                        request.setAttribute("ta", ta);
                        request.setAttribute("cliente", u);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/atendimento.jsp");
                        rd.forward(request, response);
                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                } else if (action.equals("new")) {
                    Atendimento a = new Atendimento();
                    a.setDescricao(request.getParameter("descricao"));
                    int prod = Integer.parseInt(request.getParameter("produto"));
                    int ta = Integer.parseInt(request.getParameter("ta"));
                    System.out.println(prod);
                    a.setId_produto(prod);
                    a.setId_tipo_atendimento(ta);
                    a.setId_cliente(logB.getId());
                    String str = request.getParameter("datahora");
                    Timestamp tm = null;
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date parseDate = format.parse(str);
                        tm = new java.sql.Timestamp(parseDate.getTime());
                    } catch (ParseException e) {
                    }
                    a.setDataHora(tm);
                    System.out.println(tm);
                    AtendimentoFacade.inserir(a);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ClientesServlet?action=list");
                    rd.forward(request, response);
                }

//                }
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
            Logger.getLogger(AtendimentoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AtendimentoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
