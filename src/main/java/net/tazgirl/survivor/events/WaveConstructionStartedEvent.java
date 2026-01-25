package net.tazgirl.survivor.events;

import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;

public class WaveConstructionStartedEvent extends Event
{
    public static void postEvent()
    {
        NeoForge.EVENT_BUS.post(new WaveConstructionStartedEvent());
    }
}
