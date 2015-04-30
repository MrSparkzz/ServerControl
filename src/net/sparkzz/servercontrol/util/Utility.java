package net.sparkzz.servercontrol.util;

import org.spongepowered.api.Game;

/**
 * @author Brendon
 * @since April 29, 2015
 */
public class Utility {

	protected static Game game;
	protected static org.slf4j.Logger log;

	public static void init(Game game, org.slf4j.Logger logger) {
		Utility.game = game;
		log = logger;
	}
}