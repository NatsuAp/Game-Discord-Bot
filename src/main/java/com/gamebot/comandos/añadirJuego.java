package com.gamebot.comandos;

import com.gamebot.sqlite.Juego;
import com.gamebot.sqlite.apiException;
import com.gamebot.sqlite.sqliteDrivers;
import com.gamebot.sqlite.userException;

import java.io.IOException;
import java.sql.SQLException;

import static com.gamebot.comandos.helpersComandos.getDatosJuego;
import static com.gamebot.comandos.helpersComandos.revisarSiUsuarioExiste;

public class añadirJuego {





    public static String comandoAñadirJuego(String str, String IDUsuario) throws IOException, SQLException {
        Juego jg;
        try{
            jg = getDatosJuego(str);
        }catch (apiException e){
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
