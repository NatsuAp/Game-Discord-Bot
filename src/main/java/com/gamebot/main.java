package com.gamebot;

import com.gamebot.sqlite.sqliteDrivers;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;

//
//Commands.slash("precio", "Busca el precio del sqlite.juego")
//                    .addOption(STRING, "sqlite.juego", "Que sqlite.juego desea buscar", true)
//                    .setContexts(InteractionContextType.GUILD) // this doesn't make sense in DMs
//                    .setDefaultPermissions(DefaultMemberPermissions.DISABLED) // only admins should be able to use this command.


public class main{
    public static Dotenv dotenv;
    public static JDA jda;

    public static void setup(){
        sqliteDrivers.connect();
        dotenv = Dotenv.load();
        crearJDA.crearInstanciaJDA();
        programacionAlertas.iniciarAlertaPrecios();


    }
    public static void main(String[] args) {
        //https://www.cheapshark.com/redirect?dealID={0f%2B4gT2VVUn4UcmFzPxXnuqoXKAOYoJ5mpFZRWNyohc%3D}  *Redirect*
        setup();



    }

}

