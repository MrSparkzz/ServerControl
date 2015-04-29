package net.sparkzz.servercontrol;

import org.slf4j.Logger;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

/**
 * @author Brendon
 * @since April 28, 2015
 */
@Plugin(id = "ServerControl", name = "Server Control", version = "0.1DEV")
public class ServerControl {

	@com.google.inject.Inject
	private Logger logger;

	public Logger getLogger() {
		return logger;
	}

	@Subscribe
	public void onServerStart(PreInitializationEvent event) {
		getLogger().info("Twerking..");
	}
}
