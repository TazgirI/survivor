package net.tazgirl.survivor.events.wave_mob_constructed;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.NeoForge;
import net.tazgirl.tutilz.events.EventPriorityPair;
import net.tazgirl.tutilz.events.priority_events.PriorityEvent;

import java.util.List;

public class WaveMobConstructedEvent extends PriorityEvent<WaveMobConstructedEventAccessor>
{
    public List<EventPriorityPair<WaveMobConstructedEventAccessor>> getPairs()
    {
        return super.getPairs();
    }

    public static WaveMobConstructedEvent Fire()
    {
        return NeoForge.EVENT_BUS.post(new WaveMobConstructedEvent());
    }
}
