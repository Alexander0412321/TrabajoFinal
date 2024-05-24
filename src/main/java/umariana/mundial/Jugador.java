package umariana.mundial;

public class Jugador {
    private int idJugador;
    private String nombreJugador;
    private int edad;
    private double altura;
    private double peso;
    private double salario;
    private String posicion;
    private String imagen;
    private int idEquipo;

    public Jugador(int idJugador, String nombreJugador, int edad, double altura, double peso, double salario, String posicion, String imagen, int idEquipo) {
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
        this.edad = edad;
        this.altura = altura;
        this.peso = peso;
        this.salario = salario;
        this.posicion = posicion;
        this.imagen = imagen;
        this.idEquipo = idEquipo;
    }

    // Getters y setters para todos los atributos
    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
