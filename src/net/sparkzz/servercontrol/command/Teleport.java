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
 * @since April 30, 2015
 */
public class Teleport extends Utility implements CommandCallable {

	private final Server server;
	private final Optional<String> desc = Optional.of("Teleport around the world");
	private final Optional<String> help = Optional.of("Teleport around the world");
	private final String permission[] = {"server.teleport", "server.teleport.others"};

	public Teleport(Server server) {
		this.server = server;
	}

	// TODO: store player's last location to use the "/back" command
	// TODO: make the command better /tp John,Donny,xXRazerXx Jimmy or /tp Jim,Danny x y z or other
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

		if (args.length == 1) {
			if (!(source instanceof Player)) {
				log.info("Silly console, you cannot teleport!");
				return result.SUCCESS.getResult();
			}

			Player player = (Player) source;
			Player target = server.getPlayer(args[0]).get();

			if (target == null) {
				msg.notFound(source, args[0]);
				return result.SUCCESS.getResult();
			}

			player.setLocation(target.getLocation());
		}

		if (args.length == 2) {
			if (!source.hasPermission(permission[1])) {
				msg.deny(source, "teleport others!");
				return result.SUCCESS.getResult();
			}

			Player first = server.getPlayer(args[0]).get(), second = server.getPlayer(args[1]).get();

			if (first == null) {
				msg.notFound(source, args[0]);
				return result.SUCCESS.getResult();
			}

			if (second == null) {
				msg.notFound(source, args[1]);
				return result.SUCCESS.getResult();
			}

			first.setLocation(second.getLocation());
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
		return Texts.of("/teleport <player> <player>");
	}

	public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
		return Collections.emptyList();
	}
}