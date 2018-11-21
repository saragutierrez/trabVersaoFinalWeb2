<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@ page errorPage="erro.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${!empty param.msg}">
    <div class="alert alert-danger" role="alert"><h2 style= color:red;text-align:center>${param.msg}</h2></div>
    </c:if>
    <c:if test="${!empty requestScope.msg}">
    <div class="alert alert-danger" role="alert"><h2 style= color:red;text-align:center>${requestScope.msg}</h2></div>
    </c:if>


<html>
    <head>
        <title>Login</title>

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="style/css/style.css">
    </head> 
    <body>
        <section id="login" class="container-fluid">
            <form action="LoginServlet" method="POST">
                <div class="form-group row">
                    <h1>BIEBE</h1>
                </div>
                <div class="form-group row">
                    <label for="name">Login</label>
                    <input type="text" maxlength="100" class="form-control" name="login" placeholder="login" required>
                </div>                
                <div class="form-group row">
                    <label for="senha">Senha</label>
                    <input type="password" maxlength="50" class="form-control" name="senha" aria-describedby="senhaHelp" placeholder="senha" required>
                </div>
                <div class="row">
                    <div class="col-8">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                    <div class="col-4">
                        <a href="ClientesServlet?action=cadastro" class="btn btn-primary">Cadastrar</a>
                    </div>
                </div>
            </form>
        </section>
    </body>
</html>
