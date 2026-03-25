package alertas;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.JDA;
import sqlite.Juego;
import sqlite.Usuario;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.c
import static sqlite.sqliteDrivers.conn;

public class programacionAlertas {
    private static void enviarMensajeUsuario(JDA jda,String IdUsuario, String msg){
        jda.getUserById(IdUsuario)
                .openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(msg))
                .queue();

    }
    //En la llamada a la api, la api filtra los id repetidos y no los pone en la respuesta
    private static void verificarPrecio() throws SQLException {
        ArrayList<Usuario> usuarios= sqlite.sqliteDrivers.obtenerUsuarios();
        HashSet<Integer> idJuegos = new HashSet<>();
        HashSet<Juego> juegos= new HashSet<>();

        for (Usuario usuario : usuarios){
            for(Juego juego : usuario.obtenerJuegosUsuario()){
                idJuegos.add(juego.obtenerIDJuego());
                juegos.add(juego);
            }
        }



        ArrayList<Juego> juegosList = new ArrayList<>(juegos);


        String rqst = "https://www.cheapshark.com/api/1.0/games?ids=";
        for(Integer idJuego : idJuegos){
            rqst+=idJuego;
            rqst+=",";
        }
        rqst=rqst.substring(0,rqst.length()-1);
        rqst+= "&format=array";
        String apiCallAns = "[]";
        try{
            apiCallAns = api.CurlRequest.curlRequests(rqst);
        }catch(IOException e){
            throw new SQLException(e.getMessage());
        }
        JsonArray deals = JsonParser.parseString(apiCallAns).getAsJsonArray();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM datos");
        String idUsuario="";
        String jsonJuegos="";

        while(rs.next()){

        }




    }
    public static void iniciarAlertaPrecios(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            Runnable notificationTask = () -> IO.println("Verificando precios\n");{

                try{
                    //verificarPrecio();
                    enviarMensajeUsuario();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            };

        scheduler.scheduleAtFixedRate(notificationTask, 0, 4, TimeUnit.HOURS);

    }
}
