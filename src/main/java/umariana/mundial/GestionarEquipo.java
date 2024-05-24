package umariana.mundial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;

public class GestionarEquipo {
    private static List<Equipo> equipos = new ArrayList<>(); // Inicialización de la lista de equipos
    private static ServletContext servletContext; // Contexto del servlet

    // Establece el contexto del servlet
    public static void setServletContext(ServletContext context) {
        servletContext = context;
        leerEquipos(); // Inicializa la lista de equipos desde el archivo
    }

    // Método para agregar un equipo nuevo
    public static void agregarEquipo(String nombreEquipo, String director, String logoPath) throws NombreDuplicadoException, IOException {
        // Verificar si ya existe un equipo con el mismo nombre
        for (Equipo equipo : equipos) {
            if (equipo.getNombreEquipo().equalsIgnoreCase(nombreEquipo)) {
                throw new NombreDuplicadoException("Ya existe un equipo con ese nombre, inténtelo nuevamente.");
            }
        }

        // Generar un ID único para el nuevo equipo
        int idEquipo = generarIdUnico(equipos);

        // Crear un objeto Equipo con los datos proporcionados
        Equipo miEquipo = new Equipo(idEquipo, nombreEquipo, director, logoPath);

        equipos.add(miEquipo);

        // Guardar el nuevo equipo en el archivo equipo.txt
        datosArchivo();
    }

    // Método para editar un equipo existente
    public static void editarEquipo(int idEquipo, String nombreEquipo, String director, String logoPath) throws IOException {
        boolean equipoEncontrado = false;
        for (Equipo equipo : equipos) {
            // Mensaje de registro para verificar los ID
            System.out.println("Verificando equipo con ID: " + equipo.getIdEquipo());

            if (equipo.getIdEquipo() == idEquipo) {
                equipoEncontrado = true;
                // Actualizar nombre y director
                equipo.setNombreEquipo(nombreEquipo);
                equipo.setDirector(director);

                // Actualizar el logo si se proporciona un nuevo logoPath
                if (logoPath != null && !logoPath.isEmpty()) {
                    // Eliminar el archivo de logo anterior, si existe
                    File logoFileAnterior = new File(servletContext.getRealPath(equipo.getLogo()));
                    if (logoFileAnterior.exists()) {
                        logoFileAnterior.delete();
                    }
                    // Actualizar el logo
                    equipo.setLogo(logoPath);
                }

                break;
            }
        }

        if (!equipoEncontrado) {
            System.out.println("No se encontró un equipo con el ID: " + idEquipo);
            throw new IllegalArgumentException("No se encontró un equipo con el ID: " + idEquipo);
        }

        // Guardar los cambios en el archivo equipo.txt
        datosArchivo();
    }

    public static void eliminarEquipo(int idEquipo) throws IOException {
        for (Iterator<Equipo> iterator = equipos.iterator(); iterator.hasNext();) {
            Equipo equipo = iterator.next();
            if (equipo.getIdEquipo() == idEquipo) {
                // Eliminar el archivo de logo asociado
                File logoFile = new File(servletContext.getRealPath(equipo.getLogo()));
                if (logoFile.exists()) {
                    logoFile.delete();
                }

                // Eliminar el equipo de la lista
                iterator.remove();
                break;
            }
        }

        // Guardar los cambios en el archivo equipo.txt
        datosArchivo();
    }

    // Método para guardar todos los equipos en el archivo equipo.txt
    private static void datosArchivo() throws IOException {
        String rutaArchivo = servletContext.getRealPath("data/equipo.txt");
        try (PrintWriter pluma = new PrintWriter(new FileWriter(rutaArchivo, false))) {
            for (Equipo equipo : equipos) {
                pluma.println(equipo.getIdEquipo() + "," + equipo.getNombreEquipo() + "," + equipo.getDirector() + "," + equipo.getLogo());
            }
        } catch (IOException e) {
            throw new IOException("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // Método para generar un ID único para un equipo
    private static int generarIdUnico(List<Equipo> equipos) {
        int id = 1;
        for (Equipo equipo : equipos) {
            if (equipo.getIdEquipo() >= id) {
                id = equipo.getIdEquipo() + 1;
            }
        }
        return id;
    }

    // Método para inicializar la lista de equipos
    public static void leerEquipos() {
    equipos.clear();
        // Mensaje de registro para indicar que estamos leyendo los equipos
        System.out.println("Leyendo datos de los equipos desde el archivo.");

        // Ruta completa al archivo equipo.txt
        String rutaArchivo = servletContext.getRealPath("data/equipo.txt");

        // Verificar si el archivo existe
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    // Dividir la línea por comas para obtener los valores de cada atributo
                    String[] datos = linea.split(",");

                    // Asegurarse de que haya suficientes datos en la línea
                    if (datos.length >= 4) {
                        int idEquipo = Integer.parseInt(datos[0]);
                        String nombreEquipo = datos[1];
                        String director = datos[2];
                        String logo = datos[3];

                        // Crear una instancia de Equipo con los datos extraídos
                        Equipo equipo = new Equipo(idEquipo, nombreEquipo, director, logo);

                        // Agregar el equipo a la lista de equipos
                        equipos.add(equipo);
                    }
                }
                // Mensaje de registro para indicar la cantidad de equipos leídos
                System.out.println("Equipos leídos: " + equipos.size());
            } catch (IOException e) {
                System.err.println("Error al leer el archivo equipo.txt: " + e.getMessage());
            }
        } else {
            System.out.println("Archivo de equipos no encontrado en la ruta: " + rutaArchivo);
        }
    }

    public static List<Equipo> obtenerTodosLosEquipos() {
    return equipos;
    }

}
