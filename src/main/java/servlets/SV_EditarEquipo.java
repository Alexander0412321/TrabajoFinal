package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import umariana.mundial.GestionarEquipo;

@WebServlet("/editarEquipo.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class SV_EditarEquipo extends HttpServlet {

    
    private GestionarEquipo gestionar = new GestionarEquipo();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener los parámetros del formulario
        int idEquipo = Integer.parseInt(request.getParameter("idEquipo"));
        String nombreEquipo = request.getParameter("nombreEquipo");
        String director = request.getParameter("director");
        
        // Obtener el logo anterior y nuevo logo (si se proporciona)
        String logoAnterior = request.getParameter("logoAnterior");
        Part logoPart = request.getPart("nuevoLogo");
        
        // Definir la ruta para guardar el nuevo logo si se carga uno nuevo
        String logoPath = null;
        if (logoPart != null && logoPart.getSize() > 0) {
            // Obtener la ruta de la aplicación web
            String rutaAplicacion = request.getServletContext().getRealPath("/");
            
            // Definir la ruta donde se guardará el nuevo logo
            logoPath = logoPart.getSubmittedFileName();
            // Guardar el archivo en la carpeta designada
            logoPart.write(rutaAplicacion + logoPath);
        } else {
            // Si no se proporciona un nuevo logo, usar el logo anterior
            logoPath = logoAnterior;
        }

        try {
            // Llamar al método editarEquipo de GestionarEquipo para editar el equipo
            GestionarEquipo.editarEquipo(idEquipo, nombreEquipo, director, logoPath);
            
            // Redirigir a la página de equipos (o cualquier otra página apropiada)
            response.sendRedirect("inicio.jsp");
        } catch (IOException e) {
            // Manejar la excepción, como mostrar un mensaje de error o redirigir a una página de error
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
