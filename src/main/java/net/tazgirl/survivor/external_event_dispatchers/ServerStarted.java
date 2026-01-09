package net.tazgirl.survivor.external_event_dispatchers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber
public class ServerStarted
{
    @SubscribeEvent
    public static void OnServerStarted(ServerStartedEvent event)
    {

    }
}
