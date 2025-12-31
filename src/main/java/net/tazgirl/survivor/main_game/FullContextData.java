package net.tazgirl.survivor.main_game;

import net.minecraft.world.entity.Entity;
import net.tazgirl.survivor.main_game.mobs.wave_mobs.WaveMob;
import net.tazgirl.survivor.main_game.wave.Wave;

import java.util.List;

public record FullContextData(Wave currentWave, WaveMob<Entity> thisMob, List<PlayerDataAccessor> players)
{

}
