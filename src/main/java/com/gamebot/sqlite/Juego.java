package com.gamebot.sqlite;

import java.util.ArrayList;

public class Juego {
    String nombre="";
    int idJuego=0;
    float precioActual= 0f;
    int idtienda=0;
    String idLink="";
    ArrayList<Integer>  idusuariosInteresados;

    public Juego(String nombre, int idJuego, float precioActual, int idtienda, String idLink, ArrayList<Integer> idusuariosInteresados) {
        this.nombre = nombre;
        this.idJuego= idJuego;
        this.precioActual =  precioActual;
        this.idtienda =  idtienda;
        this.idLink = idLink;
        this.idusuariosInteresados = idusuariosInteresados;
    }
    public String obtenerLink(){
        return this.idLink;
    }
     public float obtenerPrecioActual() {
        return this.precioActual;
     }

    public String obtenerNombre(){
        return this.nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }



    public void setIdJuego(int idJuego){
        this.idJuego = idJuego;
    }
    public void setPrecioActual(float precioActual){
        this.precioActual = precioActual;
    }
    public void setIdtienda(int idtienda){
        this.idtienda = idtienda;
    }
    public void setIdLink(String idLink){
        this.idLink = idLink;
    }

    public int obtenerIDJuego(){
        return this.idJuego;
    }
    //Estas dos funciones ayudan a poder usar HashSet con objetos
    // https://stackoverflow.com/questions/27203594/using-hashset-with-a-user-class-employee
    @Override
    public int hashCode(){
        return (int) this.precioActual;
    }
    @Override
    public boolean equals(Object o){
        return (this.precioActual == ((Juego)o).precioActual);
    }


}
