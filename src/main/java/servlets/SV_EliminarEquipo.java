package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import umariana.mundial.GestionarEquipo;

@WebServlet("/eliminarEquipo.do")
public class SV_EliminarEquipo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID del equipo a eliminar desde los parámetros de la solicitud
        int id = Integer.parseInt(request.getParameter("id"));

        // Eliminar el equipo utilizando el método de la clase GestionarEquipo
        GestionarEquipo.eliminarEquipo(id);

        // Redirigir al usuario a index.jsp con un parámetro en la URL indicando que el equipo se eliminó con éxito
        response.sendRedirect("inicio.jsp?success=delete");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}