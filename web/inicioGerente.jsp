<%-- 
    Document   : inicioGerente
    Created on : 10/11/2018, 23:28:50
    Author     : sa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page errorPage="erro.jsp" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
        <script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>    
        <link rel="stylesheet" type="text/css" href="style/css/style.css">
    </head>
    <script>
        $(function () {
            $("#calendario").datepicker({
                dateFormat: 'dd/mm/yy',
                dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado', 'Domingo'],
                dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S', 'D'],
                dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'],
                monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
                monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
                changeMonth: true,
                changeYear: true
            });
        });
    </script>
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
    <body>
        <c:choose>
            <c:when test = "${not empty loginBean.nome}">
                <c:set var="emAberto" value="${totAtendimentosAbertos}" />
                <c:set var="total" value="${totAtendimentos}" />
                <h4>A quantidade de atendimentos efetuados até o momento: ${totAtendimentos}</h4>
                <h4>A quantidade de atendimentos em aberto: ${totAtendimentosAbertos} Porcentagem em relação ao total: ${ emAberto/total *100 }%</h4> 
                <h4> 
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">TIPOS ATENDIMENTOS</th>
                                <th scope="col">abertos/total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${atendimentosT}" var="x" varStatus="theCount">
                                <tr>
                                    <td>${x.nome_tipoAt}</td>                                
                                    <td>${totA[theCount.index]}/${tot[theCount.index]}</td>                                
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <td><a class="btn btn-primary"  href="GeradorRelatorio?action=todosFunc" role="button">Relatorio de funcionario</a></td>   
                    <form class="form-group" action="GeradorRelatorio?action=atAbertos" method="POST">                      
                        <div class="row">
                            <label for="data">DATA INICIAL</label>
                            <input type="text" id="calendario" class="form-control" name="dataI" placeholder="Ex.: 22/12/1997">
                        </div>
                        <div class="row">
                            <label for="data">DATA FINAL</label>
                            <input type="text" id="calendario" class="form-control" name="dataF" placeholder="Ex.: 22/12/1997">
                        </div>
                        <input type="submit" class="btn btn-primary" value="GERAR Relatorio de Atendimentos em Aberto Por Data">
                    </form>
                    <td><a class="btn btn-primary"  href="GeradorRelatorio?action=prodReclamados" role="button">Relatorio de Produtos Mais Reclamados</a></td>   
                    <!--<td><a class="btn btn-primary"  href="GeradorRelatorio?action=" role="button">Relatorio de Atendimentos em Aberto Por Data</a></td>-->   
                    <!--<td><a class="btn btn-primary"  href="GeradorRelatorio?action=reclamacoes" role="button">Relatorio de Reclamações</a></td>-->  
                    <form class="form-group" action="GeradorRelatorio?action=reclamacoes" method="POST">
                        <div class="btn-group" data-toggle="buttons">                      
                            <label class="btn btn-default active">
                                <input type="radio" id="tipo" name="situacao" value="abc" checked/>Todos
                            </label>
                            <label class="btn btn-default">
                                <input type="radio" id="tipo1" name="situacao" value="finalizado"/>Abertos
                            </label>                                               
                            <label class="btn btn-default">
                                <input type="radio" id="tipo2" name="situacao" value="aberto" />Finalizados
                            </label>                                               
                        </div>          
                        <input type="submit" class="btn btn-primary" value="GERAR Relatorio de Reclamações">
                    </form>
                    <!--                    <td><a class="btn btn-primary"  href="GerenteServlet?action=todosAtendimentosNaoResolvidos" role="button">Listagem de todos os atendimentos em Aberto</a></td>   -->
                    <!--                    <td><a class="btn btn-primary"  href="GerenteServlet?action=todosAtendimentos" role="button">Listagem de todos os atendimentos</a></td>   -->
                    <a href="LogoutServlet"><button class="btn btn-danger">SAIR</button></a>
                </c:when>
                <c:otherwise>
                    <jsp:forward page="index.jsp">
                        <jsp:param name="msg" value="Usuário deve se autenticar para acessar o sistema!"/> 
                    </jsp:forward>
                </c:otherwise>
            </c:choose>
            <br/>
            <br/>        
    </body>
</html>
