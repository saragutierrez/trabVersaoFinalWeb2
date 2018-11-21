/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.relatorios;

import com.mysql.jdbc.StringUtils;
import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import com.ufpr.tads.web2.beans.UsuarioBean;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.console;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author sa
 */
@WebServlet(name = "GeradorRelatorio", urlPatterns = {"/GeradorRelatorio"})
public class GeradorRelatorio extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException, JRException, ParseException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        UsuarioBean logB = (UsuarioBean) session.getAttribute("loginBean");
        String action = request.getParameter("action");
        if (logB.getNome() == null || logB.getNome().isEmpty()) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            request.setAttribute("msg", "Usuário deve se autenticar para acessar o sistema");
            rd.forward(request, response);
        } else {
            if (isNullOrEmpty(action)) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/portal.jsp");
                rd.forward(request, response);
            } else if (action.equals("todosFunc")) {
                gerarRelatorioFuncionarios(request, response);

            } else if (action.equals("prodReclamados")) {
                gerarRelatorioProdReclamados(request, response);

            } else if (action.equals("reclamacoes")) {
                String situacao = request.getParameter("situacao");
                System.out.println(situacao);
                gerarRelatorioReclamacoes(request, response, situacao);

            } else if (action.equals("atAbertos")) {
                String strI = request.getParameter("dataI") + " 00:00:00";
                String strF = request.getParameter("dataF") + " 23:59:59";
                System.out.println(strF);
                gerarRelatorioAtAbertos(request, response, strI, strF);
            }
        } // Fechamento do processRequest
// Outros métodos escondidos
    } // Fechamento da classe

    public void gerarRelatorioFuncionarios(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, IOException, ServletException {
        Connection con = null;
        try {
// Conexão com o banco
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/trabalho",
                    "root", "root");
            // Caminho contextualizado do relatório compilado
            String jasper = request.getContextPath()
                    + "/funcs.jasper";
// Host onde o servlet esta executando
            String host = "http://" + request.getServerName()
                    + ":" + request.getServerPort();
// URL para acesso ao relatório
            URL jasperURL = new URL(host + jasper);
// Parâmetros do relatório
            HashMap params = new HashMap();
// Geração do relatório
            byte[] bytes = JasperRunManager.runReportToPdf(
                    jasperURL.openStream(), params, con);
            if (bytes != null) {
// A página será mostrada em PDF
                response.setContentType("application/pdf");
// Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
        } // Fechamento do try
        catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Driver BD não encontrado : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro de conexão ou query: "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (JRException e) {
            request.setAttribute("mensagem", "Erro no Jasper : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }

            }
        }
    }

    public void gerarRelatorioReclamacoes(HttpServletRequest request, HttpServletResponse response, String situacao) throws MalformedURLException, IOException, ServletException {
        Connection con = null;
        try {
// Conexão com o banco
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/trabalho",
                    "root", "root");
            // Caminho contextualizado do relatório compilado
            String jasper = request.getContextPath()
                    + "/reclamacoes.jasper";
// Host onde o servlet esta executando
            String host = "http://" + request.getServerName()
                    + ":" + request.getServerPort();
// URL para acesso ao relatório
            URL jasperURL = new URL(host + jasper);
// Parâmetros do relatório
            HashMap params = new HashMap();
            params.put("situacao", situacao);
// Geração do relatório
            byte[] bytes = JasperRunManager.runReportToPdf(
                    jasperURL.openStream(), params, con);
            if (bytes != null) {
// A página será mostrada em PDF
                response.setContentType("application/pdf");
// Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
        } // Fechamento do try
        catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Driver BD não encontrado : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro de conexão ou query: "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (JRException e) {
            request.setAttribute("mensagem", "Erro no Jasper : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }

            }
        }
    }

    public void gerarRelatorioAtAbertos(HttpServletRequest request, HttpServletResponse response, String dI, String dF) throws MalformedURLException, IOException, ServletException, ParseException {
        Timestamp dataI = null;
        Timestamp dataF = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date parseDateI = format.parse(dI);
        Date parseDateF = format.parse(dF);
        dataI = new java.sql.Timestamp(parseDateI.getTime());
        dataF = new java.sql.Timestamp(parseDateF.getTime());
        Connection con = null;
        try {
// Conexão com o banco
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/trabalho",
                    "root", "root");
            // Caminho contextualizado do relatório compilado
            String jasper = request.getContextPath()
                    + "/atAbertos.jasper";
// Host onde o servlet esta executando
            String host = "http://" + request.getServerName()
                    + ":" + request.getServerPort();
// URL para acesso ao relatório
            URL jasperURL = new URL(host + jasper);
// Parâmetros do relatório
            HashMap params = new HashMap();
            params.put("dataI", dataI);
            params.put("dataF", dataF);
// Geração do relatório
            byte[] bytes = JasperRunManager.runReportToPdf(
                    jasperURL.openStream(), params, con);
            if (bytes != null) {
// A página será mostrada em PDF
                response.setContentType("application/pdf");
// Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
        } // Fechamento do try
        catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Driver BD não encontrado : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro de conexão ou query: "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (JRException e) {
            request.setAttribute("mensagem", "Erro no Jasper : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }

            }
        }
    }

    public void gerarRelatorioProdReclamados(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, IOException, ServletException {
        Connection con = null;
        try {
// Conexão com o banco
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/trabalho",
                    "root", "root");
            // Caminho contextualizado do relatório compilado
            String jasper = request.getContextPath()
                    + "/prodReclamados.jasper";
// Host onde o servlet esta executando
            String host = "http://" + request.getServerName()
                    + ":" + request.getServerPort();
// URL para acesso ao relatório
            URL jasperURL = new URL(host + jasper);
// Parâmetros do relatório
            HashMap params = new HashMap();
// Geração do relatório
            byte[] bytes = JasperRunManager.runReportToPdf(
                    jasperURL.openStream(), params, con);
            if (bytes != null) {
// A página será mostrada em PDF
                response.setContentType("application/pdf");
// Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
        } // Fechamento do try
        catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Driver BD não encontrado : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro de conexão ou query: "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (JRException e) {
            request.setAttribute("mensagem", "Erro no Jasper : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }

            }
        }
    }

    public void gerarRelatorioAtendimentos(String dt_ini, String dt_fim, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, IOException, ServletException {
        Connection con = null;
        try {
// Conexão com o banco
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/teste",
                    "root", "root");
            // Caminho contextualizado do relatório compilado
            String jasper = request.getContextPath()
                    + "/relatorios/todosAtendimentos.jasper";
// Host onde o servlet esta executando
            String host = "http://" + request.getServerName()
                    + ":" + request.getServerPort();
// URL para acesso ao relatório
            URL jasperURL = new URL(host + jasper);
// Parâmetros do relatório
            HashMap params = new HashMap();
// Geração do relatório
            byte[] bytes = JasperRunManager.runReportToPdf(
                    jasperURL.openStream(), params, con);
            if (bytes != null) {
// A página será mostrada em PDF
                response.setContentType("application/pdf");
// Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
        } // Fechamento do try
        catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Driver BD não encontrado : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro de conexão ou query: "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (JRException e) {
            request.setAttribute("mensagem", "Erro no Jasper : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }

            }
        }
    }

    public void gerarRelatorioAtendimentosResolvidos(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, IOException, ServletException {
        Connection con = null;
        try {
// Conexão com o banco
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/teste",
                    "root", "root");
            // Caminho contextualizado do relatório compilado
            String jasper = request.getContextPath()
                    + "/atResolvidos.jasper";
// Host onde o servlet esta executando
            String host = "http://" + request.getServerName()
                    + ":" + request.getServerPort();
// URL para acesso ao relatório
            URL jasperURL = new URL(host + jasper);
// Parâmetros do relatório
            HashMap params = new HashMap();
// Geração do relatório
            byte[] bytes = JasperRunManager.runReportToPdf(
                    jasperURL.openStream(), params, con);
            if (bytes != null) {
// A página será mostrada em PDF
                response.setContentType("application/pdf");
// Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
        } // Fechamento do try
        catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Driver BD não encontrado : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro de conexão ou query: "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (JRException e) {
            request.setAttribute("mensagem", "Erro no Jasper : "
                    + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }

            }
        }
    }
