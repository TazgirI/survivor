package net.tazgirl.survivor.saved_data.registers.mob_sets;

import net.tazgirl.survivor.main_game.mobs.wave_mobs.WaveMobStorageSet;
import net.tazgirl.tutilz.registers.RegistryEvent;

public class MobSetsFetchEvent extends RegistryEvent<WaveMobStorageSet>
{
    public MobSetsFetchEvent()
    {
        super(MobSetsRegister.register);
    }
}
