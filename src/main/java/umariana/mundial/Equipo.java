package umariana.mundial;

import java.util.ArrayList;
import java.util.List;

public class Equipo {

    // Lista estática para almacenar todas las motos creadas
    public static List<Equipo> equipos = new ArrayList<>();

    //Atributos
    private int idEquipo;
    private String nombreEquipo;
    private String director;
    private String logo;
    
    //Constructores
    public Equipo() {
    }

    public Equipo(int idEquipo, String nombreEquipo, String director, String logo) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.director = director;
        this.logo = logo;
    }
    
    //Getters & Setters
    public int getIdEquipo() {
        return idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public String getDirector() {
        return director;
    }

    public String getLogo() {
        return logo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    // Método estático para agregar una nueva moto a la lista de motos
    public static int agregarEquipo(Equipo equipo) {
        int id = 0;
        if (equipos.isEmpty()) {
            id = 1;
        } else {
            id = equipos.get(equipos.size() - 1).getIdEquipo()+ 1;
        }
        equipo.setIdEquipo(id);
        equipos.add(equipo);
        return id;
    }

}