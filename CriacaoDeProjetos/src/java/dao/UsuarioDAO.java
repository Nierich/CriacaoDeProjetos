/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import apoio.ConexaoBD;
import entidade.Usuario;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author gabri
 */
public class UsuarioDAO {

    public boolean salvar(Usuario u) {

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "insert into usuario values ("
                    + "default,"
                    + "'" + u.getEmail() + "', "
                    + "md5('" + u.getSenha() + "'), "
                    + "'" + u.getHierarquia() + "');";

            System.out.println("SQL: " + sql);

            st.executeUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Erro ao salvar Usuario: " + e);
            return false;
        }
    }

    public ArrayList consultar() {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * from usuario";

            System.out.println("SQL: " + sql);

            ResultSet resultado = st.executeQuery(sql);

            while (resultado.next()) {
                Usuario u = new Usuario();

                u.setId(resultado.getInt("id"));
                u.setEmail(resultado.getString("email"));
                u.setSenha("");
                u.setHierarquia(resultado.getString("hierarquia"));

                usuarios.add(u);
            }

        } catch (Exception e) {
            System.out.println("Erro ao consular Usuario: " + e);
        }

        return usuarios;
    }

    public Usuario consultar(int id) {
        Usuario usuario = new Usuario();

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * from usuario "
                    + "where "
                    + "id = " + id;

            System.out.println("SQL: " + sql);

            ResultSet resultado = st.executeQuery(sql);

            resultado.next();

            usuario.setId(resultado.getInt("id"));
            usuario.setEmail(resultado.getString("email"));
            usuario.setSenha("");
            usuario.setHierarquia(resultado.getString("hierarquia"));

        } catch (Exception e) {
            System.out.println("Erro ao consular UMA Categoria: " + e);
        }

        return usuario;
    }

    public boolean excluir(int id) {

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "delete from usuario "
                    + "where "
                    + "id = " + id;

            System.out.println("SQL: " + sql);

            st.executeUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Erro ao excluir Usuario: " + e);
            return false;
        }
    }

    public boolean atualizar(Usuario u) {

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "update usuario "
                    + "set "
                    + "email = '" + u.getEmail() + "', "
                    + "senha = '" + u.getSenha() + "', "
                    + "hierarquia = '" + u.getHierarquia() + "' "
                    + "where id = " + u.getId();

            System.out.println("SQL: " + sql);

            st.executeUpdate(sql);

            return true;

        } catch (Exception e) {
            System.out.println("Erro ao atualizar Usuario: " + e);
            return false;
        }
    }

    public boolean autenticar(Usuario u) {

        try {
            String sql
                    = "SELECT email, senha, hierarquia "
                    + "FROM usuario "
                    + "WHERE "
                    + "email = '" + u.getEmail() + "' "
                    + "AND senha = md5('" + u.getSenha() + "')";

            System.out.println("SQL: " + sql);

            ResultSet resultadoQ = ConexaoBD.getInstance().getConnection().createStatement().executeQuery(sql);

            if (resultadoQ.next()) {
                String hierarquia = resultadoQ.getString("hierarquia");
                System.out.println("Valor de 'hierarquia' no DAO: " + hierarquia);
                u.setHierarquia(hierarquia);
                System.out.println("Valor de 'hierarquia' no objeto Usuario: " + u.getHierarquia());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro ao autenticar usuário: " + e);
            return false;
        }
    }

}
