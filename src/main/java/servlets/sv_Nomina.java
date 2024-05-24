package servlets;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import umariana.mundial.GestionarJugador;

@WebServlet(name = "sv_Nomina", urlPatterns = {"/sv_Nomina"})
public class sv_Nomina extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        GestionarJugador.setServletContext(context);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idEquipoParam = request.getParameter("idEquipo");
        if (idEquipoParam != null) {
            try {
                int idEquipo = Integer.parseInt(idEquipoParam);
                double nomina = GestionarJugador.calcularNominaEquipo(idEquipo);

                // Devolver la nómina en formato JSON
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"nomina\":" + nomina + "}");
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de equipo no válido.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de equipo no proporcionado.");
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
