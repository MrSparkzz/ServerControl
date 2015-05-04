package net.sparkzz.servercontrol.command;

import com.google.common.base.Optional;
import net.sparkzz.servercontrol.util.Utility;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import java.util.Collections;
import java.util.List;

/**
 * @author Brendon
 * @since April 30, 2015
 */
public class Heal extends Utility implements CommandCallable {

	private final Server server;
	private final Optional<String> desc = Optional.of("Heal yourself or another player");
	private final Optional<String> help = Optional.of("Heal yourself or another player");
	private final String permission[] = {"server.heal", "server.heal.other"};

	public Heal(Server server) {
		this.server = server;
	}

	@Override
	public Optional<CommandResult> process(CommandSource source, String arguments) throws CommandException {
		if (!testPermission(source)) {
			msg.deny(source, action.COMMAND);
			return result.EMPTY.getResult();
		}

		if (arguments.equals("")) {
			if (!(source instanceof Player)) {
				log.info("Silly console, you cannot teleport!");
				return result.SUCCESS.getResult();
			}

			Player player = (Player) source;

			player.getHealthData().setHealth(player.getHealthData().getMaxHealth());
		}

		String args[] = arguments.split(" ");

		if (args.length > 1) result.EMPTY.getResult();

		Optional<Player> target = server.getPlayer(args[0]);

		if (target == null) {
			msg.notFound(source, args[0]);
			return result.SUCCESS.getResult();
		}

		target.get().getHealthData().setHealth(target.get().getHealthData().getMaxHealth());

		return result.SUCCESS.getResult();
	}

	@Override
	public boolean testPermission(CommandSource source) {
		return source.hasPermission(permission[0]);
	}

	@Override
	public Optional<Text> getShortDescription(CommandSource source) {
		return Optional.of(Texts.of(desc));
	}

	@Override
	public Optional<Text> getHelp(CommandSource source) {
		return Optional.of(Texts.of(help));
	}

	@Override
	public Text getUsage(CommandSource source) {
		return Texts.of("/heal [player]");
	}

	public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
		return Collections.emptyList();
	}
}