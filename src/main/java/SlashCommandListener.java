import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.io.IOException;
import java.sql.SQLException;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
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
                String userID = event.getUser().getId();

                try {
                    event.reply(comandos.añadirJuego.comandoAñadirJuego(content, userID)).queue();
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            case "test" -> {
                event.reply("Funcionando").queue();
            }
        }
    }
}