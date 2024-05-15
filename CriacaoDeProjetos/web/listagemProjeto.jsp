<%-- 
    Document   : projeto
    Created on : 2 de nov. de 2023, 13:37:03
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
        <title>Cadastro de Projetos</title>
    </head>
    <body>
        <%@include file="menuUser.jsp" %><br>
<%
        Projeto p = (Projeto) request.getAttribute("projeto");

        if (p == null) {
            p = new Projeto();
        }
    %>

        <br>
        <h3>Listagem dos projetos</h3>
        
        <%            ArrayList<Projeto> pro = new ProjetoDAO().consultar();
        %>

        <table class="table table-bordered w-50 p-3">
            <thead class="bg-primary text-white">
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Descrição</th>
                </tr>
            </thead>
            <tbody>
                <%for (int i = 0; i < pro.size(); i++) { %>
                <tr>
                    <td><%= pro.get(i).getId()%></td>
                    <td><%= pro.get(i).getNome() %></td>
                    <td><%= pro.get(i).getDescricao() %></td>   
                </tr>
                <% } %>
            </tbody>
        </table>

    </body>
</html>
