package net.tazgirl.survivor.misc_game_stuff;

import net.minecraft.world.entity.Entity;
import net.tazgirl.survivor.mobs.wave_mobs.WaveMob;
import net.tazgirl.survivor.wave.Wave;

import java.util.List;

public record FullContextData(Wave currentWave, WaveMob<?> thisMob, List<PlayerDataAccessor> players)
{

}
