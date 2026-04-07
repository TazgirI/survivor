package net.tazgirl.survivor.saved_data.registers.mobs.mob_sets;

import net.tazgirl.survivor.mobs.wave_mobs.WaveMobStorageSet;
import net.tazgirl.tutilz.registers.RegistryEvent;

public class MobSetsFetchEvent extends RegistryEvent<WaveMobStorageSet>
{
    public MobSetsFetchEvent()
    {
        super(RegisterMobSets.register);
    }
}
