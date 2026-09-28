package com.gamebot;

import com.gamebot.api.CurlRequest;
import com.gamebot.comandos.eliminarJuego;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gamebot.sqlite.Juego;
import com.gamebot.sqlite.Usuario;
import com.gamebot.sqlite.sqliteDrivers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class programacionAlertas {
    private static String obtenerStringInfoJuego(Juego juego){
        String ans = "";
        ans+= juego.obtenerNombre();
        ans+= "\n Precio Actual: ";
        ans+= Float.toString(juego.obtenerPrecioActual());
        ans+= "\n " + "https://www.cheapshark.com/redirect?dealID=" +juego.obtenerLink();
        return ans;
    }

    //En la llamada a la api, la api filtra los id repetidos y no los pone en la respuesta
    private static void verificarPrecio() throws SQLException {
        System.out.println("Se esta verificando el precio\n");

        ArrayList<Usuario> usuarios= sqliteDrivers.obtenerUsuarios();
        HashSet<Integer> idJuegos = new HashSet<>();
        //HashSet<Juego> juegos= new HashSet<>();

        for (Usuario usuario : usuarios){
            for(Juego juego : usuario.obtenerJuegosUsuario()){
                idJuegos.add(juego.obtenerIDJuego());
                //juegos.add(juego);
            }
        }





        String rqst = "https://www.cheapshark.com/api/1.0/games?ids=";
        //TODO: Quitar esto cuando termine de debugear
        for(Integer idJuego : idJuegos){
            rqst+=idJuego;
            rqst+=",";
        }
        rqst=rqst.substring(0,rqst.length()-1);
        rqst+= "&format=array";
        //TODO: Para testear en algun caso
        String apiCallAns = "[]";
       //String apiCallAns = "[{\"info\":{\"gameID\":\"285827\",\"title\":\"Hades II\",\"steamAppID\":\"1145350\",\"thumb\":\"https:\\/\\/shared.fastly.steamstatic.com\\/store_item_assets\\/steam\\/apps\\/1145350\\/10c9138570a8d7ac9144f601ab0f2ccbc820337e\\/capsule_231x87.jpg?t=1765831644\"},\"cheapestPriceEver\":{\"price\":\"22.49\",\"date\":1766082385},\"deals\":[{\"storeID\":\"1\",\"dealID\":\"bvpO%2FWkluJf8hKbYUbwauDKGpNUvoJOSBDiJNUP2Jf0%3D\",\"price\":\"22.49\",\"retailPrice\":\"29.99\",\"savings\":\"25.008336\"},{\"storeID\":\"25\",\"dealID\":\"Lf61NZp9V40aGLTj97IaB4GJcSaiyzddG3IydU7h4%2BM%3D\",\"price\":\"29.99\",\"retailPrice\":\"29.99\",\"savings\":\"0.000000\"}]},{\"info\":{\"gameID\":\"307927\",\"title\":\"Hollow Knight: Silksong\",\"steamAppID\":\"1030300\",\"thumb\":\"https:\\/\\/shared.fastly.steamstatic.com\\/store_item_assets\\/steam\\/apps\\/1030300\\/b73ec03fbdb9e21e3c59eb7e59966949d0c17f29\\/capsule_231x87.jpg?t=1764916587\"},\"cheapestPriceEver\":{\"price\":\"15.99\",\"date\":1765463829},\"deals\":[{\"storeID\":\"1\",\"dealID\":\"Olae4m2LeccgcOv02vUbGxnba1q6Md509R6CDonwrFY%3D\",\"price\":\"15.99\",\"retailPrice\":\"19.99\",\"savings\":\"20.010005\"},{\"storeID\":\"7\",\"dealID\":\"Kvgekzg%2FRSmj41RBQSVGJEaM2g5H%2Bli%2BnWUswmO8kr8%3D\",\"price\":\"19.99\",\"retailPrice\":\"19.99\",\"savings\":\"0.000000\"},{\"storeID\":\"3\",\"dealID\":\"TMtPEzN%2FJDr5Eju4cWcG94bTT96JRetWb2wotp5lJ28%3D\",\"price\":\"19.99\",\"retailPrice\":\"19.99\",\"savings\":\"0.000000\"},{\"storeID\":\"11\",\"dealID\":\"GwagnTQGmwZxNU%2F36U7hmTgXs0DaoBplHH0LU1ZoojM%3D\",\"price\":\"19.99\",\"retailPrice\":\"19.99\",\"savings\":\"0.000000\"}]}]";
        try{
            apiCallAns = CurlRequest.curlRequests(rqst);
        }catch(IOException e){
            throw new SQLException(e.getMessage());
        }


        ArrayList<Juego> juegosJson = new ArrayList<>();
        JsonArray deals = JsonParser.parseString(apiCallAns).getAsJsonArray();

        int id = -1;
        String nombre = "";
        int IdTienda =-1;
        String IdLink = "";
        float precio = -1f;

        for(JsonElement element : deals){

            JsonObject ob = element.getAsJsonObject();
            JsonObject infoObj = ob.getAsJsonObject("info");
            id = infoObj.get("gameID").getAsInt();
            //juego.setIdJuego(id);
            nombre = infoObj.get("title").getAsString();
            //juego.setNombre(infoObj.get("title").getAsString());
            JsonObject deal = ob.getAsJsonArray("deals").get(0).getAsJsonObject();
            IdTienda = deal.get("storeID").getAsInt();
            //juego.setIdtienda(deal.get("storeID").getAsInt());
            IdLink = deal.get("dealID").getAsString();
            //juego.setIdLink(deal.get("dealID").getAsString());
            precio =  deal.get("price").getAsFloat();
            //juego.setPrecioActual(deal.get("price").getAsFloat());
            juegosJson.add(new Juego(nombre,id, precio, IdTienda, IdLink , new ArrayList<>()));
        }
        //ResultSet rs = st.executeQuery("SELECT * FROM datos");
        for(Usuario usuario : usuarios){
            for(Juego juegoUsuario : usuario.obtenerJuegosUsuario()){
                for(Juego juegoLista: juegosJson){
                    if(juegoUsuario.obtenerIDJuego() == juegoLista.obtenerIDJuego() &&
                        juegoUsuario.obtenerPrecioActual() > juegoLista.obtenerPrecioActual()) {
                        System.out.println("Se elimino un juego");

                        eliminarJuego.comandoEliminarJuegoPorNombre(usuario.obteneridUsuario(), juegoUsuario.obtenerNombre());
                        crearJDA.enviarMensajeUsuario(usuario.obteneridUsuario(),"Un juego de tu lista bajo de precio!\n" + obtenerStringInfoJuego(juegoLista));
                    }
                }
            }
        }






    }
    public static void iniciarAlertaPrecios(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable notificationTask;

                notificationTask = () -> {
                    try {
                        verificarPrecio();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                };





        scheduler.scheduleAtFixedRate(notificationTask, 0, 12, TimeUnit.HOURS);

    }
}
