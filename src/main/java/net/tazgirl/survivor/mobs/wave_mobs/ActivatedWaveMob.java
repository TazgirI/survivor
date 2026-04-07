package net.tazgirl.survivor.mobs.wave_mobs;

import net.tazgirl.survivor.mobs.modifiers.storage.CachedModifierStorageSet;

public record ActivatedWaveMob(WaveMob<?> waveMob, CachedWaveMob cachedWaveMob, CachedModifierStorageSet cachedModifiers)
{
}
