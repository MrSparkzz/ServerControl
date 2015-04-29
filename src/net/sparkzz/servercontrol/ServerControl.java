package net.sparkzz.servercontrol;

import net.sparkzz.servercontrol.command.Broadcast;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.command.CommandService;

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

		CommandService service = event.getGame().getCommandDispatcher();
		service.register(this, new Broadcast(server), "broadcast");
	}
}