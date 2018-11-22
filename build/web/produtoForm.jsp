<%-- 
    Document   : clientesForm
    Created on : 25/10/2018, 21:57:04
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
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
        <script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
        <link rel="stylesheet" type="text/css" href="style/css/style.css">

        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.0/jquery.mask.js"></script>
    </head>
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
    <c:if test = "${form == 'alterar'}">
        <c:set var="titulo" value="ALTERAR"/>
        <c:set var="action" value="ProdutoServlet?action=update"/>
        <c:set var="button" value="ALTERAR"/>
    </c:if>
    <c:if test = "${form == null}">
        <c:set var="titulo" value="CADASTRAR"/>
        <c:set var="action" value="ProdutoServlet?action=new"/>
        <c:set var="button" value="SALVAR"/>
    </c:if>
    <body>
        <section id="formulario_produto" class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <c:choose>
                        <c:when test = "${not empty loginBean.nome}">                            
                            <form class="form-group" action="${action}" method="POST"> 
                                <div class="row">
                                    <!--<label for="id">ID</label>-->
                                    <input type="hidden" class="form-control" name="id_produto" value="${form == "alterar" ? p.id_produto : ""}">
                                </div>
                                <div class="row">
                                    <label for="name">NOME</label>
                                    <input required type="text" class="form-control" name="nome_produto" value="${form == "alterar" ? p.nome_produto : ""}" placeholder="Ex.: Clareador">
                                    </div>                                  

                                    <div class="row">
                                        <label for="descricao_produto">DESCRICAO</label>
                                        <input required type="text" class="form-control" name="descricao_produto" value="${form == "alterar" ? p.descricao_produto : ""}" placeholder="Ex.: bla">
                                    </div>          
                                    <!--TODO FAZER MASCARA PARA TELEFONE-->             
                                    <div class="row">
                                        <label for="peso_produto">PESO (em gramas)</label>
                                        <input required type="text" class="form-control" name="peso_produto" value="${form == "alterar" ? p.peso_produto : ""}" placeholder="500 gramas">
                                    </div>

                                    <div class="row">
                                        <label>CATEGORIA</label><br>
                                        <select name="categoria" class="form-control" required>
                                        <c:forEach items="${categorias}" var="x">
                                            <c:if test = "${x.id_categoria == p.id_categoria}">
                                                <option value="${x.id_categoria}" selected>${x.nome_categoria}</option>
                                            </c:if>
                                            <c:if test = "${x.id_categoria != p.id_categoria}">
                                                <option value="${x.id_categoria}">${x.nome_categoria}</option>                                  
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                        </div>
                    </div> 
                    <c:if test = "${form != 'visualizar'}">
                        <input type="submit" class="btn btn-primary" value="${button}">
                        <a href="ProdutoServlet?action=list" class="btn btn-danger"> CANCELAR </a>
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
