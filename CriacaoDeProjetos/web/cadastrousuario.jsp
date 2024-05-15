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
        <%@include file="menu.jsp" %><br>

        <%
        Usuario u = (Usuario) request.getAttribute("usuario");

        if (u == null) {
            u = new Usuario();
            u.setEmail("");  // Define o email como vazio
            u.setSenha("");  // Define a senha como vazia
        }
        
        %>


        <h1>Cadastro de Usuários</h1>

        <form method="post" action="acao?a=salvarUsuario"> <!--  -->

            <label for="id">ID</label><br>
            <input type="text" id="id" name="id" readonly="" value="<%= u.getId() %>"><br>

            <label for="email">Email:</label><br>
            <input type="text" id="email" name="email" value="<%= u.getEmail() %>"><br>

            <label for="senha">Senha:</label><br>
            <input type="password" id="senha" name="senha" value="<%= u.getSenha() %>"> <br>


            <label for="hierarquia">Hierarquia:</label><br>
            <select class="form-select w-25" aria-label="Default select example" name="hierarquia">
                <option value="Admin" <%= (u.getHierarquia() != null && u.getHierarquia().equals("Admin")) ? "selected" : "" %>>Admin</option>
                <option value="Usuário" <%= (u.getHierarquia() != null && u.getHierarquia().equals("Usuário")) ? "selected" : "" %>>Usuário</option>
            </select>


            <br>
            <input type="submit" name="salvar" value="SALVAR" class="btn btn-success">
            <input type="Reset" name="reset" value="LIMPAR" class="btn btn-dark">
        </form> 

        <br>

        <h3>Listagem dos usúarios</h3>

        <%            ArrayList<Usuario> usua = new UsuarioDAO().consultar();
        %>

        <table class="table table-bordered w-50 p-3">
            <thead class="bg-primary text-white">
                <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Senha</th>
                    <th>Hierarquia</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <%for (int i = 0; i < usua.size(); i++) { %>
                <tr>
                    <td><%= usua.get(i).getId()%></td>
                    <td><%= usua.get(i).getEmail() %></td>
                    <td><%= usua.get(i).getSenha() %></td>
                    <td><%= usua.get(i).getHierarquia() %></td>      
                    <td><a href="acao?a=editarUsuario&id=<%= usua.get(i).getId()%>" class="btn btn-primary">Editar</a></td>
                    <td><a href="acao?a=excluirUsuario&id=<%= usua.get(i).getId()%>" class="btn btn-danger">Excluir</a></td>
                </tr>
                <% } %>
            </tbody>
        </table>

    </body>
</html>



