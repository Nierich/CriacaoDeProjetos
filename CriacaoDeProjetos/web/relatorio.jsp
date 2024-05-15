<%-- 
    Document   : relatorio
    Created on : 2 de nov. de 2023, 13:56:17
    Author     : gabri
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ProjetoDAO"%>
<%@page import="entidade.Projeto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Relatórios</title>
    </head>


    <body class="input_field_create text-center mx-auto">
        <%@include file="menu.jsp" %><br>

        <h1>Geração de relatórios</h1> <br>

        <form method="post" id="formulario" action="">
            <select class="form-select text-center w-50 mx-auto" aria-label="Default select example" name="projeto" id="idSelect" onChange="pegarId()">
                <option value="" disabled selected >Escolha um projeto</option>
                <%
                    ProjetoDAO projetoDAO = new ProjetoDAO();
                    ArrayList<Projeto> projetos = projetoDAO.consultarTodos();
                %>

                <%
                    for (int i = 0; i < projetos.size(); i++) {
                %>
                <option value="<%= projetos.get(i).getId()%>"><%= projetos.get(i).getNome()%> (ID: <%= projetos.get(i).getId()%>) </option>
                <%
                    }
                %>
            </select>
            <br>

            <input type="submit" name="relatorio" value="Gerar PDF" class="btn btn-primary">
        </form>

        <br>
    </body>
    <script src="/js/relatorio.js"></script>
</html>