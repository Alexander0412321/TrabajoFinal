<%@page import="umariana.mundial.GestionarEquipo"%>
<%@page import="umariana.mundial.Equipo"%>
<%@include file="lib/header.jsp"%>

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

<div class="equipos-container">
    <div class="col-md-8">
        <div class="card" style="text-align: center;">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5 style="color: white">Listado de Equipos</h5>
            </div>
            <div class="card-body">
                <div class="table-wrapper" style="max-height: 450px; overflow-y: auto;">
                    <table class="table table-hover centered-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Seleccion</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            List<Equipo> equipos = GestionarEquipo.obtenerTodosLosEquipos();
                            if (equipos.isEmpty()) {
                            %>
                                <tr>
                                    <td colspan="5">No se encuentran equipos registrados</td>
                                </tr>
                            <% } else {
                                for (Equipo equipo : equipos) {
                            %>
                                    <tr>
                                        <td><%= equipo.getIdEquipo()%></td>
                                        <td><%= equipo.getNombreEquipo()%></td>
                                        <td class="action">
                                            <a class="btn btn-primary btn-sm me-1" style="color: white;" data-bs-toggle="modal" data-bs-target="#verEquipoModal<%= equipo.getIdEquipo() %>"><i class="fas fa-eye"></i></a>                                            
                                            <a class="btn btn-warning btn-sm me-1" data-bs-toggle="modal" data-bs-target="#editarModal<%= equipo.getIdEquipo()%>"><i class="fas fa-edit text-white"></i></a>
                                            <a href="eliminarEquipo.do?id=<%= equipo.getIdEquipo()%>" onclick="return confirmarEliminar();" class="btn btn-danger btn-sm me-1"><i class="fas fa-trash"></i></a>
                                        </td>
                                    </tr>
                                <% } %>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%
for (Equipo equipo : equipos) { 
%>
<!-- Modal para mostrar la información de la equipo -->
<div class="modal fade" id="verEquipoModal<%= equipo.getIdEquipo() %>" tabindex="-1" role="dialog" aria-labelledby="equipoModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="verModalLabel">Información del Equipo</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div id="equipoDetails">
                    <p><strong>ID:</strong> <%= equipo.getIdEquipo()%></p>
                    <p><strong>Nombre del Equipo:</strong> <%= equipo.getNombreEquipo()%></p>
                    <p><strong>Director:</strong> <%= equipo.getDirector()%></p>
                    <br>
                    <img style="width: 250px"src="<%= equipo.getLogo() %>" class="img-fluid rounded" alt="logoEquipo">
                </div>
                <br>
                <button onclick="calcularNomina(<%= equipo.getIdEquipo() %>)" class="btn btn-primary"data-bs-target="#nominaModal<%= equipo.getIdEquipo() %>" 
                        data-bs-toggle="modal">Nómina</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="editarModal<%= equipo.getIdEquipo() %>" tabindex="-1" role="dialog" aria-labelledby="equipoModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editarModalLabel">Editar Equipo</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div id="equipoDetails">
                    <form id="editarEquipoForm" action="editarEquipo.do" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="idEquipo" value="<%= equipo.getIdEquipo() %>">
                        <div class="mb-3">
                            <label for="nombreEquipo" class="form-label">Nombre del Equipo:</label>
                            <input type="text" name="nombreEquipo" class="form-control" value="<%= equipo.getNombreEquipo() %>">
                        </div>
                        <div class="mb-3">
                            <label for="director" class="form-label">Director:</label>
                            <input type="text" name="director" class="form-control" value="<%= equipo.getDirector() %>">
                        </div>
                        <div class="mb-3">
                            <label for="logo" class="form-label">Logo Actual:</label><br>
                            <img  style="width: 150px" src="<%= equipo.getLogo() %>" class="img-fluid rounded" alt="logoEquipo">
                        </div>
                        <div class="mb-3">
                            <label for="nuevoLogo" class="form-label">Nuevo Logo:</label>
                            <input type="file" name="nuevoLogo" class="form-control">
                        </div>
                        <!-- Puedes agregar más campos aquí si es necesario -->
                        <button type="submit" class="btn btn-primary">Actualizar</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="nominaModal<%= equipo.getIdEquipo() %>" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="nominaLabel<%= equipo.getIdEquipo() %>">Nomina</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
          <h1>Nomina Del Equipo</h1>
        <p id="nominaResultado<%= equipo.getIdEquipo() %>">Nómina total: </p>
      </div>
    </div>
  </div>
</div>                            
<% 
    
}
%>
<script>

    // Función para ver el listado de Jugadores
    function verJugador(idEquipo) {
        window.location.href = 'verJugador.jsp?id=' + idEquipo;
    }
    
    // Función para confirmar la eliminación de un equipo
    function confirmarEliminar() {
        if (confirm("¿Estás seguro de que deseas eliminar este equipo?")) {
            // Eliminar el equipo (código para enviar la solicitud al servidor)
            
            // Mostrar mensaje de éxito
            mostrarMensaje("Equipo eliminado correctamente");
            
            // Devolver true para permitir la acción de eliminación
            return true;
        } else {
            // Si el usuario cancela la eliminación, devolver false para cancelar la acción de eliminación
            return false;
        }
    }
    function calcularNomina(idEquipo) {
            fetch('sv_Nomina?idEquipo=' + idEquipo)
            .then(response => response.json())
            .then(data => {
                document.getElementById('nominaResultado' + idEquipo).innerText = 'Nómina total: ' + data.nomina;
            })
            .catch(error => console.error('Error al calcular la nómina:', error));
        }
</script>

<%@include file="lib/footer.jsp"%>