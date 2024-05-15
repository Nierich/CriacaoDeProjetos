/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import apoio.ConexaoBD;
import entidade.Projeto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author gabri
 */
public class ProjetoDAO {
    
    public boolean salvar(Projeto p) {

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "insert into projeto values ("
                    + "default,"
                    + "'" + p.getNome() + "', "
                    + "'" + p.getDescricao() + "') ";

            System.out.println("SQL: " + sql);

            st.executeUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Erro ao salvar Projeto: " + e);
            return false;
        }
    }

    public ArrayList consultar() {
        ArrayList<Projeto> projetos = new ArrayList<>();

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * from projeto";

            System.out.println("SQL: " + sql);

            ResultSet resultado = st.executeQuery(sql);

            while (resultado.next()) {
                Projeto p = new Projeto();

                p.setId(resultado.getInt("id"));
                p.setNome(resultado.getString("nome"));
                p.setDescricao(resultado.getString("descricao"));

                projetos.add(p);
            }

        } catch (Exception e) {
            System.out.println("Erro ao consular Projeto: " + e);
        }

        return projetos;
    }

    public Projeto consultar(int id) {
        Projeto projeto = new Projeto();

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * from projeto "
                    + "where "
                    + "id = " + id;

            System.out.println("SQL: " + sql);

            ResultSet resultado = st.executeQuery(sql);

            resultado.next();

            projeto.setId(resultado.getInt("id"));
            projeto.setNome(resultado.getString("nome"));
            projeto.setDescricao(resultado.getString("descricao"));

        } catch (Exception e) {
            System.out.println("Erro ao consular UMA Categoria: " + e);
        }

        return projeto;
    }

    public boolean excluir(int id) {

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "delete from projeto "
                    + "where "
                    + "id = " + id;

            System.out.println("SQL: " + sql);

            st.executeUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Erro ao excluir Projeto: " + e);
            return false;
        }
    }

    public boolean atualizar(Projeto u) {

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "update projeto "
                    + "set "
                    + "nome = '" + u.getNome() + "', "
                    + "descricao = '" + u.getDescricao() + "' "
                    + "where id = " + u.getId();

            System.out.println("SQL: " + sql);

            st.executeUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Erro ao atualizar Projeto: " + e);
            return false;
        }
    }
    public ArrayList<Projeto> consultarTodos() {
        ArrayList<Projeto> projeto = new ArrayList();

        try {
            Connection connection = ConexaoBD.getInstance().getConnection();
            String sql = "SELECT * "
                    + "FROM projeto "
                    + "ORDER BY nome";

            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println("SQL: " + statement.toString());
            ResultSet retorno = statement.executeQuery();
            while (retorno.next()) {
                Projeto projetos = new Projeto();

                projetos.setId(retorno.getInt("id"));
                projetos.setNome(retorno.getString("nome"));
                projetos.setDescricao(retorno.getString("descricao"));

                projeto.add(projetos);
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar Projeto " + e);
        }
        return projeto;
    }
}
