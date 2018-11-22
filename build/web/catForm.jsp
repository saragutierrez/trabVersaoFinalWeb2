<%-- 
    Document   : catForm
    Created on : 11/11/2018, 19:49:15
    Author     : sa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page errorPage="erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="style/css/style.css">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <c:if test = "${form == 'alterar'}">
        <c:set var="titulo" value="ALTERAR"/>
        <c:set var="action" value="FuncionarioServlet?action=update"/>
        <c:set var="button" value="ALTERAR"/>
    </c:if>
    <c:if test = "${form == 'visualizar'}">
        <c:set var="titulo" value="VISUALIZAR"/>
        <%--<c:set var="action" value="ClientesServlet?action=update"/>--%>
    </c:if>
    <c:if test = "${form == null}">
        <c:set var="titulo" value="CADASTRAR"/>
        <c:set var="action" value="FuncionarioServlet?action=new"/>
        <c:set var="button" value="SALVAR"/>
    </c:if>
    <c:if test= "${loginBean.tipo == 'F'}"> 
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">${titulo}</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item ">
                        <a class="nav-link" href="GerenteServlet?action=todosAtendimentos">Listagem de todos os atendimentos</a>
                    </li>
                    <li class="nav-item ">
                        <a class="nav-link" href="FuncionarioServlet?action=listCat">Gerenciar Categorias</a>
                    </li>
                    <li class="nav-item ">
                        <a class="nav-link" href="ProdutoServlet?action=list">Gerenciar Produtos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="FuncionarioServlet?action=list">Tela Inicial</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="LogoutServlet">Sair(${loginBean.nome})</a>
                    </li>
                </ul>  
            </div>
        </nav>
    </c:if>

    <body>     
        <section id="formulario_categoria" class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <c:choose>
                        <c:when test = "${not empty loginBean.nome}">                            
                            <form class="form-group" action="${action}" method="POST"> 
                                <div class="row">
                                    <!--<label for="id">ID</label>-->
                                    <input type="hidden" class="form-control" name="id" value="${form == "alterar" ? c.id_categoria : ""}">
                                </div>
                                <div class="row">
                                    <label for="name">NOME</label>
                                    <input required type="text" class="form-control" name="nome" value="${form == ('alterar'||'visualizar') ? c.nome_categoria : ""}" placeholder="Ex.: Cabelo"  <c:if test = "${form == 'visualizar'}">disabled="true"</c:if>>
                                    </div>                                                     
                                <c:if test = "${form != 'visualizar'}">
                                    <input type="submit" class="btn btn-primary" value="${button}">
                                    <a href="FuncionarioServlet?action=listCat" class="btn btn-danger"> CANCELAR </a>
                                </c:if>
                            </form>  
                            </section>
                        </c:when>
                        <c:otherwise>
                            <jsp:forward page="index.jsp">
                                <jsp:param name="msg" value="Usuário deve se autenticar para acessar o sistema!"/> 
                            </jsp:forward>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
</body>
</html>
