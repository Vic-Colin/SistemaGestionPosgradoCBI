
package uam.azc.sistemagestionposgradocbi.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uam.azc.sistemagestionposgradocbi.dao.BecaDAO;
import uam.azc.sistemagestionposgradocbi.modelo.Beca;

/**
 *
 * @author CASH
 */
@WebServlet(name = "BecaServlet", urlPatterns = {"/BecaServlet"})
public class BecaServlet extends HttpServlet {

    private BecaDAO dao;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        dao = new BecaDAO();
        // Configurar Gson para que formatee las fechas SQL correctamente
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
        // 1. Recibir los parámetros del filtro
        String cvu = request.getParameter("cvu");
        String generacion = request.getParameter("generacion");
        String estatus = request.getParameter("estatus");

        // 2. Normalizar (evitar nulls)
        if (cvu == null) cvu = "";
        if (generacion == null) generacion = "";
        if (estatus == null) estatus = "";
        
        // 3. Consultar al DAO
        List<Beca> lista = dao.buscarPorFiltros(cvu, generacion, estatus);

        // 4. Responder con JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(lista));
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
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
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

}
