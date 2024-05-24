package umariana.mundial;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Obtener el contexto del servlet
        ServletContext servletContext = sce.getServletContext();
        
        // Configurar el contexto en GestionarEquipo
        GestionarEquipo.setServletContext(servletContext);
        GestionarJugador.setServletContext(servletContext);
        
        // Cargar los equipos desde el archivo .txt
        GestionarEquipo.leerEquipos();
        GestionarJugador.leerJugadores();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Puedes dejar este método vacío si no necesitas hacer algo cuando el contexto se destruye.
    }
}

