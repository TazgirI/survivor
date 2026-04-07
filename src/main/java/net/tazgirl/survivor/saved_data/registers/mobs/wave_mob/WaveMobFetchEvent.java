package net.tazgirl.survivor.saved_data.registers.mobs.wave_mob;

import net.tazgirl.survivor.mobs.wave_mobs.WaveMob;
import net.tazgirl.tutilz.registers.RegistryEvent;

public class WaveMobFetchEvent extends RegistryEvent<WaveMob<?>>
{
    public WaveMobFetchEvent()
    {
        super(RegisterWaveMob.register);
    }
}
