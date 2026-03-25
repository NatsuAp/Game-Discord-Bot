package com.gamebot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.Collections;

import static net.dv8tion.jda.api.interactions.commands.OptionType.INTEGER;
import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class crearJDA {
    public static JDA jda;
    public static void crearInstanciaJDA(){
        jda = JDABuilder
                .createLight(main.dotenv.get("BOT_TOKEN"), Collections.emptyList())
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("hola"))
                .addEventListeners(new SlashCommandListener())
                .build();

        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(
                Commands.slash("test","Probar"),
                Commands.slash("buscar", "Buscar sqlite.juego")
                        .addOption(STRING, "juego", "El sqlite.juego que quieres buscar", true), // Accepting a user input
                Commands.slash("precio", "Busca el precio del sqlite.juego")
                        .addOption(STRING, "juego", "Que sqlite.juego desea buscar", true),
                Commands.slash("añadirjuego", "Añade un juego a tu lista personal")
                        .addOption(STRING, "juego", "Juego a añadir a tu lista personal", true),
                Commands.slash("listarjuegos", "Listar juegos de tu lista personal"),
                Commands.slash("eliminarjuego", "Ingresa el indice o el nombre del juego que desees eliminar de tu lista")
                        .addOption(STRING, "juego", "Nombre (exacto) del juego en tu lista personal que deseas eliminar", false)
                        .addOption(INTEGER, "indice", "Indice en tu lista personal de el juego a eliminar", false)
                
        );
        commands.queue();

    }
    public static void enviarMensajeUsuario(String IdUsuario, String msg){
        System.out.println("hola");
        User user = jda.retrieveUserById(IdUsuario).complete();
                user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(msg))
                .queue();

    }
}
