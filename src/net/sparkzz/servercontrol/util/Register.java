package net.sparkzz.servercontrol.util;

import net.sparkzz.servercontrol.ServerControl;
import net.sparkzz.servercontrol.command.Broadcast;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.state.StateEvent;
import org.spongepowered.api.service.command.CommandService;

/**
 * @author Brendon
 * @since April 29, 2015
 */
public class Register {

	public Register(Server server, StateEvent event) {
		CommandService service = event.getGame().getCommandDispatcher();

		service.register(ServerControl.class, new Broadcast(server), "broadcast");
	}
}