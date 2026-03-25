package com.gamebot.sqlite;

import java.util.ArrayList;

public class Usuario {
    String idUsuario;
    ArrayList<Juego> juegos;
    Usuario(String idUsuario, ArrayList<Juego> juegos) {
        this.idUsuario = idUsuario;
        this.juegos = juegos;
    }
    public String obteneridUsuario(){
        return this.idUsuario;
    }
    public ArrayList<Juego> obtenerJuegosUsuario () {
        return this.juegos;
    }

}
