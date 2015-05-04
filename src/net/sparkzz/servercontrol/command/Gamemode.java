package net.sparkzz.servercontrol.command;

import com.google.common.base.Optional;
import net.sparkzz.servercontrol.util.Utility;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.entity.player.gamemode.GameMode;
import org.spongepowered.api.entity.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
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
			msg.deny(source, msg.COMMAND);
			return result.SUCCESS.getResult();
		}

		String args[] = arguments.split(" ");

		if (args.length == 0) {
			if (!(source instanceof Player)) {
				log.info("Silly console, gamemodes are for players!");
				return result.SUCCESS.getResult();
			}

			Player player = (Player) source;

			if (player.hasPermission(permission[3]))
				switcher(player);

			return result.SUCCESS.getResult();
		}

		if (args.length == 1) {
			if (!(source instanceof Player)) {
				log.info("Silly console, gamemodes are for players!");
				return result.SUCCESS.getResult();
			}

			Player player = (Player) source;

			if (player.hasPermission(permission[3]))
				change(player, player, args[0], true);

			return result.SUCCESS.getResult();
		}

		if (args.length == 2) {
			if (!source.hasPermission(permission[4])) {
				msg.deny(source, msg.COMMAND);
				return result.SUCCESS.getResult();
			}

			if (!server.getOnlinePlayers().contains(args[1])) {
				msg.notFound(source, args[1]);
				return result.SUCCESS.getResult();
			}

			Optional<Player> target = server.getPlayer(args[1]);

			if (target == null) {
				msg.notFound(source, args[0]);
				return result.SUCCESS.getResult();
			}

			if (!target.equals(source))
				change(source, target.get(), args[0], false);
			else change(source, target.get(), args[0], true);

			return result.SUCCESS.getResult();
		}

		return result.EMPTY.getResult();
	}

	private void change(CommandSource source, Player target, String gamemode, boolean self) {
		int selected = -1;
		String prefix, permit;

		if (self) {
			prefix = TextColors.RED + "You are already in ";
			permit = TextColors.RED + "You are not permitted to enter ";
		} else {
			prefix = TextColors.GOLD + target.getName() + TextColors.RED + " is already in ";
			permit = TextColors.RED + "You are not permitted to put others in ";
		}


		for (int i = 0; i < mode.length; i++) {
			if (Arrays.asList(mode[i]).contains(gamemode)) selected = i;
		}

		switch (selected) {
			case 0:
				if (target.getGameModeData().getGameMode().equals(GameModes.SURVIVAL))
					source.sendMessage(Texts.of(prefix + "survival mode!"));
				else
					target.getGameModeData().setGameMode(GameModes.SURVIVAL);
				break;
			case 1:
				if (!target.hasPermission(permission[1])) {
					source.sendMessage(Texts.of(permit));
					break;
				}

				if (target.getGameModeData().getGameMode().equals(GameModes.CREATIVE))
					source.sendMessage(Texts.of(prefix + "creative mode!"));
				else
					target.getGameModeData().setGameMode(GameModes.CREATIVE);
				break;
			case 2:
				if (target.getGameModeData().getGameMode().equals(GameModes.ADVENTURE))
					source.sendMessage(Texts.of(prefix + "adventure mode!"));
				else
					target.getGameModeData().setGameMode(GameModes.ADVENTURE);
				break;
			case 3:
				if (!source.hasPermission(permission[2])) {
					target.sendMessage(Texts.of(permit));
					break;
				}

				if (target.getGameModeData().getGameMode().equals(GameModes.SPECTATOR))
					source.sendMessage(Texts.of(prefix + "spectator mode!"));
				else
					target.getGameModeData().setGameMode(GameModes.SPECTATOR);
				break;
			default:
				target.sendMessage(Texts.of("Invalid arguments!"));
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
		return Texts.of("/gamemode <mode> [player]");
	}

	public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
		return Collections.emptyList();
	}
}