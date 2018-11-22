<%-- 
    Document   : atendimentoDetalhes
    Created on : 02/11/2018, 22:25:18
    Author     : sa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page errorPage="erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="style/css/style.css">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="ClientesServlet?action=list">Atendimentos</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item ">
                        <c:if test= "${loginBean.tipo == 'C'}">
                            <a class="nav-link" href="ClientesServlet?action=list">Voltar</a>
                        </c:if>
                        <c:if test= "${loginBean.tipo == 'F'}">
                            <a class="nav-link" href="FuncionarioServlet?action=list">Voltar</a>
                        </c:if>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="LogoutServlet">Sair(${loginBean.nome})</a>
                    </li>
                </ul>  
            </div>
        </nav>       
        <c:choose>
            <c:when test = "${not empty loginBean.nome}"> 
                <c:choose>
                    <c:when test =  "${loginBean.tipo == 'F'}">
                        <c:if test= "${resolver == 'resolver'}">
                            <h2 style="text-align: center; color: grey">RESOLVER</h2>
                        </c:if>
                        <c:if test= "${resolver == null}">
                            <h2 style="text-align: center; color: grey">VISUALIZAR DETALHES</h2>
                        </c:if>
                    </c:when>
                </c:choose>
                <c:if test= "${loginBean.tipo == 'C'}">
                    <h2 style="text-align: center; color: grey">VISUALIZAR DETALHES</h2>
                </c:if>
                <section id="tabela-atendimento">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">data/hora</th>
                                <th scope="col">cliente</th>
                                <th scope="col">situacao</th>
                                <th scope="col">descricao</th>
                                <th scope="col">solucao</th>                            
                                <th scope="col">produtos</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><fmt:formatDate value="${x.dataHora}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                <td>${cliente}</td>
                                <td>${x.situacao}</td>
                                <td>${x.descricao != "" ? x.descricao:'sem descrição'}</td>
                                <td>${x.solucao != "" ? x.solucao:'ainda em aberto'}</td>
                                <td>${produto}</td>                            
                            </tr>              
                        </tbody>
                    </table>
                </section>
                <c:choose>
                    <c:when test =  "${loginBean.tipo == 'F'}">
                        <c:if test= "${resolver == 'resolver'}">
                            <form class="form-group" action="AtendimentoServlet?action=resolver&id=${x.id_atendimento}" method="POST"> 
                                <div class="row">
                                    <label for="solucao"> Solucao</label>
                                    <input type="text" class="form-control" name="solucao" value="" required>
                                </div> 
                                <input type="submit" class="btn btn-primary" value="RESOLVER">
                            </form>
                        </c:if>
                    </c:when>
                </c:choose>
            </c:when>
            <c:otherwise>
                <jsp:forward page="index.jsp">
                    <jsp:param name="msg" value="Usuário deve se autenticar para acessar o sistema!"/> 
                </jsp:forward>
            </c:otherwise>
        </c:choose>
    </body>
</html>
