package net.sparkzz.servercontrol;

import com.google.inject.Inject;
import net.sparkzz.servercontrol.util.Register;
import net.sparkzz.servercontrol.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;

/**
 * @author Brendon
 * @since April 28, 2015
 */
@Plugin(id = "ServerControl", name = "Server Control", version = "0.1-DEV")
public class ServerControl {

	@Inject private static Logger logger = LoggerFactory.getLogger("ServerControl");
	private static Server server;

	public static Logger getLogger() {
		return logger;
	}

	@Subscribe
	public void onPreInit(PreInitializationEvent event) {
		this.server = event.getGame().getServer();

		Utility.init(event.getGame(), logger);

		new Register(this, server, event);

		logger.info("Enabled successfully!");
	}

	@Subscribe
	public void onServerStopping(ServerStoppingEvent event) {
		logger.info("Disabled successfully!");
	}
}