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
        <%@include file="menu.jsp" %><br>
        
        
    <%
        Projeto p = (Projeto) request.getAttribute("projeto");

        if (p == null) {
            p = new Projeto();
            p.setNome("");
            p.setDescricao("");
        }
    %>


        <h1>Cadastro de Projetos</h1>

        <form method="post" action="acao?a=salvarProjeto"> 
            <label for="id">ID:</label><br>
            <input type="text" id="id" name="id" readonly="" value="<%= p.getId() %>"><br>

            <label for="nome">Nome:</label><br>
            <input type="text" id="nome" name="nome" value="<%= p.getNome() %>"> <br>

           <label for="descricao">Descrição:</label><br>
            <textarea id="descricao" name="descricao" rows="4" cols="50"><%= p.getDescricao() %></textarea>


            <br>
            <input type="submit" name="salvar" value="+CRIAR" class="btn btn-success">
            <input type="Reset" name="reset" value="LIMPAR" class="btn btn-dark">
        </form> 

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
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <%for (int i = 0; i < pro.size(); i++) { %>
                <tr>
                    <td><%= pro.get(i).getId()%></td>
                    <td><%= pro.get(i).getNome() %></td>
                    <td><%= pro.get(i).getDescricao() %></td>    
                    <td><a href="acao?a=editarProjeto&id=<%= pro.get(i).getId()%>" class="btn btn-primary">Editar</a></td>
                    <td><a href="acao?a=excluirProjeto&id=<%= pro.get(i).getId()%>" class="btn btn-danger">Excluir</a></td>
                </tr>
                <% } %>
            </tbody>
        </table>

    </body>
</html>
