package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import umariana.mundial.GestionarJugador;

@WebServlet("/eliminarJugador.do")
public class sv_EliminarJugador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejar la posible excepción de NumberFormatException
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            // Eliminar el jugador utilizando el método de la clase GestionarJugador
            GestionarJugador.eliminarJugador(id);

            // Redirigir al usuario a verJugador.jsp con un parámetro indicando éxito
            response.sendRedirect("verJugador.jsp?success=delete");
        } catch (NumberFormatException e) {
            // Manejar error de formato de número inválido en el parámetro "id"
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID proporcionado no es válido.");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No se necesita proceso adicional aquí si solo quieres hacer el manejo en `doGet`.
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método POST no permitido.");
    }
}
