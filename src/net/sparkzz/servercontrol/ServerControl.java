package net.sparkzz.servercontrol;

import net.sparkzz.servercontrol.util.Register;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

/**
 * @author Brendon
 * @since April 28, 2015
 */
@Plugin(id = "ServerControl", name = "Server Control", version = "0.1-DEV")
public class ServerControl {

	private static Server server;

	@Subscribe
	public void onServerStart(PreInitializationEvent event) {
		this.server = event.getGame().getServer();

		new Register(server, event);
	}
}