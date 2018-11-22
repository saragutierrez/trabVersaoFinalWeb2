<%-- 
    Document   : erro
    Created on : 27/08/2018, 21:28:31
    Author     : sa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    </head>
    <body>
         Exceção:
            ${pageContext.exception.message}
            <br>
            
            StackTrace:
            ${pageContext.out.flush()}
            ${exception.printStackTrace(pageContext.response.writer)}
        <footer>
            <div class="alert alert-success" role="alert" style="text-align: center">                 
                <h4>Em caso de problemas contactar a administradora: ${configuracao.email}</h4>
            </div>
        </footer>
    </body>

</html>
