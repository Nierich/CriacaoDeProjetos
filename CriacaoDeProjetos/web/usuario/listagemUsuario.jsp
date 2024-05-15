<%-- 
    Document   : cadastrousuario
    Created on : 2 de nov. de 2023, 14:02:57
    Author     : gabri
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.UsuarioDAO"%>
<%@page import="entidade.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de usuário</title>
    </head>
    <body>
        <%@include file="menuUser.jsp" %><br>

        <%
        Usuario u = (Usuario) request.getAttribute("usuario");

        if (u == null) {
            u = new Usuario();
        }
        %>

        <br>

        <h3>Listagem dos usuários</h3>

        <% ArrayList<Usuario> usua = new UsuarioDAO().consultar(); %>

        <table class="table table-bordered w-50 p-3">
            <thead class="bg-primary text-white">
                <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Senha</th>
                    <th>Hierarquia</th>
                </tr>
            </thead>
            <tbody>
                <% for (int i = 0; i < usua.size(); i++) { %>
                <tr>
                    <td><%= usua.get(i).getId() %></td>
                    <td><%= usua.get(i).getEmail() %></td>
                    <td><%= usua.get(i).getSenha() %></td>
                    <td><%= usua.get(i).getHierarquia() %></td>
                    
                </tr>
                <% } %>
            </tbody>
        </table>

    </body>
</html>


