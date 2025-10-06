package net.tazgirl.survivor.external_event_dispatchers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.Inits.InitSavedData;

@EventBusSubscriber(modid = Survivor.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerStarting
{
    @SubscribeEvent
    public static void OnServerStarting(ServerStartingEvent event)
    {
        Globals.OnServerStarting(event);

        InitSavedData.OnServerStarting(event);
    }

}
