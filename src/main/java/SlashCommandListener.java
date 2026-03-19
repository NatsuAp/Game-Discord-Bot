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
                IO.println(content);
                event.reply(comandos.buscar.comandoBuscar(content)).queue();
            }
            case "precio" -> {
                String content = event.getOption("juego", OptionMapping::getAsString);

                try {
                    event.reply(comandos.precio.comandoPrecio(content)).queue();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            case "añadirjuego" -> {
                String content = event.getOption("juego", OptionMapping::getAsString);

                try {
                    event.reply(comandos.añadirJuego.comandoAñadirJuego(content, userID)).queue();
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            case "listarjuegos" -> {
                try {
                 event.reply(comandos.listarJuegos.comandoListarJuegos(userID)).queue();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            case "eliminarjuego" -> {
                if(!comandos.helpersComandos.revisarSiUsuarioExiste(userID)){
                    event.reply("Actualmente no tienes juegos en tu lista\n");
                    return;
                }
                String  nombreJuego = event.getOption("juego", OptionMapping::getAsString);
                String indiceJuego = event.getOption("indice", OptionMapping::getAsString);
                    if(nombreJuego == null && indiceJuego == null) event.reply("Debes ingresar el nombre " +
                            "del juego o el indice en la lista en la cual se encuentra");
                    try {
                        if(indiceJuego!=null){
                            event.reply(comandos.eliminarJuego.comandoEliminarJuegoPorIndice(userID,indiceJuego)).queue();
                        }else{
                            event.reply(comandos.eliminarJuego.comandoEliminarJuegoPorNombre(userID,nombreJuego)).queue();
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