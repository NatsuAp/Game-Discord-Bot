import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;
//
//Commands.slash("precio", "Busca el precio del juego")
//                    .addOption(STRING, "juego", "Que juego desea buscar", true)
//                    .setContexts(InteractionContextType.GUILD) // this doesn't make sense in DMs
//                    .setDefaultPermissions(DefaultMemberPermissions.DISABLED) // only admins should be able to use this command.
void main() {
    Dotenv dotenv = Dotenv.load();

    JDA jda = JDABuilder
            .createLight(dotenv.get("BOT_TOKEN"), Collections.emptyList())
            .setActivity(Activity.playing("Con la gorda de tu madre"))
            .addEventListeners(new SlashCommandListener())
            .build();

    CommandListUpdateAction commands = jda.updateCommands();

    commands.addCommands(
            Commands.slash("buscar", "Buscar juego")
                    .addOption(STRING, "juego", "El juego que quieres buscar", true), // Accepting a user input
            Commands.slash("precio", "Busca el precio del juego")
                    .addOption(STRING, "juego", "Que juego desea buscar", true)

    );


    commands.queue();

}

