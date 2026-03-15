import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

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

                event.reply("I'm leaving the server now!")
                        .setEphemeral(true) // this message is only visible to the command user
                        .flatMap(m -> event.getGuild().leave()) // append a follow-up action using flatMap
                        .queue(); // enqueue both actions to run in sequence (send message -> leave guild)
            }
        }
    }
}