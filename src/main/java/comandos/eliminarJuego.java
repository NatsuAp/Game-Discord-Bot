package comandos;

import sqlite.Juego;

import java.sql.SQLException;
import java.util.ArrayList;

public class eliminarJuego {
    public static String comandoEliminarJuegoPorIndice(String idUsuario, String str) throws SQLException {
         ArrayList<Juego> juegos = sqlite.sqliteDrivers.obtenerJuegosDelUsuario(idUsuario);
         if(juegos.isEmpty()){
             return "Actualmente no tienes juegos en tu lista\n";
         }
        int id;
         try{
            id = Integer.parseInt(str.trim());
         }catch(Exception e){
             return "Entrada invalida\n";
         }
         id -=1;
         if(id < 0 || id > juegos.size()){
             return "Entrada invalida\n";
         }
        juegos.remove(id);
        sqlite.sqliteDrivers.actualizarJuegosTabla(juegos, idUsuario);

        return "Juego eliminado exitosamente\n";
    }
    public static String comandoEliminarJuegoPorNombre(String idUsuario, String str) throws SQLException {
        ArrayList<Juego> juegos = sqlite.sqliteDrivers.obtenerJuegosDelUsuario(idUsuario);
        if((juegos.isEmpty())) {
            return "Actualmente no tienes juegos en tu lista\n";
        }
        for(int i = 0; i< juegos.size(); i++){
            if(juegos.get(i).getNombre().equals(str)){
                juegos.remove(i);
                sqlite.sqliteDrivers.actualizarJuegosTabla(juegos, idUsuario);
                return "Juego eliminado exitosamente\n";
            }
        }
        return "No se encontro el juego\n";
    }
}
