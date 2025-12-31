package net.tazgirl.survivor.events.wave_mob_constructed;

import net.tazgirl.survivor.main_game.mobs.wave_mobs.IWaveMob;
import net.tazgirl.tutilz.events.EventPriorityPair;
import net.tazgirl.tutilz.events.priority_events.EventPairListProcessor;

import java.util.List;

public class WaveMobConstructedEventHandler extends WaveMobConstructedEvent
{
    public static void ProcessWaveMobConstructedEvent(WaveMobConstructedEvent event, IWaveMob waveMobInterface)
    {
        List<EventPriorityPair<WaveMobConstructedEventAccessor>> pairs = event.getPairs();

        WaveMobConstructedEventAccessor accessor = new WaveMobConstructedEventAccessor(waveMobInterface);

        EventPairListProcessor.ResolveAndRun(pairs, accessor);
    }

}
