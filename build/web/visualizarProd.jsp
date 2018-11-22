<%-- 
    Document   : visualizarProd
    Created on : 13/11/2018, 22:03:09
    Author     : sa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page errorPage="erro.jsp"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="style/css/style.css">
    </head>
    <c:if test= "${loginBean.tipo == 'F'}"> 
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">VISUALIZAR</a>
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
        <x:choose>
            <x:when test = "${not empty loginBean.nome}"> 
                <section id="tabela-produto">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">NOME DO PRODUTO</th>                            
                                <th scope="col">DESCRICAO</th>                            
                                <th scope="col">PESO</th>                            
                                <th scope="col">CATEGORIA</th>                 
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${x.nome_produto}</td>                                
                                <td>${x.descricao_produto}</td>                                
                                <td>${x.peso_produto}</td>                     
                                <td>${cat}</td>                     
                            </tr>              
                        </tbody>
                    </table>
                </section>      
                <a href="ProdutoServlet?action=list"><button class="btn btn-danger">VOLTAR</button></a>
            </x:when>
            <x:otherwise>
                <jsp:forward page="index.jsp">
                    <jsp:param name="msg" value="Usuário deve se autenticar para acessar o sistema!"/> 
                </jsp:forward>
            </x:otherwise>
        </x:choose>
    </body>
</html>
