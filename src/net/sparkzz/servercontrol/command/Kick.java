package net.sparkzz.servercontrol.command;

import com.google.common.base.Optional;
import net.sparkzz.servercontrol.util.Utility;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import java.util.Collections;
import java.util.List;

/**
 * @author Brendon
 * @since May 04, 2015
 */
public class Kick extends Utility implements CommandCallable {

	private final Server server;
	private final Optional<String> desc = Optional.of("Kick a player");
	private final Optional<String> help = Optional.of("Kick a player");
	private final String permission[] = {"server.kicked", "server.ban.view"};

	public Kick(Server server) {
		this.server = server;
	}

	@Override
	public Optional<CommandResult> process(CommandSource source, String arguments) throws CommandException {
		if (!testPermission(source)) {
			msg.deny(source, action.COMMAND);
			return result.EMPTY.getResult();
		}

		if (arguments.equals("")) {
			source.sendMessage(getUsage(source));
			return result.EMPTY.getResult();
		}

		String args[] = arguments.split(" ");

		Player player = server.getPlayer(args[0]).get();

		if (player == null) {
			msg.notFound(source, args[0]);
			return result.SUCCESS.getResult();
		}

		if (args.length == 1) {
			player.kick();

			return result.SUCCESS.getResult();
		}

		if (args.length > 1) {
			String reason = msg.buildString(1, args);
			player.kick(Texts.of(reason));

			return result.SUCCESS.getResult();
		}

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
		return Texts.of("/kick <player> [REASON..]");
	}

	public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
		return Collections.emptyList();
	}
}