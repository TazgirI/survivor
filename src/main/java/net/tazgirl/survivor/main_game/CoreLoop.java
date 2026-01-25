package net.tazgirl.survivor.main_game;

import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.tazgirl.survivor.InternalMagicValues;
import net.tazgirl.survivor.main_game.wave.Wave;

public class CoreLoop
{

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onServerTick(ServerTickEvent event)
    {
        if(CoreGameData.currentWave != null && CoreGameData.zeroedTicks % InternalMagicValues.ticksToCensus == 0)
        {
            CoreGameData.currentWave.updateCount();

            if(CoreGameData.currentWave.getTotalRemainingMobs() <= 0)
            {
                CoreGameData.currentWave = null;
                CoreGameData.waveNum++;
                GameState.currentState = GameState.State.WAVE_BREAK;
            }
        }
    }
}
