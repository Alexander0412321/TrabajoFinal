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
import umariana.mundial.GestionarJugador;

@WebServlet("/agregarJugador.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class sv_AgregarJugador extends HttpServlet {

    private GestionarJugador gestionarJugador;

    @Override
    public void init() throws ServletException {
        super.init();
        gestionarJugador = new GestionarJugador();
        gestionarJugador.setServletContext(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario
        String nombreJugador = request.getParameter("nombreJugador");
        int edad = Integer.parseInt(request.getParameter("edad"));
        double altura = Double.parseDouble(request.getParameter("altura"));
        double peso = Double.parseDouble(request.getParameter("peso"));
        double salario = Double.parseDouble(request.getParameter("salario"));
        String posicion = request.getParameter("posicion");

        // Obtener la parte de la imagen
        Part imagenPart = request.getPart("imagen");
        String imagenPath = null;

        // Si la imagen se ha subido
        if (imagenPart != null && imagenPart.getSize() > 0) {
            // Obtener el nombre del archivo de imagen
            String imagenFilename = imagenPart.getSubmittedFileName();

            // Directorio donde se guardará la imagen
            String imagenDir = getServletContext().getRealPath("");
            File imagenFile = new File(imagenDir, imagenFilename);

            // Guardar la imagen en el servidor
            imagenPart.write(imagenFile.getAbsolutePath());

            // Guardar la ruta de la imagen
            imagenPath = imagenFilename;
        }

        // Obtener el ID del equipo seleccionado del formulario
        String idEquipoParam = request.getParameter("idEquipo");
        int idEquipo = 0; // Valor por defecto
        if (idEquipoParam != null && !idEquipoParam.isEmpty()) {
            idEquipo = Integer.parseInt(idEquipoParam);
        }

        try {
            // Agregar el jugador utilizando GestionarJugador
            gestionarJugador.agregarJugador(nombreJugador, edad, altura, peso, salario, posicion, imagenPath, idEquipo);

            // Redirigir a una página de confirmación
            response.sendRedirect("verJugador.jsp");
        } catch (Exception e) {
            // Manejo de errores
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }
}
