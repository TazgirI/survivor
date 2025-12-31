package net.tazgirl.survivor.saved_data.registers.wave_mob;

import net.tazgirl.survivor.main_game.mobs.wave_mobs.WaveMob;
import net.tazgirl.tutilz.registers.RegistryEvent;

public class WaveMobFetchEvent extends RegistryEvent<WaveMob<?>>
{
    public WaveMobFetchEvent()
    {
        super(WaveMobRegister.register);
    }
}
