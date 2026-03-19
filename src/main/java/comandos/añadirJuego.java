package comandos;

import api.CurlRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sqlite.Juego;
import sqlite.sqliteDrivers;
import sqlite.userException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static comandos.helpersComandos.getDatosJuego;
import static comandos.helpersComandos.revisarSiUsuarioExiste;

public class añadirJuego {





    public static String comandoAñadirJuego(String str, String IDUsuario) throws IOException, SQLException {
        Juego jg;
        try{
            jg = getDatosJuego(str);
        }catch (sqlite.apiException e){
            return "Has realizado demasiadas peticiones recientemente, espera un poco y vuelve a intentarlo\n";
        }
        try {
            if (revisarSiUsuarioExiste(IDUsuario)) sqliteDrivers.añadirJuegoATabla(jg, IDUsuario);
            else sqliteDrivers.añadirNuevoUsuario(IDUsuario, jg);
        } catch (userException e) {
            return e.getMessage();
        }
                return "Juego agregado a tu lista de seguimiento.\n" +
                "Recibirás alertas cuando baje de precio.";
    }

}
