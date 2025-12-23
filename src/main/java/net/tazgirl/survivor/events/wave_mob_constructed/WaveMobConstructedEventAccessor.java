package net.tazgirl.survivor.events.wave_mob_constructed;

import net.tazgirl.survivor.main_game.mobs.IWaveMob;
import net.tazgirl.tutilz.events.priority_events.EventAccessor;


public class WaveMobConstructedEventAccessor extends EventAccessor<WaveMobConstructedEventAccessor>
{
    private final IWaveMob waveMobInterface;

    public WaveMobConstructedEventAccessor(IWaveMob waveMobInterface)
    {
        super(null);
        this.waveMobInterface = waveMobInterface;
    }

    public IWaveMob getWaveMobInterface()
    {
        return waveMobInterface;
    }

    public void test()
    {
        waveMobInterface.getWeight();
    }

}
