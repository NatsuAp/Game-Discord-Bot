package alerts;

import sqlite.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class programacionAlertas {
    private static void verificarPrecio() throws SQLException {
        ArrayList<Usuario> usuarios= sqlite.sqliteDrivers.obtenerUsuarios();


    }
    public static void iniciarAlertaPrecios(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            Runnable notificationTask = () -> IO.println("Verificando precios\n");{
                try{
                    verificarPrecio();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            };

        scheduler.scheduleAtFixedRate(notificationTask, 0, 4, TimeUnit.HOURS);

    }
}
