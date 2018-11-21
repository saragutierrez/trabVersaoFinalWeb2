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
    <script>
        function CPF() {
            "user_strict";
            function r(r) {
                for (var t = null, n = 0; 9 > n; ++n)
                    t += r.toString().charAt(n) * (10 - n);
                var i = t % 11;
                return i = 2 > i ? 0 : 11 - i
            }
            function t(r) {
                for (var t = null, n = 0; 10 > n; ++n)
                    t += r.toString().charAt(n) * (11 - n);
                var i = t % 11;
                return i = 2 > i ? 0 : 11 - i
            }
            var n = "CPF Inválido", i = "CPF Válido";
            this.gera = function () {
                for (var n = "", i = 0; 9 > i; ++i)
                    n += Math.floor(9 * Math.random()) + "";
                var o = r(n), a = n + "-" + o + t(n + "" + o);
                return a
            }, this.valida = function (o) {
                for (var a = o.replace(/\D/g, ""), u = a.substring(0, 9), f = a.substring(9, 11), v = 0; 10 > v; v++)
                    if ("" + u + f == "" + v + v + v + v + v + v + v + v + v + v + v)
                        return n;
                var c = r(u), e = t(u + "" + c);
                return f.toString() === c.toString() + e.toString() ? i : n
            }
        }

        var CPF = new CPF();

        $(document).ready(function () {
            $("#cpf").keyup(function () {
                var teste = CPF.valida($(this).val());
                $("#resposta").html(teste);
                if (teste == "CPF Válido") {
                    $("#cadastrar").removeAttr("disabled");
                } else {
                    $("#cadastrar").attr("disabled", true);
                }
            });

            $("#cpf").blur(function () {
                var teste = CPF.valida($(this).val());
                $("#resposta").html(teste);
                if (teste == "CPF Válido") {
                    $("#cadastrar").removeAttr("disabled");
                } else {
                    $("#cadastrar").attr("disabled", true);
                }
            });
        });
    </script>
    <script type="text/javascript" >

        $(document).ready(function () {
            $("#estado").change(function () {
                getCidades();
            });
        });

        function getCidades() {
            var estadoId = $("#estado").val();
            var url = "AJAXServlet";
            $.ajax({
                url: url, // URL da sua Servlet
                data: {
                    estadoId: estadoId
                }, // Parâmetro passado para a Servlet
                dataType: 'json',
                success: function (data) {
                    // Se sucesso, limpa e preenche a combo de cidade
                    // alert(JSON.stringify(data));
                    $("#cidade").empty();
                    $.each(data, function (i, obj) {
                        $("#cidade").append('<option value=' + obj.id + '>' + obj.nome + '</option>');
                    });
                },
                error: function (request, textStatus, errorThrown) {
                    alert(request.status + ', Error: ' + request.statusText);
                    // Erro
                }
            });
        }
    </script>
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
    <script>
        $(document).ready(function () {
            $("#cpf").mask('000.000.000-00', {reverse: true});
            $("#cep").mask("00000-000", {reverse: true});
            $("#estado").change(function () {
                getCidades();
            });
        });
    </script>
    <c:if test = "${form == 'alterar'}">
        <c:set var="titulo" value="ALTERAR"/>
        <c:set var="action" value="GerenteServlet?action=update"/>
        <c:set var="button" value="ALTERAR"/>
    </c:if>
    <c:if test = "${form == 'alterarC'}">
        <c:set var="titulo" value="ALTERAR"/>
        <c:set var="action" value="ClientesServlet?action=update"/>
        <c:set var="button" value="ALTERAR"/>
    </c:if>
    <c:if test = "${form == 'newF'}">
        <c:set var="titulo" value="CADASTRAR"/>
        <c:set var="action" value="GerenteServlet?action=new"/>
        <c:set var="button" value="SALVAR"/>
    </c:if>
    <c:if test = "${form == 'newC'}">
        <c:set var="titulo" value="CADASTRAR"/>
        <c:set var="action" value="ClientesServlet?action=new"/>
        <c:set var="button" value="SALVAR"/>
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
    </c:if>
    <c:if test= "${loginBean.tipo == 'G'}">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">${titulo}</a>
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
        <!--        <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <a class="navbar-brand" href="#">${titulo}</a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                </nav> -->
        <c:choose>
            <c:when test = "${not empty loginBean.tipo || not empty logB}">
                <section id="formulario_usuario" class="container-fluid">
                    <form class="form-group" action="${action}" method="POST"> 
                        <div class="row">
                            <input type="hidden" class="form-control" name="id" value="${form == ("alterar"||"alterarC") ? c.id : ""}">
                        </div>
                        <div class="row">
                            <label for="name">NOME</label>
                            <input required type="text" class="form-control" name="nome" value="${form == ("alterar"||"alterarC") ? c.nome : ""}" placeholder="Ex.: Ana da Silva">
                        </div>
                        <c:if test = "${form != 'alterarC'}">
                            <div class="row">
                                <label for="cpf">CPF</label>
                                <input required type="text" class="form-control" id="cpf" onBlur="javascript:validarCpf();" name="cpf" value="${form == "alterar" ? c.cpf : ""}" placeholder="Ex.: xxx.xxx.xxx-xx">
                            </div>

                            <div class="row">
                                <label for="email">EMAIL</label>
                                <input required type="text" class="form-control" name="email" value="${form == "alterar" ? c.email : ""}" placeholder="Ex.: ana@gmail.com">
                            </div>     
                        </c:if>
                        <!--TODO FAZER MASCARA PARA TELEFONE-->             
                        <div class="row">
                            <label for="tel">TELEFONE</label>
                            <input required type="text" class="form-control" name="tel" value="${form == ("alterar"||"alterarC") ? c.tel : ""}" placeholder="(41)565555">
                        </div>

                        <div class="row">
                            <label for="rua">RUA</label>
                            <input required type="text" class="form-control" name="rua" value="${form == ("alterar"||"alterarC") ? c.rua : ""}" placeholder="Ex.: Rua das Flores">
                        </div>
                        <div class="row">
                            <label for="num">Nº</label>
                            <input required type="text" class="form-control" name="nr_cliente" value="${form == ("alterar"||"alterarC") ? c.nr_casa : ""}" placeholder="Ex.: 4512">                      
                        </div>
                        <div class="row">
                            <label for="complemento">Complemento</label>
                            <input required type="text" class="form-control" name="complemento" value="${form == ("alterar"||"alterarC") ? c.complemento : ""}" placeholder="Ex.: casa">                      
                        </div>                                    
                        <div class="row">
                            <label for="cep">CEP</label>
                            <input required type="text" class="form-control" id="cep" name="cep" value="${form == ("alterar"||"alterarC") ? c.cep : ""}" placeholder="Ex.: 00000-000">
                        </div> 
                        <!--todo cadastrar se é funcionario ou gerente-->      
                        <div class="btn-group" data-toggle="buttons">
                            <c:if test = "${c.tipo == 'G'}">
                                <label class="btn btn-default active">
                                    <input type="radio" id="tipo" name="tipo" value="G" checked/> Gerente
                                </label>
                                <label class="btn btn-default">
                                    <input type="radio" id="tipo1" name="tipo" value="F" />Funcionario
                                </label>
                            </c:if>
                            <c:if test = "${c.tipo == 'F'}">
                                <label class="btn btn-default active">
                                    <input type="radio" id="tipo" name="tipo" value="G"/> Gerente
                                </label>
                                <label class="btn btn-default">
                                    <input type="radio" id="tipo1" name="tipo" value="F" checked/>Funcionario
                                </label>
                            </c:if>
                        </div>           
                        <c:if test = "${form == ('newC'|| 'alterarC' || 'newF')}">
                            <div class="row">
                                <label for="senha">Senha</label>
                                <input required type="text" class="form-control" id="senha" name="senha" value="${form == ("alterar"||"alterarC") ? c.senha : ""}" placeholder="Ex.: senhas">
                            </div> 
                        </c:if>

                        <div class="row">
                            <label>UF</label><br>
                            <select id="estado" name="estado" class="form-control" required>
                                <c:forEach items="${estados}" var="x">
                                    <c:if test = "${x.id == c.estado.id}">
                                        <option value="${x.id}" selected>${x.sigla}</option>
                                        getCidades();                                                 
                                    </c:if>
                                    <c:if test = "${x.id != c.estado.id}">
                                        <option value="${x.id}">${x.sigla}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="row">
                            <label>Cidade:</label><br>
                            <select class="form-control" id="cidade" name="cidade" required>
                                <c:forEach items="${cidades}" var="x">
                                    <c:if test = "${x.id == c.cidade.id}">
                                        <option value="${x.id}" selected>${x.nome}</option>
                                        getCidades();                                                 
                                    </c:if>
                                    <c:if test = "${x.id != c.cidade.id}">
                                        <option value="${x.id}">${x.nome}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div id="botao" class="row">
                            <div class="col-8">
                                <input type="submit" id="cadastrar" class="btn btn-success" value="${button}">
                            </div>
                            <div class="col-4">
                                <a href="portal.jsp" class="btn btn-danger"> CANCELAR </a>
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
