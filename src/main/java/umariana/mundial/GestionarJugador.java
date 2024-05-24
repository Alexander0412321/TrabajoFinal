package umariana.mundial;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;

public class GestionarJugador {
    private static List<Jugador> jugadores = new ArrayList<>();
    private static ServletContext servletContext;

    public static void setServletContext(ServletContext context) {
        servletContext = context;
        leerJugadores();
    }

    public void agregarJugador(String nombreJugador, int edad, double altura, double peso, double salario, String posicion, String imagenPart, int idEquipo) throws IOException, NombreDuplicadoException {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombreJugador().equalsIgnoreCase(nombreJugador)) {
                throw new NombreDuplicadoException("Ya existe un jugador con ese nombre, inténtelo nuevamente.");
            }
        }
        // Generar un nuevo ID único para el jugador
        int idJugador = generarIdUnico(jugadores);

        // Crear un nuevo objeto Jugador
        Jugador nuevoJugador = new Jugador(idJugador, nombreJugador, edad, altura, peso, salario, posicion, imagenPart, idEquipo);

        // Agregar el nuevo jugador a la lista
        jugadores.add(nuevoJugador);

        // Guardar los datos en el archivo jugadores.txt (asegúrate de tener una implementación para esto)
        datosArchivo();
    }

    public static void editarJugador(int idJugador, String nombreJugador, int edad, double altura, double peso, double salario, String posicion, String imagenPath) throws IOException {
        boolean jugadorEncontrado = false; // Bandera para verificar si se encontró el jugador

        for (Jugador jugador : jugadores) {
            if (jugador.getIdJugador() == idJugador) {
                jugadorEncontrado = true; // El jugador ha sido encontrado

                // Actualizar los atributos del jugador
                jugador.setNombreJugador(nombreJugador);
                jugador.setEdad(edad);
                jugador.setAltura(altura);
                jugador.setPeso(peso);
                jugador.setSalario(salario);
                jugador.setPosicion(posicion);

                // Actualizar la imagen si se proporciona un nuevo imagenPath
                if (imagenPath != null && !imagenPath.isEmpty()) {
                    // Eliminar la imagen anterior si existe
                    File imagenFileAnterior = new File(servletContext.getRealPath(jugador.getImagen()));
                    if (imagenFileAnterior.exists()) {
                        imagenFileAnterior.delete();
                    }
                    // Actualizar la imagen
                    jugador.setImagen(imagenPath);
                }

                // Guardar los cambios en el archivo jugadores.txt
                datosArchivo(); // Supongo que esta función guarda los cambios
                break; // Romper el ciclo porque el jugador ya fue encontrado
            }
        }

        if (!jugadorEncontrado) {
            System.out.println("No se encontró un jugador con el ID: " + idJugador);
            throw new IllegalArgumentException("No se encontró un jugador con el ID: " + idJugador);
        }
    }


    public static void eliminarJugador(int idJugador) throws IOException {
        Iterator<Jugador> iterator = jugadores.iterator();
        while (iterator.hasNext()) {
            Jugador jugador = iterator.next();
            if (jugador.getIdJugador() == idJugador) {
                // Eliminar la imagen asociada con el jugador
                File imagenFile = new File(jugador.getImagen());
                if (imagenFile.exists()) {
                    imagenFile.delete();
                }

                // Eliminar el jugador de la lista
                iterator.remove();

                // Guardar los cambios en el archivo jugadores.txt
                datosArchivo();
                break;
            }
        }
    }
    public static List<Jugador> obtenerTodosLosJugadores() {
        return jugadores;
    }

    private static int generarIdUnico(List<Jugador> jugadores) {
        int id = 1;
        for (Jugador jugador : jugadores) {
            if (jugador.getIdJugador() >= id) {
                id = jugador.getIdJugador() + 1;
            }
        }
        return id;
    }
    
    public static Jugador obtenerJugadorPorId(int idJugador) {
        for (Jugador jugador : jugadores) {
            if (jugador.getIdJugador() == idJugador) {
                return jugador;
            }
        }
        return null;
    }

    
    public static List<Jugador> obtenerJugadoresPorEquipo(int idEquipo) {
        List<Jugador> jugadoresPorEquipo = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (jugador.getIdEquipo() == idEquipo) {
                jugadoresPorEquipo.add(jugador);
            }
        }
        return jugadoresPorEquipo;
    }
    
    public static double calcularNominaEquipo(int idEquipo) {
        double nominaTotal = 0.0;
        for (Jugador jugador : jugadores) {
            if (jugador.getIdEquipo() == idEquipo) {
                nominaTotal += jugador.getSalario();
            }
        }
        return nominaTotal;
    }

    private static void datosArchivo() throws IOException {
        String rutaArchivo = servletContext.getRealPath("data/jugadores.txt");
        try (PrintWriter pluma = new PrintWriter(new FileWriter(rutaArchivo, false))) {
            for (Jugador jugador : jugadores) {
                pluma.println(jugador.getIdJugador() + "," +
                              jugador.getNombreJugador() + "," + jugador.getEdad() + "," +                              
                              jugador.getAltura() + "," + jugador.getPeso() + "," +                              
                              jugador.getSalario() + "," + jugador.getPosicion() + "," +                              
                              jugador.getImagen() + "," + jugador.getIdEquipo());
            }
        } catch (IOException e) {
            throw new IOException("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public static void leerJugadores() {
        jugadores.clear();
        if (jugadores == null) {
            jugadores = new ArrayList<>();
        }
        String rutaArchivo = servletContext.getRealPath("data/jugadores.txt");
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    String[] datos = linea.split(",");

                    if (datos.length >= 9) {
                        int idJugador = Integer.parseInt(datos[0]);
                        String nombreJugador = datos[1];
                        int edad = Integer.parseInt(datos[2]);
                        double altura = Double.parseDouble(datos[3]);
                        double peso = Double.parseDouble(datos[4]);
                        double salario = Double.parseDouble(datos[5]);
                        String posicion = datos[6];
                        String imagen = datos[7];
                        int idEquipo = Integer.parseInt(datos[8]);

                        // Crear un objeto Jugador con los datos y establecer la ruta de la imagen
                        Jugador jugador = new Jugador(idJugador, nombreJugador, edad, altura, peso, salario, posicion, imagen, idEquipo);
                        jugador.setImagen(imagen);
                        jugadores.add(jugador);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo jugadores.txt: " + e.getMessage());
            }
        }
    }

    
}
