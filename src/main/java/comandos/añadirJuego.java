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

public class añadirJuego {
    public static String jsonJuegos;
    public static boolean revisarSiUsuarioExiste(String idUsuario){
        String command = "Select idUsuario, juegos from datos";
        try {
            Statement st = sqliteDrivers.conn.createStatement();
            ResultSet rs =  st.executeQuery(command);
            while(rs.next()){
                if(rs.getString("IdUsuario").equals(idUsuario)) {
                    jsonJuegos = rs.getString("juegos");
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    //TODO Puede que no necesite esta funcion

//    private static Juego buscarDatosJuego(int idJuego) throws IOException {
//        String request = CurlRequest.curlRequests("https://www.cheapshark.com/api/1.0/games?id=" + idJuego);
//        JsonObject jsonObject = JsonParser.parseString(request).getAsJsonObject();
//        JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
//        String name = infoObject.get("title").getAsString();
//        JsonArray dealsArray = jsonObject.get("deals").getAsJsonArray();
//        JsonObject dealsObject = dealsArray.get(0).getAsJsonObject();
//        int storeId = dealsObject.get("storeID").getAsInt();
//        String dealId = dealsObject.get("dealID").getAsString();
//        float price = dealsObject.get("price").getAsFloat();
//        return new Juego(name, idJuego, price, storeId, dealId);
//
//
//    }

    public static Juego getDatosJuego(String juego) throws IOException, sqlite.apiException {
        String request = CurlRequest.curlRequests("https://www.cheapshark.com/api/1.0/games?title=" + juego);
        IO.println(request);

            if(request.contains("[]")){
                throw new sqlite.apiException("Llamada a Api retorno arreglo vacio");
            }



        JsonObject jsonObject = JsonParser.parseString(request).getAsJsonArray().get(0).getAsJsonObject();
        int idjuego  =jsonObject.get("gameID").getAsInt();
        float precio = jsonObject.get("cheapest").getAsFloat();
        String dealId = jsonObject.get("cheapestDealID").getAsString();
        String nombre =  jsonObject.get("external").getAsString();
        return new Juego(nombre,idjuego,precio,0,dealId);
    }
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
