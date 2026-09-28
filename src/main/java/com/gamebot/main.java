package com.gamebot;

import com.gamebot.comandos.precio;
import com.gamebot.helpers.json_map;
import com.gamebot.sqlite.sqliteDrivers;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;

import java.io.IOException;
import java.net.URISyntaxException;

//
//Commands.slash("precio", "Busca el precio del sqlite.juego")
//                    .addOption(STRING, "sqlite.juego", "Que sqlite.juego desea buscar", true)
//                    .setContexts(InteractionContextType.GUILD) // this doesn't make sense in DMs
//                    .setDefaultPermissions(DefaultMemberPermissions.DISABLED) // only admins should be able to use this command.


public class main{
    public static Dotenv dotenv;
    public static JDA jda;

    public static void setup() throws URISyntaxException, IOException {
        dotenv = Dotenv.load();
        precio.storeMap = json_map.jsonmap();
        sqliteDrivers.connect();

        crearJDA.crearInstanciaJDA();
        programacionAlertas.iniciarAlertaPrecios();


    }
    public static void main(String[] args)  {
        //https://www.cheapshark.com/redirect?dealID={0f%2B4gT2VVUn4UcmFzPxXnuqoXKAOYoJ5mpFZRWNyohc%3D}  *Redirect*
        try{
            setup();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }




    }

}

