<%-- 
    Document   : clientesListar
    Created on : 20/09/2018, 19:54:22
    Author     : sa
--%>

<%@page import="com.ufpr.tads.web2.beans.UsuarioBean"%>
<%@page import="java.util.List"%>
<%@page import="com.mysql.jdbc.StringUtils"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="erro.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<script>
    function confirmaExclusao(id) {
        if (confirm("Deseja mesmo apagar o registro com código " + id + "?")) {
            // Faz o processamento necessário para exclusão
            location.href = "ClientesServlet?action=remove&id=" + id + "";
        }
    }
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>cli list</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    </head>
    <c:choose>
        <c:when test = "${not empty loginBean.nome}">
            <body>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">NOME</th>
                            <th scope="col">CPF</th>
                            <th scope="col">EMAIL</th>
                            <th scope="col">x</th>
                            <th scope="col">x</th>
                            <th scope="col">x</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${lista}" var="x">
                            <tr>
                                <td>${x.nome}</td>
                                <td>${x.cpf}</td>
                                <td>${x.email}</td>
                                <td><a href="ClientesServlet?action=show&id=${x.id}"><button class="btn btn-primary">Visualizar</button></a></td>
                                <td><a href="ClientesServlet?action=formUpdate&id=${x.id}"><button class="btn btn-primary">Alterar</button></a></td>
                                <td><a href="javascript: confirmaExclusao(${x.id});"><button class="btn btn-primary">Remover</button></a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <a href="ClientesServlet?action=formNew"><button class="btn btn-primary">NOVO</button></a>
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
