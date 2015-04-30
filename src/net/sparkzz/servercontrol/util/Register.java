package net.sparkzz.servercontrol.util;

import net.sparkzz.servercontrol.ServerControl;
import net.sparkzz.servercontrol.command.Broadcast;
import net.sparkzz.servercontrol.command.Gamemode;
import net.sparkzz.servercontrol.command.Plugins;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.state.StateEvent;
import org.spongepowered.api.service.command.CommandService;

/**
 * @author Brendon
 * @since April 29, 2015
 */
public class Register {

	public Register(Object object, Server server, StateEvent event) {
		CommandService service = event.getGame().getCommandDispatcher();

		service.register(object, new Broadcast(server), "broadcast");
		service.register(object, new Gamemode(server), "gamemode");
		service.register(object, new Plugins(server), "plugins");
	}
}