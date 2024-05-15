<%-- 
    Document   : requisito
    Created on : 2 de nov. de 2023, 13:56:06
    Author     : gabri
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ProjetoDAO"%>
<%@page import="dao.RequisitoDAO"%>
<%@page import="entidade.Projeto"%>
<%@page import="entidade.Requisito"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Requisitos</title>
    </head>
    <body>
        <%@include file="menuUser.jsp" %><br>
        <%
                ProjetoDAO projetoDAO = new ProjetoDAO();
                ArrayList<Projeto> projetos = projetoDAO.consultarTodos();
        
                Requisito r = (Requisito) request.getAttribute("requisito");
        
                int projeto_id = 1;

                if (r == null) {
                    r = new Requisito();
  
                } else{
                projeto_id = r.getProjeto_id();
                }
        
        %>

        <h3>Listagem dos projetos</h3>

        <form method="post" action="acao?a=consultarPorProjeto">
            <select class="form-select w-25" aria-label="Default select example" required title="Qual o Projeto?" name="projeto_id">
                <% for (int i = 0; i < projetos.size(); i++) { %>
                <option value="<%= projetos.get(i).getId()%>" <%= (projeto_id == projetos.get(i).getId()) ? "selected" : "" %>>
                    <%= projetos.get(i).getNome()%> (ID: <%= projetos.get(i).getId()%>) 
                </option>
                <% } %>
            </select> <br>
            <input type="submit" value="Consultar Requisitos" class="btn btn-warning">
            <br>
        </form>

        <%
            ArrayList<Requisito> req = (ArrayList<Requisito>)request.getAttribute("requisitos");
            System.out.println("projeto_id antes de exibir os requisitos: " + projeto_id);
        %>
        <br>
        <table class="table table-bordered w-50 p-3">
            <thead class="bg-primary text-white">
                <tr>
                    <th>Nome</th>
                    <th>Tipo</th>
                    <th>Complexidade</th>
                    <th>Descrição</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <% if (req != null) { %>
                <% for (int i = 0; i < req.size(); i++) { %>
                <tr>
                    <td><%= req.get(i).getNome() %></td>
                    <td><%= req.get(i).getTipo() %></td>
                    <td><%= req.get(i).getComplexidade() %></td>
                    <td><%= req.get(i).getDescricao() %></td>    
                    <td><a href="acao?a=atualizarRequisito&id=<%= req.get(i).getId()%>" class="btn btn-primary">Atualizar</a></td>
                    <td><a href="acao?a=desativarRequisito&id=<%= req.get(i).getId()%>" class="btn btn-danger">Desativar</a></td>
                </tr>
                <% } %>
                <% } else { %>
                <tr>
                    <td colspan="4">Nenhum requisito encontrado.</td>
                </tr>
                <% } %>
            </tbody>
        </table>


    </body>
</html>
