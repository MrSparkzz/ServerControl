package net.sparkzz.servercontrol.command;

import com.google.common.base.Optional;
import net.sparkzz.servercontrol.util.Utility;
import org.spongepowered.api.Server;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import java.util.*;

/**
 * @author Brendon
 * @since April 30, 2015
 */
public class Plugins extends Utility implements CommandCallable {

	private final Server server;
	private final Optional<String> desc = Optional.of("View a list of installed plugins");
	private final Optional<String> help = Optional.of("View a list of installed plugins");
	private final String permission = "server.plugins";

	public Plugins(Server server) {
		this.server = server;
	}

	// TODO: getPlugins() returns mods as well, try to figure a way to split them
	// TODO: implement colors
	@Override
	public Optional<CommandResult> process(CommandSource source, String arguments) throws CommandException {
		if (!testPermission(source)) {
			source.sendMessage(Texts.of("You are not permitted to use this command!"));
			return result.EMPTY.getResult();
		}

		if (!arguments.equals("")) {
			source.sendMessage(getUsage(source));
			return result.EMPTY.getResult();
		}

		source.sendMessage(Texts.of("Plugins: " + getPluginList()));

		return result.SUCCESS.getResult();
	}

	private String getPluginList() {
		StringBuilder list = new StringBuilder();
		Collection<PluginContainer> plugins = game.getPluginManager().getPlugins();
		List<String> exclude = new ArrayList<String>(Arrays.asList("Minecraft", "Minecraft Coder Pack", "Forge Mod Loader", "Minecraft Forge", "Sponge"));

		int i = plugins.size();

		for (PluginContainer plugin : plugins) {
			if (!exclude.contains(plugin.getName())) {
				if (list.length() > 0) {
					//list.append(/*white*/);
					list.append(", ");
				}

				list.append(/*green?*/ plugin.getName());
			} else i--;
		}

		return /*dark gray*/ "(" /*pink*/ + i + /*dark gray*/ "): " + list.toString();
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
		return Texts.of("/plugins");
	}

	public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
		return Collections.emptyList();
	}
}