package com.gamebot;

import com.gamebot.comandos.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.io.IOException;
import java.sql.SQLException;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String userID = event.getUser().getId();

        switch (event.getName()) {
            case "buscar" -> {
                String content = event.getOption("juego", OptionMapping::getAsString);
                System.out.println(content);
                System.out.println(event.getUser().getId());
                event.reply(buscar.comandoBuscar(content)).queue();
            }
            case "precio" -> {

                String content = event.getOption("juego", OptionMapping::getAsString);

                try {
                    event.reply(precio.comandoPrecio(content)).queue();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            case "añadirjuego" -> {
                String content = event.getOption("juego", OptionMapping::getAsString);

                try {
                    event.reply(añadirJuego.comandoAñadirJuego(content, userID)).queue();
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            case "listarjuegos" -> {
                try {
                 event.reply(listarJuegos.comandoListarJuegos(userID)).queue();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            case "eliminarjuego" -> {
                if(!helpersComandos.revisarSiUsuarioExiste(userID)){
                    event.reply("Actualmente no tienes juegos en tu lista\n");
                    return;
                }
                String  nombreJuego = event.getOption("juego", OptionMapping::getAsString);
                String indiceJuego = event.getOption("indice", OptionMapping::getAsString);
                    if(nombreJuego == null && indiceJuego == null) event.reply("Debes ingresar el nombre " +
                            "del juego o el indice en la lista en la cual se encuentra");
                    try {
                        if(indiceJuego!=null){
                            event.reply(eliminarJuego.comandoEliminarJuegoPorIndice(userID,indiceJuego)).queue();
                        }else{
                            event.reply(eliminarJuego.comandoEliminarJuegoPorNombre(userID,nombreJuego)).queue();
                        }
                    }catch (SQLException e){
                        throw new RuntimeException(e);
                    }


            }
            case "test" -> {
                event.reply("Funcionando").queue();
            }
        }
    }
}