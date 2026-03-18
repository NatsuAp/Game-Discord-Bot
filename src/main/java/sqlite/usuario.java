package sqlite;

import java.util.ArrayList;

public class usuario {
    String idUsuario;
    ArrayList<Juego> juegos = new ArrayList<>();
    usuario(String idUsuario, ArrayList<Juego> juegos) {
        this.idUsuario = idUsuario;
        this.juegos = juegos;
    }
}
