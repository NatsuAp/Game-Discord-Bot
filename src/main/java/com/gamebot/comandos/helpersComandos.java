package com.gamebot.comandos;

import com.gamebot.api.CurlRequest;
import com.gamebot.sqlite.apiException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gamebot.sqlite.Juego;
import com.gamebot.sqlite.sqliteDrivers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class helpersComandos {
    //public static String jsonJuegos;
    public static boolean revisarSiUsuarioExiste(String idUsuario){
        String command = "Select idUsuario, juegos from datos";
        try {
            Statement st = sqliteDrivers.conn.createStatement();
            ResultSet rs =  st.executeQuery(command);
            while(rs.next()){
                if(rs.getString("IdUsuario").equals(idUsuario)) {
                    //jsonJuegos = rs.getString("juegos");
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static Juego getDatosJuego(String juego) throws IOException, apiException {
        String request = CurlRequest.curlRequests("https://www.cheapshark.com/api/1.0/games?title=" + juego);
        System.out.println(request);

        if(request.contains("[]")){
            throw new apiException("Llamada a Api retorno arreglo vacio");
        }



        JsonObject jsonObject = JsonParser.parseString(request).getAsJsonArray().get(0).getAsJsonObject();
        int idjuego  =jsonObject.get("gameID").getAsInt();
        float precio = jsonObject.get("cheapest").getAsFloat();
        String dealId = jsonObject.get("cheapestDealID").getAsString();
        String nombre =  jsonObject.get("external").getAsString();
        return new Juego(nombre,idjuego,precio,0,dealId, new ArrayList<>());
    }

}