//    public void gerarRelatorioAtendimentos(HttpServletRequest request, HttpServletResponse response, String dt_ini, String dt_fim) throws MalformedURLException, IOException, ServletException, ParseException {
//        Connection con = null;
////        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
////        Date data1 = format.parse(dt_ini);
////        Timestamp time1 = new Timestamp(data1.getTime());
////
////        Date data2 = format.parse(dt_fim);
////        Timestamp time2 = new Timestamp(data2.getTime());
//
//        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date time1 = (Date) formatter.parse(dt_ini);
//        java.sql.Timestamp time1Time = new Timestamp(time1.getTime());
//        
//        Date time2 = (Date) formatter.parse(dt_fim);
//        java.sql.Timestamp time2Time = new Timestamp(time2.getTime());
//        try {
//// Conexão com o banco
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/teste",
//                    "root", "root");
//            // Caminho contextualizado do relatório compilado
//            String jasper = request.getContextPath()
//                    + "/relatorios/todosAtendimentos.jasper";
//// Host onde o servlet esta executando
//            String host = "http://" + request.getServerName()
//                    + ":" + request.getServerPort();
//// URL para acesso ao relatório
//            URL jasperURL = new URL(host + jasper);
//// Parâmetros do relatório
//            HashMap params = new HashMap();
//            System.out.println(time1Time);
//            System.out.println(time2Time);
////        System.out.println(dt_fim.getTime());
////            Map<String, Object> params = new HashMap<>();
//            params.put("dt_ini", "'" + time1Time + "'");
//            params.put("dt_fim", "'" + time2Time + "'");
//
//// Geração do relatório
//            byte[] bytes = JasperRunManager.runReportToPdf(
//                    jasperURL.openStream(), params, con);
//            if (bytes != null) {
//// A página será mostrada em PDF
//                response.setContentType("application/pdf");
//// Envia o PDF para o Cliente
//                OutputStream ops = response.getOutputStream();
//                ops.write(bytes);
//            }
//        } // Fechamento do try
//        catch (ClassNotFoundException e) {
//            request.setAttribute("mensagem", "Driver BD não encontrado : "
//                    + e.getMessage());
//            request.getRequestDispatcher("erro.jsp").forward(request, response);
//        } catch (SQLException e) {
//            request.setAttribute("mensagem", "Erro de conexão ou query: "
//                    + e.getMessage());
//            request.getRequestDispatcher("erro.jsp").forward(request, response);
//        } catch (JRException e) {
//            request.setAttribute("mensagem", "Erro no Jasper : "
//                    + e.getMessage());
//            request.getRequestDispatcher("erro.jsp").forward(request, response);
//        } finally {
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                }
//
//            }
//        }
//    }

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
        } catch (ClassNotFoundException | SQLException | JRException | ParseException ex) {
            Logger.getLogger(GeradorRelatorio.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException | SQLException | JRException | ParseException ex) {
            Logger.getLogger(GeradorRelatorio.class.getName()).log(Level.SEVERE, null, ex);
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
