package sqlite;

public class Juego {
    String nombre="";
    int idJuego=0;
    float precioActual= 0f;
    int idtienda=0;
    String idLink="";

    public Juego(String nombre, int idJuego, float precioActual, int idtienda, String idLink) {
        this.nombre = nombre;
        this.idJuego= idJuego;
        this.precioActual =  precioActual;
        this.idtienda =  idtienda;
        this.idLink = idLink;
    }
    public String getNombre(){
        return this.nombre;
    }



}
