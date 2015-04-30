package net.sparkzz.servercontrol.util;

import com.google.common.base.Optional;
import org.spongepowered.api.Game;
import org.spongepowered.api.util.command.CommandResult;

/**
 * @author Brendon
 * @since April 29, 2015
 */
public class Utility {

	protected static Game game;
	protected static org.slf4j.Logger log;

	protected static enum result {
		EMPTY(Optional.of(CommandResult.empty())), SUCCESS(Optional.of(CommandResult.success()));

		private Optional<CommandResult> value;

		result(Optional<CommandResult> value) {
			this.value = value;
		}

		public Optional<CommandResult> getResult() {
			return value;
		}
	}

	public static void init(Game game, org.slf4j.Logger logger) {
		Utility.game = game;
		log = logger;
	}
}

