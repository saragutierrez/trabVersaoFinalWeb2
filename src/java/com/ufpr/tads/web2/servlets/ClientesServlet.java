/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.servlets;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import com.ufpr.tads.web2.beans.Atendimento;
import com.ufpr.tads.web2.beans.CidadeBean;
import com.ufpr.tads.web2.beans.EstadoBean;
import com.ufpr.tads.web2.beans.UsuarioBean;
import com.ufpr.tads.web2.dao.UsuarioDao;
import com.ufpr.tads.web2.exceptions.ErroInserindoClienteException;
import com.ufpr.tads.web2.facade.AtendimentoFacade;
import com.ufpr.tads.web2.facade.ClientesFacade;
import com.ufpr.tads.web2.facade.UsuarioFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "ClientesServlet", urlPatterns = {"/ClientesServlet"})
public class ClientesServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ParseException, ErroInserindoClienteException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            UsuarioBean logB = (UsuarioBean) session.getAttribute("loginBean");
            String action = request.getParameter("action");
            try {
                if (action == null) {
                    action = "";
                }

                if (action.equals("cadastro")) {//(logB.getNome() == null || logB.getNome().isEmpty()) && !
                    UsuarioBean log = new UsuarioBean();
                    try {
                        logB.getTipo();
                    } catch (NullPointerException e) {
                        if ((String) request.getAttribute("msg") != null) {
                            String msg = (String) request.getAttribute("msg");
                            request.setAttribute("msg", msg);
                            System.out.println("setou");
                        } else {
                            System.out.println("passou");
                        }

                        log.setTipo("C");
                        List<EstadoBean> estados = UsuarioFacade.buscarEstados();
                        request.setAttribute("estados", estados);
                        request.setAttribute("logB", log);
                        request.setAttribute("form", "newC");
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/usuarioForm.jsp");
                        rd.forward(request, response);
                    }

                    /*
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                    request.setAttribute("msg", "Usuário deve se autenticar para acessar o sistema");
                    rd.forward(request, response);
                     */
                } else {
                    if ((isNullOrEmpty(action)) || action.equals("list")) {
                        System.out.println(logB.getId());
                        List<Atendimento> atendimentos = AtendimentoFacade.listAtendimentosCli(logB.getId());
                        List<String> produtos = new ArrayList<>();
                        List<String> clientes = new ArrayList<>();
                        List<String> tipoA = new ArrayList<>();
                        for (Atendimento a : atendimentos) {
                            String p = AtendimentoFacade.buscarProduto(a.getId_produto()).getNome_produto();
                            String c = UsuarioFacade.show(a.getId_cliente()).getNome();
                            produtos.add(p);
                            clientes.add(c);
                            String t = AtendimentoFacade.buscarTipoAtendimento(a.getId_tipo_atendimento()).getNome_tipoAt();
                            tipoA.add(t);
                        }
                        request.setAttribute("atendimentos", atendimentos);//NaoResolvidos
                        request.setAttribute("produtos", produtos);
                        request.setAttribute("clientes", clientes);
                        request.setAttribute("tipoA", tipoA);
                        request.setAttribute("temp", 'n');
                        request.setAttribute("atendimentos", AtendimentoFacade.listAtendimentosCli(logB.getId()));
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/inicioFunc.jsp");
                        rd.forward(request, response);

                    } else if (action.equals("formUpdate")) {
                        int id = Integer.parseInt(request.getParameter("id"));
                        System.out.println(id);
                        request.setAttribute("form", "alterarC");
                        UsuarioBean u = UsuarioFacade.show(id);
                        request.setAttribute("c", u);
                        List<EstadoBean> estados = UsuarioFacade.buscarEstados();
                        List<CidadeBean> cidades = UsuarioFacade.buscarCidades(u.getEstado().getId());
                        request.setAttribute("estados", estados);
                        request.setAttribute("cidades", cidades);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/usuarioForm.jsp");
                        rd.forward(request, response);

                    } else if (action.equals("update")) {
                        UsuarioBean c = new UsuarioBean();
                        String idS = request.getParameter("id");
                        int convId = Integer.parseInt(idS.trim());
                        c.setId(convId);
                        //                    System.out.println(idS);
                        c.setNome(request.getParameter("nome"));
                        c.setTel(request.getParameter("tel"));
                        c.setRua(request.getParameter("rua"));
                        String numS = request.getParameter("nr_cliente");
                        c.setComplemento(request.getParameter("complemento"));
                        c.setCep(request.getParameter("cep"));
                        int numI = Integer.parseInt(numS);
                        c.setNr_casa(numI);
                        CidadeBean cidade = new CidadeBean();
                        EstadoBean estado = new EstadoBean();
                        int city = Integer.parseInt(request.getParameter("cidade"));
                        cidade.setEstado(estado);
                        c.setId_cidade(city);
                        c.setSenha(request.getParameter("senha"));
                        if (!UsuarioFacade.updateC(c)) {
                            request.setAttribute("msg", "Houve um problema ao atualizar");
                        } else {
                            request.setAttribute("msg", "Atualizado com sucesso");
                        }
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/ClientesServlet?action=list");
                        rd.forward(request, response);
                    } else if (action.equals("cadastro")) {
                        UsuarioBean log = new UsuarioBean();
                        log.setTipo("C");
                        List<EstadoBean> estados = UsuarioFacade.buscarEstados();
                        request.setAttribute("estados", estados);
                        request.setAttribute("logB", log);
                        request.setAttribute("form", "newC");
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/usuarioForm.jsp");
                        rd.forward(request, response);
                    } else if (action.equals("new")) {
                        boolean verifica;
                        UsuarioBean c = new UsuarioBean();
                        c.setNome(request.getParameter("nome"));
                        c.setCpf(request.getParameter("cpf"));
                        c.setEmail(request.getParameter("email"));
                        c.setTel(request.getParameter("tel"));
                        c.setRua(request.getParameter("rua"));
                        String numS = request.getParameter("nr_cliente");
                        c.setComplemento(request.getParameter("complemento"));
                        c.setCep(request.getParameter("cep"));
                        int numI = Integer.parseInt(numS);
                        c.setNr_casa(numI);
                        CidadeBean cidade = new CidadeBean();
                        EstadoBean estado = new EstadoBean();
                        int city = Integer.parseInt(request.getParameter("cidade"));
                        cidade.setEstado(estado);
                        c.setId_cidade(city);
                        c.setTipo("C");
                        c.setSenha(request.getParameter("senha"));
                        verifica = UsuarioFacade.inserir(c);
                        if (verifica == false) {
                            throw new ErroInserindoClienteException("Erro ao inserir Cliente!");
                        }
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/LogoutServlet?action=newC");
                        rd.forward(request, response);

                    }
                }
            } catch (ErroInserindoClienteException e) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/ClientesServlet?action=cadastro");
                request.setAttribute("msg", e.getLocalizedMessage());
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

        } catch (SQLException | ParseException ex) {
            Logger.getLogger(ClientesServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ErroInserindoClienteException ex) {
            Logger.getLogger(ClientesServlet.class.getName()).log(Level.SEVERE, null, ex);
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

        } catch (SQLException | ParseException ex) {
            Logger.getLogger(ClientesServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ErroInserindoClienteException ex) {
            Logger.getLogger(ClientesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
