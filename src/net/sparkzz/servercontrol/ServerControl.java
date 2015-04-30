package net.sparkzz.servercontrol;

import com.google.inject.Inject;
import net.sparkzz.servercontrol.config.Config;
import net.sparkzz.servercontrol.util.Register;
import net.sparkzz.servercontrol.util.Utility;
import ninja.leaping.configurate.ConfigurationNode;
import org.slf4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;

/**
 * @author Brendon
 * @since April 28, 2015
 */
@Plugin(id = "ServerControl", name = "Server Control", version = "0.1-DEV")
public class ServerControl {

	private Config config;
	@Inject
	private static Logger logger;
	private static Server server;

	public static Logger getLogger() {
		return logger;
	}

	@Subscribe
	public void onPreInit(PreInitializationEvent event) {
		this.server = event.getGame().getServer();
		this.config = new Config();

		Utility.init(event.getGame(), logger);

		new Register(this, server, event);

		logger.info("Enabled successfully!");
	}

	@Subscribe
	public void onServerStopping(ServerStoppingEvent event) {
		config.unload();

		logger.info("Disabled successfully!");
	}
}