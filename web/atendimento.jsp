<%-- 
    Document   : atendimento
    Created on : 02/11/2018, 22:23:33
    Author     : sa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">Atendimentos</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mr-auto">
                <!--                        <li class="nav-item ">
                                            <a class="nav-link" href="AtendimentoServlet?action=formNew">Efetuar atendimento</a>
                                        </li>-->
                <li class="nav-item">
                    <a class="nav-link" href="ClientesServlet?action=formUpdate&id=${loginBean.id}">Alterar dados cadastrais de ${loginBean.nome}</a>
                </li>
                <li class="nav-item ">
                    <a class="nav-link" href="ClientesServlet">Tela Inicial</a>
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
            <section id="efetuar_atendimento" class="container-fluid">
                <form class="form-group" action="AtendimentoServlet?action=new" method="POST">
                    <div class="row">
                        <label for="datahora">DATA HORA</label>
                        <input type="text" class="form-control" name="datahora" readonly="readonly" value="${dataDeHoje}">
                    </div>
                    <div class="row">
                        <label for="descricao">descricao</label>
                        <input type="text" class="form-control" name="descricao" value="">
                    </div>                               
                    <div id="efetuar_atendimento_combo" class="row">
                        <div class="col-4">
                            <label>PRODUTOS</label><br>
                            <select id="produto" name="produto" class="form-control" required>
                                <c:forEach items="${produtos}" var="x">                            
                                    <option value="${x.id_produto}">${x.nome_produto}</option>                            
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-4">
                            <label>TIPOS DE ATENDIMENTO</label><br>
                            <select id="ta" name="ta" class="form-control" required>
                                <c:forEach items="${ta}" var="x">                            
                                    <option value="${x.id_tipoAt}">${x.nome_tipoAt}</option>                            
                                </c:forEach>
                            </select>
                        </div>
                        <div id="botao" class="col-4">
                            <input type="submit" class="btn btn-primary" value="SALVAR">     
                        </div>    
                    </div>
                </form>
            </section>
        </c:when>
        <c:otherwise>
            <jsp:forward page="index.jsp">
                <jsp:param name="msg" value="Usuário deve se autenticar para acessar o sistema!"/> 
            </jsp:forward>
        </c:otherwise>
    </c:choose>
</body>
</html>
