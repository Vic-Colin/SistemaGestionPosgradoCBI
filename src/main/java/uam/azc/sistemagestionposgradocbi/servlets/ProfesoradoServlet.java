/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package uam.azc.sistemagestionposgradocbi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import javax.servlet.http.HttpSession;
import uam.azc.sistemagestionposgradocbi.DAO.ProfesorDAO;
import uam.azc.sistemagestionposgradocbi.modelo.Profesor;

/**
 *
 * @author CASH
 */
@WebServlet(name = "ProfesoradoServlet", urlPatterns = {"/ProfesoradoServlet"})
public class ProfesoradoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ProfesorDAO dao;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        dao = new ProfesorDAO();
        gson = new Gson();
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
        // 🔐 Validación de sesión (opcional pero recomendable)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioActivo") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String noEconomico = request.getParameter("noEconomico");
        String cvu = request.getParameter("cvu");

        if (noEconomico != null) noEconomico = noEconomico.trim();
        if (cvu != null) cvu = cvu.trim();

        List<Profesor> lista = dao.buscarProfesores(noEconomico, cvu);

        String json = gson.toJson(lista);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
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
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "Método POST no permitido en este servlet.");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet para filtrado dináico de profesorado";
    }// </editor-fold>

}
