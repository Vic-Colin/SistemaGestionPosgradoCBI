/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package uam.azc.sistemagestionposgradocbi.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import uam.azc.sistemagestionposgradocbi.dao.AlumnoDAO;
import uam.azc.sistemagestionposgradocbi.modelo.Alumno;

/**
 *
 * @author CASH
 */
@WebServlet(name = "AlumnadoServlet", urlPatterns = {"/AlumnadoServlet"})
public class AlumnadoServlet extends HttpServlet {

    private AlumnoDAO dao;
    private Gson gson;

    @Override
    public void init() {
        dao = new AlumnoDAO();
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
        String matricula = request.getParameter("matricula");
        String trimIngreso = request.getParameter("trimIngreso");
        String trimPierdeCalidad = request.getParameter("trimPierdeCalidad");
        String anioTitulacion = request.getParameter("anioTitulacion");
        String estatusUam = request.getParameter("estatusUam");

        List<Alumno> lista = dao.buscarPorFiltros(
            matricula == null ? "" : matricula,
            trimIngreso == null ? "" : trimIngreso,
            trimPierdeCalidad == null ? "" : trimPierdeCalidad,
            anioTitulacion == null ? "" : anioTitulacion,
            estatusUam == null ? "" : estatusUam
        );

        response.setContentType("application/json;charset=UTF-8");
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
        
        String accion = request.getParameter("accion");
        boolean exito = false;

        if ("eliminar".equals(accion)) {
            String matricula = request.getParameter("matricula");
            exito = dao.eliminar(matricula);
        } else {
            Alumno a = new Alumno();
            a.setMatricula(request.getParameter("matricula"));
            a.setNombreCompleto(request.getParameter("nombre"));
            a.setCorreoInstitucional(request.getParameter("correoInst"));
            a.setCorreoAlternativo(request.getParameter("correoAlt"));
            a.setTelefono(request.getParameter("telefono"));
            a.setTrimestreIngreso(request.getParameter("trimIngreso"));
            a.setTrimestrePierdeCalidad(request.getParameter("trimPierde"));

            if ("crear".equals(accion)) {
                exito = dao.insertar(a);
            } else if ("editar".equals(accion)) {
                exito = dao.actualizar(a);
            }
        }

        response.setContentType("text/plain");
        response.getWriter().write(exito ? "OK" : "ERROR");
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
