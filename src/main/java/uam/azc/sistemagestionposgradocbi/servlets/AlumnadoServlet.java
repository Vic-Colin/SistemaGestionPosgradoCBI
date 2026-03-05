/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package uam.azc.sistemagestionposgradocbi.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import uam.azc.sistemagestionposgradocbi.dao.AlumnoDAO;
import uam.azc.sistemagestionposgradocbi.modelo.Alumno;

/**
 * Servlet controlador encargado de la gestión
 * del módulo de alumnado.
 *
 * Funciones:
 * - Consulta
 * - Alta
 * - Edición
 * - Eliminación
 * - Obtención de catálogos
 *
 * Arquitectura:
 * MVC (Controller Layer)
 *
 * @author Victor Enrique Colin Olivares
 */
@WebServlet(name = "AlumnadoServlet", urlPatterns = {"/AlumnadoServlet"})
public class AlumnadoServlet extends HttpServlet {

    private AlumnoDAO dao;
    private Gson gson;

    /**
    * Inicializa recursos del servlet.
    *
    * Instancia el DAO y el conversor JSON.
    */
    @Override
    public void init() {
        dao = new AlumnoDAO();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    /**
    * Atiende solicitudes GET del módulo alumnado.
    *
    * Acciones soportadas:
    * - catalogos
    * - buscar alumno
    *
    * @param request solicitud HTTP
    * @param response respuesta HTTP
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        if ("catalogos".equals(accion)) {
            List<uam.azc.sistemagestionposgradocbi.modelo.AreaConcentracion> areas = dao.obtenerAreas();
            List<uam.azc.sistemagestionposgradocbi.modelo.Profesor> profesores = dao.obtenerProfesores();
            
            // Empaquetamos ambas listas en un Map para enviarlas juntas como un solo JSON
            java.util.Map<String, Object> catalogos = new java.util.HashMap<>();
            catalogos.put("areas", areas);
            catalogos.put("profesores", profesores);
            
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(gson.toJson(catalogos));
            return; // Salimos para no ejecutar el resto del doGet
        }else if ("buscar".equals(accion)) {
            // 🔴 NUEVA LÓGICA PARA CARGAR DATOS COMPLETOS AL EDITAR
            String matricula = request.getParameter("matricula");
            Alumno alumno = dao.buscarPorId(matricula);
            
            // Usamos response.reset() para asegurar que no haya basura HTML mezclada con el JSON
            response.reset(); 
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(alumno));
            out.flush();
            return;
        }
        
        // 1. Siempre definir el año para los combos del JSP
        int anioActual = java.time.LocalDate.now().getYear();
        request.setAttribute("anioActual", anioActual);

        
        String matricula = request.getParameter("matricula");
        // Si NO hay matrícula (primera vez que carga la página), solo mostramos el JSP
        if (matricula == null) {
            request.getRequestDispatcher("jsp/Alumnos.jsp").forward(request, response);
            return; // Salir para no ejecutar el JSON
        }
        
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
    * Procesa operaciones de creación,
    * edición y eliminación de alumnos.
    *
    * @param request solicitud HTTP
    * @param response respuesta HTTP
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); 
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        boolean exito = false;

        if ("eliminar".equals(accion)) {
            String matricula = request.getParameter("matricula");
            exito = dao.eliminar(matricula);
        } else {
            Alumno a = new Alumno();
            a.setMatricula(request.getParameter("matricula"));
            a.setCurp(request.getParameter("curp"));
            a.setNombreCompleto(request.getParameter("nombre"));
            a.setCorreoInstitucional(request.getParameter("correoInst"));
            a.setCorreoAlternativo(request.getParameter("correoAlt"));
            a.setTelefono(request.getParameter("telefono"));
            a.setTrimestreIngreso(request.getParameter("trimIngreso"));
            a.setEstatusUam(request.getParameter("estatusUam"));
            
            // CALCULAR AUTOMÁTICAMENTE el trimestre pierde calidad
            String trimIngreso = request.getParameter("trimIngreso");
            String trimPierdeCalidad = dao.calcularTrimestrePierdeCalidad(trimIngreso);
            a.setTrimestrePierdeCalidad(trimPierdeCalidad);
            
            // Nuevos campos Beca
            a.setCvu(request.getParameter("cvu"));
            a.setEstatusBeca(request.getParameter("estatusBeca"));
            a.setRegFechaFinBeca(request.getParameter("fechaFinBeca"));
            a.setRegFechaMax(request.getParameter("fechaMaxBeca"));
            
            
            // Nuevos campos Titulación y Tesis
            a.setNumeroActa(request.getParameter("acta"));

            String fechaTitStr = request.getParameter("fechaTit");
            if (fechaTitStr != null && !fechaTitStr.trim().isEmpty()) {
                a.setFechaTitulacion(java.sql.Date.valueOf(fechaTitStr));
            } else {
                a.setFechaTitulacion(null); // Fuerza el Nulo si el usuario vació la fecha
            }

            a.setTituloTesis(request.getParameter("tituloTesis"));
            if(request.getParameter("areaTesis") != null && !request.getParameter("areaTesis").isEmpty()){
                a.setIdAreaConcentracion(Integer.parseInt(request.getParameter("areaTesis")));
            }
            a.setNumEcoDirector(request.getParameter("directorTesis"));
            a.setNumEcoCodirector(request.getParameter("codirectorTesis"));
            
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
