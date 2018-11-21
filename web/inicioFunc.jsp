<%-- 
    Document   : inicioFunc
    Created on : 10/11/2018, 13:56:55
    Author     : sa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="erro.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<script>
    function confirmaExclusao(id) {
        if (confirm("Deseja mesmo apagar o registro com código " + id + "?")) {
            // Faz o processamento necessário para exclusão
            location.href = "AtendimentoServlet?action=remover&id=" + id + "";
        }
    }
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>cli list</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="style/css/style.css">
    </head>
    <body>
        <c:if test="${!empty param.msg}">
            <div class="alert alert-danger" role="alert">
                <h2 style= color:red;text-align:center>${param.msg}</h2>
            </div>
        </c:if>
        <c:if test="${!empty requestScope.msg}">
            <div class="alert alert-danger" role="alert">
                <h2 style= color:red;text-align:center>${requestScope.msg}</h2>
            </div>
        </c:if>
        <c:if test= "${loginBean.tipo == 'F'}"> 
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="#">Atendimentos</a>
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
                            <a class="nav-link" href="FuncionarioServlet?action=list">Voltar</a>
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

        <c:if test= "${loginBean.tipo == 'G'}">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="#">Atendimentos</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item ">
                            <a class="nav-link" href="GerenteServlet?action=listFuncionarios">Gerenciar Funcionários</a>
                        </li>                   
                        <li class="nav-item ">
                            <a class="nav-link" href="GerenteServlet?action=todosAtendimentosNaoResolvidos">Atendimentos em aberto</a>
                        </li>

                        <li class="nav-item ">
                            <a class="nav-link" href="GerenteServlet?action=todosAtendimentos">Todos os atendimentos</a>
                        </li>
                        <li class="nav-item ">
                            <a class="nav-link" href="GerenteServlet?action=list">Tela Inicial</a>
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

        <c:if test= "${loginBean.tipo == 'C'}">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="#">Atendimentos</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item ">
                            <a class="nav-link" href="AtendimentoServlet?action=formNew">Efetuar atendimento</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="ClientesServlet?action=formUpdate&id=${loginBean.id}">Alterar dados cadastrais de ${loginBean.nome}</a>
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
        <section id="atendimentos" class="container-fluid">
            <c:choose>
                <c:when test = "${not empty loginBean.nome}">
                    <table id="tabela-atendimento" class="table">
                        <thead>
                            <tr>
                                <th scope="col">data/hora</th>
                                <th scope="col">cliente</th>
                                <th scope="col">situacao</th>
                                <th scope="col">tipo atendimento</th>
                                <th scope="col">descricao</th>
                                <th scope="col">solucao</th>                            
                                <th scope="col">produtos</th>  
                                    <c:if test= "${loginBean.tipo != 'G'}">
                                    <th scope="col">x</th>
                                    <th scope="col">x</th>
                                    </c:if>
                            </tr>
                        </thead>
                        <tbody>                        
                            <c:forEach items="${atendimentos}" var="x" varStatus="theCount">
                                <tr>
                                    <c:if test= "${temp=='n'}">
                                        <td><fmt:formatDate value="${x.dataHora}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                    </c:if>
                                    <c:if test= "${tempo[theCount.index]=='v'}">
                                        <td style="color: red"><fmt:formatDate value="${x.dataHora}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                    </c:if>
                                    <c:if test= "${tempo[theCount.index]=='a'}">
                                        <td style="color: #999900"><fmt:formatDate value="${x.dataHora}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                    </c:if>
                                    <!--<td><fmt:formatDate value="${x.dataHora}" pattern="dd/MM/yyyy HH:mm:ss"/></td>-->
                                    <td>${clientes[theCount.index]}</td>
                                    <td>${x.situacao}</td>
                                    <td>${tipoA[theCount.index]}</td>
                                    <td>${x.descricao != '' ? x.descricao:'sem descrição'}</td>
                                    <td>${x.solucao == '' ? 'ainda em aberto':x.solucao }</td>
                                    <td>${produtos[theCount.index]}</td>
                                    <c:if test= "${loginBean.tipo != 'G'}">
                                        <td><a href="AtendimentoServlet?action=show&id=${x.id_atendimento}"><button class="btn btn-primary">VER TUDO</button></a></td>
                                    </c:if>
                                    <c:if test= "${x.situacao == 'aberto'&&loginBean.tipo == 'F'}">
                                        <!--SEABERTO-->  <td><a href="AtendimentoServlet?action=formResolver&id=${x.id_atendimento}"><button class="btn btn-danger">RESOLVER</button></a></td>
                                    </c:if>
                                    <c:if test= "${x.situacao == 'aberto'&&loginBean.tipo == 'C'}">
                                        <!--SEABERTO-->  <td><a href="javascript: confirmaExclusao(${x.id_atendimento});"><button class="btn btn-danger">remover</button></a></td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <jsp:forward page="index.jsp">
                        <jsp:param name="msg" value="Usuário deve se autenticar para acessar o sistema!"/> 
                    </jsp:forward>
                </c:otherwise>
            </c:choose>
        </section>
    </body>
</html>
