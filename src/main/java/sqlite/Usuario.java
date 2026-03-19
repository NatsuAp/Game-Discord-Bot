package sqlite;

import java.util.ArrayList;

public class Usuario {
    String idUsuario;
    ArrayList<Juego> juegos = new ArrayList<>();
    Usuario(String idUsuario, ArrayList<Juego> juegos) {
        this.idUsuario = idUsuario;
        this.juegos = juegos;
    }
}
