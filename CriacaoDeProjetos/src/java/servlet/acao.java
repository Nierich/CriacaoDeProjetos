package servlet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dao.ProjetoDAO;
import dao.RequisitoDAO;
import dao.UsuarioDAO;
import entidade.Projeto;
import entidade.Requisito;
import entidade.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import apoio.ConexaoBD;

/**
 *
 * @author gabri
 */
@WebServlet(urlPatterns = {"/acao"})
public class acao extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet acao</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet acao at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String a = request.getParameter("a");

        if (a.equals("editarUsuario")) {
            String id = request.getParameter("id");
            int codigo = Integer.parseInt(id);

            Usuario usuario = new UsuarioDAO().consultar(codigo);

            request.setAttribute("usuario", usuario);

            encaminharPagina("cadastrousuario.jsp", request, response);
        }

        if (a.equals("excluirUsuario")) {
            String id = request.getParameter("id");
            int codigo = Integer.parseInt(id);

            if (new UsuarioDAO().excluir(codigo)) {
                encaminharPagina("cadastrousuario.jsp", request, response);
            } else {
                encaminharPagina("erro.jsp", request, response);
            }
        }

        if (a.equals("editarProjeto")) {
            String id = request.getParameter("id");
            int codigo = Integer.parseInt(id);

            Projeto projeto = new ProjetoDAO().consultar(codigo);

            request.setAttribute("projeto", projeto);

            encaminharPagina("projeto.jsp", request, response);
        }

        if (a.equals("excluirProjeto")) {
            String id = request.getParameter("id");
            int codigo = Integer.parseInt(id);

            if (new ProjetoDAO().excluir(codigo)) {
                encaminharPagina("projeto.jsp", request, response);
            } else {
                encaminharPagina("erro.jsp", request, response);
            }
        }

        if (a.equals("desativarRequisito")) {
            String id = request.getParameter("id");
            int codigo = Integer.parseInt(id);

            if (new RequisitoDAO().desativar(codigo)) {
                encaminharPagina("requisito.jsp", request, response);
            } else {
                encaminharPagina("erro.jsp", request, response);
            }
        }

        if (a.equals("atualizarRequisito")) {
            String id = request.getParameter("id");
            int codigo = Integer.parseInt(id);

            Requisito requisito = new RequisitoDAO().consultar(codigo);

            request.setAttribute("requisito", requisito);

            encaminharPagina("requisito.jsp", request, response);
        }
        
        System.out.println("entrou no get " +a);
        if (a.equals("gerarRelatorio")) {
           
        System.out.println("relatorio gerado");
            
            gerarRelatorio(request, response);
            
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String a = request.getParameter("a");
        LocalDateTime localdatetime = getCurrentTimestamp();

        if (a.equals("entrar")) {
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            Usuario us = new Usuario();
            us.setEmail(email);
            us.setSenha(senha);

            System.out.println("Valor de 'email': " + email);
            System.out.println("Valor de 'senha': " + senha);

            if (new UsuarioDAO().autenticar(us)) {
                HttpSession sessao = request.getSession();
                sessao.setAttribute("user", us);

                //"a" = admin
                if ("Admin".equals(us.getHierarquia())) {
                    System.out.println("Hierarquia do usuário (Admin): " + us.getHierarquia());
                    encaminharPagina("admin.jsp", request, response);
                } else {
                    System.out.println("Hierarquia do usuário (Usuário): " + us.getHierarquia());
                    encaminharPagina("usuario.jsp", request, response);
                }
            } else {
                encaminharPagina("erro.jsp", request, response);
            }
        }
        if (a.equals("logout")) {
            HttpSession sessao = request.getSession();
            sessao.invalidate();

            response.sendRedirect("login.jsp");
        }

        if (a.equals("salvarUsuario")) {
            String id = request.getParameter("id");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String hierarquia = request.getParameter("hierarquia");

            Usuario usuario = new Usuario();
            int codigo = Integer.parseInt(id);

            usuario.setId(codigo);
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setHierarquia(hierarquia);

            if (codigo == 0) {
                if (new UsuarioDAO().salvar(usuario)) {
                    encaminharPagina("sucesso.jsp", request, response);
                } else {
                    encaminharPagina("erro.jsp", request, response);
                }
            } else {
                if (new UsuarioDAO().atualizar(usuario)) {
                    encaminharPagina("sucesso.jsp", request, response);
                } else {
                    encaminharPagina("erro.jsp", request, response);
                }
            }
        }

        if (a.equals("salvarProjeto")) {
            String id = request.getParameter("id");
            String nome = request.getParameter("nome");
            String descricao = request.getParameter("descricao");

            Projeto projeto = new Projeto();
            int codigo = Integer.parseInt(id);

            projeto.setId(codigo);
            projeto.setNome(nome);
            projeto.setDescricao(descricao);

            if (codigo == 0) {
                if (new ProjetoDAO().salvar(projeto)) {
                    encaminharPagina("sucesso.jsp", request, response);
                } else {
                    encaminharPagina("erro.jsp", request, response);
                }
            } else {
                if (new ProjetoDAO().atualizar(projeto)) {
                    encaminharPagina("sucesso.jsp", request, response);
                } else {
                    encaminharPagina("erro.jsp", request, response);
                }
            }
        }

        if (a.equals("salvarRequisito")) {
            System.out.println("entrou no if");
            String id = request.getParameter("id");
            String descricao = request.getParameter("descricao");
            String tipo = request.getParameter("tipo");
            String complexidade = request.getParameter("complexidade");
            String projeto_id = request.getParameter("projeto_id");
            String nome = request.getParameter("nome");

            System.out.println("ID: " + id);
            System.out.println("Descricao: " + descricao);
            System.out.println("Tipo: " + tipo);
            System.out.println("Complexidade: " + complexidade);
            System.out.println("Projeto ID: " + projeto_id);

            Requisito requisito = new Requisito();
            int codigo = Integer.parseInt(id);
            int codigo_projeto = Integer.parseInt(projeto_id);

            requisito.setId(codigo);
            requisito.setDescricao(descricao);
            requisito.setTipo(tipo);
            requisito.setComplexidade(complexidade);
            requisito.setProjeto_id(codigo_projeto);
            requisito.setNome(nome);
            requisito.setStatus("ativo");
            requisito.setDatahora(localdatetime);

            if (codigo == 0) {
                if (new RequisitoDAO().salvar(requisito)) {
                    encaminharPagina("sucesso.jsp", request, response);
                } else {
                    encaminharPagina("erro.jsp", request, response);
                }
            } else {
                if (new RequisitoDAO().atualizar(requisito)) {
                    new RequisitoDAO().salvar(requisito);
                    encaminharPagina("sucesso.jsp", request, response);
                } else {
                    encaminharPagina("erro.jsp", request, response);
                }
            }
        }
        if (a.equals("consultarPorProjeto")) {
            String projeto_id = request.getParameter("projeto_id");
            int idProjeto = Integer.parseInt(projeto_id);

            System.out.println("Chegou aqui");

            ArrayList<Requisito> requisito = new RequisitoDAO().consultarPorProjeto(idProjeto);

            request.setAttribute("requisitos", requisito);
            
            encaminharPagina("requisito.jsp", request, response);
        }
        
       System.out.println("entrou no post " +a);
        if (a.equals("gerarRelatorio")) {
           
        System.out.println("relatorio gerado");
            
            gerarRelatorio(request, response);
            
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void encaminharPagina(String pagina, HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher(pagina);
            rd.forward(request, response);
        } catch (Exception e) {
            System.out.println("Erro ao encaminhar: " + e);
        }
    }

    private void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, String> reports = new HashMap<>();
        String basePath = "/servlet/relatorio/";
        reports.put("relProjetos", basePath + "relProjetos.jrxml");        


        int projectId = Integer.parseInt(request.getParameter("id"));
        try {
            String selectedReport = reports.get("relProjetos");
            InputStream inputStream = getClass().getResourceAsStream(selectedReport);
            JasperReport report = JasperCompileManager.compileReport(inputStream);

            Map params = new HashMap<>();
            params.put("idProjeto", projectId);

            JasperPrint print = JasperFillManager.fillReport(
                    report,
                    params,
                    ConexaoBD.getInstance().getConnection()
            );

            JasperViewer.viewReport(print, false);

            String createMessage = "Relatório sendo gerado";
            request.setAttribute("create_message", createMessage);
            encaminharPagina("relatorio.jsp", request, response);
        } catch (JRException ex) {
            Logger.getLogger(acao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }

}
