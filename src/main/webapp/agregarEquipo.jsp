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

<div class="wrapper">
    <div class="form-wrapper">
        <form id="agregarEquipoForm" action="agregarEquipo.do" method="post" enctype="multipart/form-data" onsubmit="return validarFormulario()">
            <h2>Agregar Equipo</h2>
            <div class="input-group">
                <input type="text" name="nombreEquipo" id="nombreEquipo" class="custom-input" required>
                <label for="nombreEquipo">Selección</label>
            </div>
            <div class="input-group">
                <input type="text" name="director" id="director" class="custom-input" required>
                <label for="director">Director Técnico</label>
            </div>
            <div class="input-group">
                <input type="file" name="logo" id="logo" class="custom-input" required>
                <label for="logo"></label>
            </div>
            <button type="submit" class="btn btn-success">Agregar</button>
        </form>
    </div>