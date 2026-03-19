package comandos;

import sqlite.Juego;

import java.sql.SQLException;
import java.util.ArrayList;

public class listarJuegos {
    public static String comandoListarJuegos(String idUsuario) throws SQLException {
        ArrayList<Juego> juegos = sqlite.sqliteDrivers.obtenerJuegosDelUsuario(idUsuario);
        String botAns = "Tu lista de seguidos: \n";
        int i = 1;
        for (Juego juego : juegos) {
        botAns+= String.valueOf(i) + ". " + juego.getNombre() + "\n";
        }
        return  botAns;
    }
}
