package net.tazgirl.survivor.main_game.mobs.wave_mobs;

import net.tazgirl.survivor.main_game.mobs.modifiers.storage.CachedModifierStorageRecord;
import net.tazgirl.survivor.main_game.mobs.modifiers.storage.CachedModifierStorageSet;

public record ActivatedWaveMob(WaveMob<?> waveMob, CachedWaveMob cachedWaveMob, CachedModifierStorageSet cachedModifiers)
{
}
