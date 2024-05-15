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

    <%
        ProjetoDAO projetoDAO = new ProjetoDAO();
        ArrayList<Projeto> projetos = projetoDAO.consultarTodos();
        
        Requisito r = (Requisito) request.getAttribute("requisito");
        
        int projeto_id = 1;

        if (r == null) {
            r = new Requisito();
            r.setDescricao("");
            r.setNome("");
  
        } else{
        projeto_id = r.getProjeto_id();
        }
        
        System.out.println("projeto_id antes do formulário: " + projeto_id);
    %>

    <body>
        <%@include file="menu.jsp" %><br>


        <h1>Cadastro de Requisitos</h1> <br>

        <form method="post" action="acao?a=salvarRequisito"> <!--acao?a=salvarProjeto -->
            <label for="id">ID:</label><br>
            <input type="text" id="id" name="id" readonly="" value="<%= r.getId() %>"><br>
            
            <label for="nome">Nome do Requisito:</label><br>
            <input type="text" id="nome" name="nome" value="<%= r.getNome() %>"><br>

            <label for="projetos">Escolha um projeto:</label> <br>
            <select class="form-select w-25" aria-label="Default select example" name ="projeto_id">
                <%
                   for (int i = 0; i < projetos.size(); i++) {
                %>
                <option value="<%= projetos.get(i).getId()%>" <%= (projeto_id == projetos.get(i).getId()) ? "selected" : "" %>><%= projetos.get(i).getNome()%> (ID: <%= projetos.get(i).getId()%>) </option>

                <%
                    }
                %>
            </select> <br>

            <label for="projetos">Tipo do requisito:</label> <br>
            <select class="form-select w-25" aria-label="Default select example" name = "tipo">
                <option value="funcional" <%= (r.getTipo() != null && r.getTipo().equals("Funcional")) ? "selected" : "" %>>Funcional</option>
                <option value="naofuncional" <%= (r.getTipo() != null && r.getTipo().equals("NaoFuncional")) ? "selected" : "" %>>Não-Funcional</option>
            </select> <br>

            <label for="projetos">Grau de complexidade:</label> <br>
            <select class="form-select w-25" aria-label="Default select example" name ="complexidade">
                <option value="baixo" <%= (r.getComplexidade() != null && r.getComplexidade().equals("baixo")) ? "selected" : "" %>>Baixa</option>
                <option value="medio" <%= (r.getComplexidade() != null && r.getComplexidade().equals("medio")) ? "selected" : "" %>>Média</option>
                <option value="alto" <%= (r.getComplexidade() != null && r.getComplexidade().equals("alto")) ? "selected" : "" %>>Alta</option>
            </select> <br>


            <label for="descricao">Descrição:</label><br>
            <textarea id="descricao" name="descricao" rows="4" cols="50"><%= r.getDescricao() %></textarea>



            <br>
            <input type="submit" name="salvar" value="SALVAR" class="btn btn-success">
            <input type="Reset" name="reset" value="LIMPAR" class="btn btn-dark">
        </form> 

        <br>
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
