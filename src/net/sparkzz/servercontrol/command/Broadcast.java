package net.sparkzz.servercontrol.command;

import com.google.common.base.Optional;
import net.sparkzz.servercontrol.util.Utility;
import org.spongepowered.api.Server;
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
 * @since April 28, 2015
 */
public class Broadcast extends Utility implements CommandCallable {

	private final Server server;
	private final Optional<String> desc = Optional.of("Announces a message to all players");
	private final Optional<String> help = Optional.of("Announce something to all online players");
	private final String permission = "server.broadcast";

	public Broadcast(Server server) {
		this.server = server;
	}

	@Override
	public Optional<CommandResult> process(CommandSource source, String arguments) throws CommandException {
		if (!testPermission(source)) {
			msg.deny(source, msg.COMMAND);
			return result.EMPTY.getResult();
		}

		if (!arguments.equals(""))
			server.broadcastMessage(Texts.of("[Broadcast] " + arguments));
		else {
			source.sendMessage(getUsage(source));
			return result.EMPTY.getResult();
		}

		return result.SUCCESS.getResult();
	}

	@Override
	public boolean testPermission(CommandSource source) {
		return source.hasPermission(permission);
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
		return Texts.of("/broadcast [MESSAGE..]");
	}

	public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
		return Collections.emptyList();
	}
}