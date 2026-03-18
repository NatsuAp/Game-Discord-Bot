package sqlite;

import com.google.gson.*;

import java.sql.*;
import java.util.ArrayList;

public class sqliteDrivers {
    //Esta funcion da el siguiente warning
    /* WARNING: A restricted method in java.lang.System has been called
WARNING: java.lang.System::load has been called by org.sqlite.SQLiteJDBCLoader in an unnamed module (file:/home/andres/.m2/repository/org/xerial/sqlite-jdbc/3.51.3.0/sqlite-jdbc-3.51.3.0.jar)
WARNING: Use --enable-native-access=ALL-UNNAMED to avoid a warning for callers in this module
WARNING: Restricted methods will be blocked in a future release unless native access is enabled*/


//    For INSERT, UPDATE or DELETE use the executeUpdate() method
//    and for SELECT use the executeQuery() method which returns the ResultSet.
    public static Connection conn = null;
    public static void connect(){
        String url = "jdbc:sqlite:src/main/resources/db/datitoData.db";

        try{
            conn = DriverManager.getConnection(url);
            IO.println("Conectado Exitosamente");
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public static ArrayList<usuario> usuarios = new ArrayList<>();
    public static boolean añadirNuevoUsuario(String idUsuario, Juego juego) throws userException, SQLException {

        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO datos (IdUsuario, juegos)"
                                + "VALUES ("+ idUsuario+ " , '[]'); ");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        añadirJuegoATabla(juego, idUsuario);
        return true;
        }
        //TODO: Funcion para verificar si ya existe el juego que se intenta añadir
        public static boolean revisarSiJuegoExisteEnTabla(ArrayList<Juego> juegos, Juego juego){
        for(Juego x : juegos){
            if(x.idJuego == juego.idJuego){
                return true;
            }
        }
        return false;
        }

        public static ArrayList<Juego> getJuegosDelUsuario(String IDUsuario) throws SQLException {
        ArrayList<Juego> juegos = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs =  st.executeQuery("select juegos from datos where idUsuario="+ "'" +IDUsuario+ "'");
        String juegosJson = rs.getString("juegos");
            JsonArray jsonArr = JsonParser.parseString(juegosJson).getAsJsonArray();
            IO.println(jsonArr.size());
            for(JsonElement je:jsonArr){
                JsonObject obj = je.getAsJsonObject();
                String nombre = obj.get("nombre").getAsString();
                int idJuego = obj.get("idJuego").getAsInt();
                float precio = obj.get("precioActual").getAsFloat();
                int idtienda = obj.get("idtienda").getAsInt();
                String idLink = obj.get("idLink").getAsString();
                Juego juego = new Juego(nombre, idJuego, precio, idtienda, idLink);
                juegos.add(juego);
            }
            return juegos;
        }
        public static boolean añadirJuegoATabla(Juego juego, String idUsuario) throws SQLException, userException{
        ArrayList<Juego> juegos = getJuegosDelUsuario(idUsuario);
        if(revisarSiJuegoExisteEnTabla(juegos, juego)) throw new userException("Ya tienes este juego en tu lista de seguimiento");
        juegos.add(juego);
        String json = new Gson().toJson(juegos);
        IO.println(json);
        try {
            Statement st = conn.createStatement();

            st.executeUpdate("UPDATE datos SET juegos = '" + json + "'" + "WHERE IdUsuario = '" + idUsuario + "';");

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
        }
}


