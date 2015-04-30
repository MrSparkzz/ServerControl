package net.sparkzz.servercontrol.command;

import com.google.common.base.Optional;
import net.sparkzz.servercontrol.util.Utility;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.entity.player.gamemode.GameMode;
import org.spongepowered.api.entity.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Brendon
 * @since April 29, 2015
 */
public class Gamemode extends Utility implements CommandCallable {

	private final Server server;
	private final Optional<String> desc = Optional.of("Change your gamemode");
	private final Optional<String> help = Optional.of("Change your gamemode");
	private final String permission[] = {"server.gamemode", "server.gamemode.creative", "server.gamemode.spectator", "server.gamemode.self", "server.gamemode.other"};
	private final String[][] mode = new String[][] {
			{"0", "survival", "surv", "s"},
			{"1", "creative", "create", "c"},
			{"2", "adventure", "adv", "a"},
			{"3", "spectator", "spec", "sp"}
	};

	public Gamemode(Server server) {
		this.server = server;
	}

	@Override
	public Optional<CommandResult> process(CommandSource source, String arguments) throws CommandException {
		if (!testPermission(source)) {
			source.sendMessage(Texts.of("You are not permitted to use this command!"));
			return Optional.of(CommandResult.empty());
		}

		String args[] = arguments.split(" ");

		if (args.length == 0) {
			if (!(source instanceof Player)) {
				log.info("Silly console, gamemodes are for players!");
				return Optional.of(CommandResult.success());
			}

			Player player = (Player) source;

			if (player.hasPermission(permission[3]))
				switcher(player);
		}

		if (args.length == 1) {
			if (!(source instanceof Player)) {
				log.info("Silly console, gamemodes are for players!");
				return Optional.of(CommandResult.success());
			}

			Player player = (Player) source;

			if (player.hasPermission(permission[3]))
				change(player, args[0]);
		}

		return Optional.of(CommandResult.success());
	}

	private void change(Player player, String gamemode) {
		int selected = -1;

		for (int i = 0; i < mode.length; i++) {
			if (Arrays.asList(mode[i]).contains(gamemode)) selected = i;
		}

		switch (selected) {
			case 0:
				if (player.getGameModeData().getGameMode().equals(GameModes.SURVIVAL))
					player.sendMessage(Texts.of("You are already in survival mode!"));
				else
					player.getGameModeData().setGameMode(GameModes.SURVIVAL);
				break;
			case 1:
				if (!player.hasPermission(permission[1])) {
					player.sendMessage(Texts.of("You are not permitted to enter creative mode!"));
					break;
				}

				if (player.getGameModeData().getGameMode().equals(GameModes.CREATIVE))
					player.sendMessage(Texts.of("You are already in creative mode!"));
				else
					player.getGameModeData().setGameMode(GameModes.CREATIVE);
				break;
			case 2:
				if (player.getGameModeData().getGameMode().equals(GameModes.ADVENTURE))
					player.sendMessage(Texts.of("You are already in adventure mode!"));
				else
					player.getGameModeData().setGameMode(GameModes.ADVENTURE);
				break;
			case 3:
				if (!player.hasPermission(permission[2])) {
					player.sendMessage(Texts.of("You are not permitted to enter spectator mode!"));
					break;
				}

				if (player.getGameModeData().getGameMode().equals(GameModes.SPECTATOR))
					player.sendMessage(Texts.of("You are already in spectator mode!"));
				else
					player.getGameModeData().setGameMode(GameModes.SPECTATOR);
				break;
			default:
				break;
		}
	}

	private void switcher(Player player) {
		GameMode gamemode = player.getGameModeData().getGameMode();
		int mode = 0;

		if (gamemode.equals(GameModes.CREATIVE)) mode = 1;
		if (gamemode.equals(GameModes.ADVENTURE)) mode = 2;
		if (gamemode.equals(GameModes.SPECTATOR)) mode = 3;

		switch (mode) {
			case 0:
				if (player.hasPermission(permission[1]))
					player.getGameModeData().setGameMode(GameModes.CREATIVE);
				else
					player.getGameModeData().setGameMode(GameModes.ADVENTURE);
				break;
			default:
				player.getGameModeData().setGameMode(GameModes.SURVIVAL);
				break;
		}
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
		return Texts.of("/broadcast [MESSAGE..]");
	}

	public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
		return Collections.emptyList();
	}
}
