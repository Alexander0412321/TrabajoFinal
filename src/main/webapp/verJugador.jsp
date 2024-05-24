<%@page import="java.io.IOException"%>
<%@page import="umariana.mundial.Equipo"%>
<%@page import="umariana.mundial.GestionarEquipo"%>
<%@page import="umariana.mundial.Jugador"%>
<%@page import="umariana.mundial.GestionarJugador"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="lib/header.jsp" %>

<!-- Navbar con Bootstrap -->
<nav>
    <div class="navbar-container">
        <div class="logo-text">
            Mundial 2026
        </div>
        <ul class="navbar">
            <li><a href="index.jsp">Inicio</a></li>
            <li><a href="inicio.jsp">Equipos</a></li>
            <li><a href="agregarEquipo.jsp">Agregar equipo</a></li>
            <li><a href="verJugador.jsp">Jugadores</a></li>
        </ul>
    </div>
</nav>

<div class="jugador-container">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card shadow">
                <form method="post" action="verJugador.jsp" class="form-inline">
                    <select name="idEquipo" id="idEquipo" class="form-select" style="width: 500px" required>
                        <%
                        // Listar los equipos en la lista desplegable
                        List<Equipo> equipos = GestionarEquipo.obtenerTodosLosEquipos();
                        if (!equipos.isEmpty()) {
                            for (Equipo equipo : equipos) {
                        %>
                            <option value="<%= equipo.getIdEquipo() %>"><%= equipo.getNombreEquipo() %></option>
                        <%
                            }
                        }
                        %>
                    </select>
                <button type="submit">Buscar</button>
                </form>
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="" style="color: black">Jugadores</h5> 
                    <a class="btn btn-info btn-xxl" data-bs-toggle="modal" data-bs-target="#nuevoJugaModal">
                        <i>Agregar Jugador</i>
                    </a>
                </div>
                <div class="card-body">
                    <div class="table-scroll" style="max-width: 100%; overflow-x: auto;">                        
                        <table class="table table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Edad</th>
                                    <th>Altura</th>
                                    <th>Peso</th>
                                    <th>Salario</th>
                                    <th>Posición</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <%
                            // Obtener el ID del equipo ingresado por el usuario
                            int idEquipo = 0;
                            if (request.getParameter("idEquipo") != null) {
                                idEquipo = Integer.parseInt(request.getParameter("idEquipo"));
                            }
                            // Obtener la lista de jugadores por el ID del equipo
                            List<Jugador> jugadoresPorEquipo = null;
                            if (idEquipo != 0) {
                                jugadoresPorEquipo = GestionarJugador.obtenerJugadoresPorEquipo(idEquipo);
                            }
                            // Mostrar la lista de jugadores del equipo
                            if (jugadoresPorEquipo != null && !jugadoresPorEquipo.isEmpty()) {

                                for (Jugador jugador : jugadoresPorEquipo) {
                            %>
                            <tr>
                                <td><%= jugador.getIdJugador() %></td>
                                <td><%= jugador.getNombreJugador() %></td>
                                <td><%= jugador.getEdad() %></td>
                                <td><%= jugador.getAltura() %></td>
                                <td><%= jugador.getPeso() %></td>
                                <td><%= jugador.getSalario() %></td>
                                <td><%= jugador.getPosicion() %></td>
                                <td>
                                    <a class="btn btn-primary btn-sm me-1" style="color: white;" data-bs-toggle="modal" data-bs-target="#verJugaModal<%= jugador.getIdJugador()%>"><i class="fas fa-eye"></i></a>                                            
                                    <a class="btn btn-warning btn-sm me-1" data-bs-toggle="modal" data-bs-target="#EditarJugaModal<%=jugador.getIdJugador()%>"><i class="fas fa-edit text-white"></i></a>
                                    <a href="eliminarJugador.do" onclick="return confirmarEliminar();" class="btn btn-danger btn-sm me-1"><i class="fas fa-trash"></i></a>
                                </td>
                            </tr>
                            <%
                                }
                               } else {
                                       %>
                            <tr>
                                <td colspan="5">Ningun Jugador</td>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
                                                       
<!-- Modal -->
<div class="modal fade" id="nuevoJugaModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="nuevoJugaModalLabel">Agregar Jugador</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="agregarJugadorForm" action="agregarJugador.do" method="post" enctype="multipart/form-data">
                    <label for="idEquipo">Equipo:</label>
                    <select name="idEquipo" id="idEquipo" class="form-select" style="width: 400px" required>
                        <option value="" disabled selected>Seleccione un equipo</option>
                        <%
                        if (!equipos.isEmpty()) {
                            for (Equipo equipo : equipos) {
                        %>
                            <option value="<%= equipo.getIdEquipo() %>"><%= equipo.getNombreEquipo() %></option>
                        <%
                            }
                        }
                        %>
                    </select>
                    <div class="mb-3">
                        <label for="nombreJugador" class="form-label">Nombre:</label>
                        <input type="text" name="nombreJugador" id="nombreJugador" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="edad" class="form-label">Edad:</label>
                        <input type="text" name="edad" id="edad" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="altura" class="form-label">Altura:</label>
                        <input type="text" name="altura" id="altura" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="peso" class="form-label">Peso:</label>
                        <input type="text" name="peso" id="peso" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="salario" class="form-label">Salario:</label>
                        <input type="text" name="salario" id="salario" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="posicion" class="form-label">Posicion</label>
                        <input type="text" name="posicion" id="posicion" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="imagen" class="form-label">Imagen</label>
                        <input type="file" name="imagen" id="imagen" class="form-control" required>
                    </div>                    
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary" id="liveToastBtn">Guardar</button>
                </form>
            </div>
        </div>
    </div>
</div>

<% 
if(!equipos.isEmpty()) {
for(Jugador jugador : jugadoresPorEquipo) {
%>  
<div class="modal fade" id="verJugaModal<%=jugador.getIdJugador()%>" tabindex="-1" aria-labelledby="verJugaModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="verJugaModalLabel">Imagen Jugador</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <img src="<%= jugador.getImagen()%>" alt="imagen" style="width: 200px"/>
            </div>
        </div>
    </div>
</div>
<%
    }
}
%>

<script>
    // Función para confirmar la eliminación de un equipo
    function confirmarEliminar() {
        if (confirm("¿Estás seguro de que deseas eliminar este jugador?")) {
            // Eliminar el equipo (código para enviar la solicitud al servidor)
            
            // Mostrar mensaje de éxito
            mostrarMensaje("Jugador eliminado correctamente");
            
            // Devolver true para permitir la acción de eliminación
            return true;
        } else {
            // Si el usuario cancela la eliminación, devolver false para cancelar la acción de eliminación
            return false;
        }
    }
        
</script>

                    
