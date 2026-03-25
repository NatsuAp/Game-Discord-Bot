package com.gamebot.comandos;

import com.gamebot.sqlite.Juego;
import com.gamebot.sqlite.sqliteDrivers;

import java.sql.SQLException;
import java.util.ArrayList;

public class listarJuegos {
    public static String comandoListarJuegos(String idUsuario) throws SQLException {
        ArrayList<Juego> juegos = sqliteDrivers.obtenerJuegosDelUsuario(idUsuario);
        if(juegos.isEmpty()){
            return "No tienes juegos en tu lista actualmente";
        }
        String botAns = "Tu lista de seguidos: \n";
        int i = 1;
        for (Juego juego : juegos) {
        botAns+= String.valueOf(i) + ". " + juego.obtenerNombre() + "\n";
        }
        return  botAns;
    }
}
