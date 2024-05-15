/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import apoio.ConexaoBD;
import entidade.Requisito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author gabri
 */
public class RequisitoDAO {

    public boolean salvar(Requisito r) {

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "INSERT INTO requisito VALUES ("
                    + "DEFAULT, "
                    + "'" + r.getDescricao() + "', "
                    + "'" + r.getTipo() + "', "
                    + "'" + r.getComplexidade() + "', "
                    + r.getProjeto_id() + ", "
                    + "'" + r.getNome() + "', "
                    + "'" + r.getDatahora() + "', "
                    + "'" + r.getStatus() + "')";

            System.out.println("SQL: " + sql);

            st.executeUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Erro ao salvar Requisito: " + e);
            return false;
        }
    }

    public ArrayList consultar() {
        ArrayList<Requisito> requisitos = new ArrayList<>();

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * from requisito";

            System.out.println("SQL: " + sql);

            ResultSet resultado = st.executeQuery(sql);

            while (resultado.next()) {
                Requisito r = new Requisito();

                r.setId(resultado.getInt("id"));
                r.setDescricao(resultado.getString("descricao"));
                r.setTipo(resultado.getString("tipo"));
                r.setComplexidade(resultado.getString("complexidade"));
                r.setProjeto_id(resultado.getInt("projeto_id"));
                r.setNome(resultado.getString("nome"));

                // Recupera a data e hora como string
                String dataHoraString = resultado.getString("datahora");

                // Converte a string para LocalDateTime (assumindo o formato "yyyy-MM-dd HH:mm:ss.SSSSSS")
                LocalDateTime dataHora = LocalDateTime.parse(dataHoraString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
                r.setDatahora(dataHora);

                r.setStatus(resultado.getString("status"));

                requisitos.add(r);
            }

        } catch (Exception e) {
            System.out.println("Erro ao consular Requisito: " + e);
        }

        return requisitos;
    }

    public Requisito consultar(int id) {
        Requisito requisito = new Requisito();

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * from requisito "
                    + "where "
                    + "id = " + id;

            System.out.println("SQL: " + sql);

            ResultSet resultado = st.executeQuery(sql);

            resultado.next();

            requisito.setId(resultado.getInt("id"));
            requisito.setDescricao(resultado.getString("descricao"));
            requisito.setTipo(resultado.getString("tipo"));
            requisito.setComplexidade(resultado.getString("complexidade"));
            requisito.setProjeto_id(resultado.getInt("projeto_id"));
            requisito.setNome(resultado.getString("nome"));

            // Recupera a data e hora como string
            String dataHoraString = resultado.getString("datahora");

            // Converte a string para LocalDateTime (assumindo o formato "yyyy-MM-dd HH:mm:ss")
            LocalDateTime dataHora = LocalDateTime.parse(dataHoraString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
            requisito.setDatahora(dataHora);

            requisito.setStatus(resultado.getString("status"));

        } catch (Exception e) {
            System.out.println("Erro ao consular UMA Categoria: " + e);
        }

        return requisito;
    }

    public boolean desativar(int id) {
        try {
            Connection connection = ConexaoBD.getInstance().getConnection();

            // Utiliza PreparedStatement para evitar SQL injection
            String sql = "UPDATE requisito "
                    + "SET status = 'inativo' "
                    + "WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }

            System.out.println("Requisito desativado com sucesso.");

            return true;

        } catch (Exception e) {
            System.out.println("Erro ao desativar Requisito: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Requisito r) {

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "update requisito "
                    + "set "
                    + "datahora = '" + r.getDatahora() + "', "
                    + "status = 'inativo' "
                    + "where id = " + r.getId();

            System.out.println("SQL: " + sql);

            st.executeUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Erro ao atualizar Projeto: " + e);
            return false;
        }
    }

    public ArrayList<Requisito> consultarTodos() {
        ArrayList<Requisito> requisito = new ArrayList();

        try {
            Connection connection = ConexaoBD.getInstance().getConnection();
            String sql = "SELECT * "
                    + "FROM requisito "
                    + "ORDER BY nome";

            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println("SQL: " + statement.toString());
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                Requisito requisitos = new Requisito();

                requisitos.setId(resultado.getInt("id"));
                requisitos.setDescricao(resultado.getString("descricao"));
                requisitos.setTipo(resultado.getString("tipo"));
                requisitos.setComplexidade(resultado.getString("complexidade"));
                requisitos.setProjeto_id(resultado.getInt("projeto_id"));
                requisitos.setNome(resultado.getString("nome"));

                // Recupera a data e hora como string
                String dataHoraString = resultado.getString("datahora");

                // Converte a string para LocalDateTime (assumindo o formato "yyyy-MM-dd HH:mm:ss")
                LocalDateTime dataHora = LocalDateTime.parse(dataHoraString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
                requisitos.setDatahora(dataHora);

                requisitos.setStatus(resultado.getString("status"));

                requisito.add(requisitos);
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar Requisito " + e);
        }
        return requisito;
    }

    public ArrayList<Requisito> consultarPorProjeto(int projetoId) {
        ArrayList<Requisito> requisitos = new ArrayList<>();

        try {
            Connection connection = ConexaoBD.getInstance().getConnection();
            System.out.println("Conexão com o banco de dados estabelecida.");

            String sql = "SELECT * "
                    + "FROM requisito "
                    + "WHERE projeto_id = ? "
                    + "AND status = 'ativo'";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, projetoId);
            System.out.println("SQL: " + statement.toString());

            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Requisito requisito = new Requisito();
                requisito.setId(resultado.getInt("id"));
                requisito.setDescricao(resultado.getString("descricao"));
                requisito.setTipo(resultado.getString("tipo"));
                requisito.setComplexidade(resultado.getString("complexidade"));
                requisito.setProjeto_id(resultado.getInt("projeto_id"));
                requisito.setNome(resultado.getString("nome"));

                // Recupera a data e hora como string
                String dataHoraString = resultado.getString("datahora");

                // Verifica se a string não é nula antes de tentar converter
                if (dataHoraString != null) {
                    // Converte a string para LocalDateTime (assumindo o formato "yyyy-MM-dd HH:mm:ss")
                    LocalDateTime dataHora = LocalDateTime.parse(dataHoraString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
                    requisito.setDatahora(dataHora);
                } else {
                    requisito.setDatahora(null); // Ou defina um valor padrão, dependendo dos requisitos do seu sistema.
                }

                requisito.setStatus(resultado.getString("status"));

                requisitos.add(requisito);
            }

            System.out.println("Consulta bem-sucedida. Número de requisitos encontrados: " + requisitos.size());

        } catch (Exception e) {
            System.out.println("Erro ao consultar Projeto: " + e.getMessage());
            e.printStackTrace(); // Adicionando rastreamento de pilha para ver a exceção completa.
        }

        return requisitos;
    }
}
