package net.tazgirl.survivor.events.wave_setup;

import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.tazgirl.survivor.main_game.wave.Wave;
import net.tazgirl.tutilz.events.EventPriorityPair;
import net.tazgirl.tutilz.events.priority_events.PriorityEvent;


import java.util.ArrayList;
import java.util.List;

public abstract class WaveSetupEvent<T extends WaveSetupEventAccessor> extends PriorityEvent<T>
{
    List<EventPriorityPair<WaveSetupEventAccessor>> priorityPairs = new ArrayList<>();



    public static class Pre extends WaveSetupEvent<WaveSetupEventAccessor.Pre>
    {

    }


    public static class Post extends WaveSetupEvent<WaveSetupEventAccessor.Post>
    {
        public Wave incomingWave;
    }

    public static WaveSetupEvent.Pre FirePre()
    {
        return NeoForge.EVENT_BUS.post(new WaveSetupEvent.Pre());
    }

    public static WaveSetupEvent.Post FirePost()
    {
        return NeoForge.EVENT_BUS.post(new WaveSetupEvent.Post());
    }
}
