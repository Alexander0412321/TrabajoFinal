package servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import umariana.mundial.GestionarEquipo;
import umariana.mundial.NombreDuplicadoException;

@WebServlet("/agregarEquipo.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class SV_AgregarEquipo extends HttpServlet {

    private GestionarEquipo gestionar;

    @Override
    public void init() throws ServletException {
        super.init();
        gestionar = new GestionarEquipo();
        gestionar.setServletContext(getServletContext());
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros de la solicitud
        String nombreEquipo = request.getParameter("nombreEquipo");
        String director = request.getParameter("director");
        
        // Obtener el archivo de logo
        Part logoPart = request.getPart("logo");
        if (logoPart == null || logoPart.getSize() == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("El logo es requerido.");
            return;
        }

        // Guardar el archivo de logo
        String logoDir = getServletContext().getRealPath("");
        String logoFilename = logoPart.getSubmittedFileName();
        File logoFile = new File(logoDir, logoFilename);
        logoPart.write(logoFile.getAbsolutePath());

        // Generar la ruta relativa del logo
        String logoPath = logoFilename;

        try {
            // Llamar a agregarEquipo
            gestionar.agregarEquipo(nombreEquipo, director, logoPath);
            response.sendRedirect("inicio.jsp");
        } catch (NombreDuplicadoException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Error: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }
}
