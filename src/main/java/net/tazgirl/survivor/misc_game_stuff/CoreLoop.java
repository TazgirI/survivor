package net.tazgirl.survivor.misc_game_stuff;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.tazgirl.survivor.InternalMagicValues;

@EventBusSubscriber
public class CoreLoop
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onServerTick(ServerTickEvent.Pre event)
    {
        if(CoreGameData.currentWave != null && CoreGameData.zeroedTicks % InternalMagicValues.ticksToCensus == 0 && CoreGameData.currentWave.mobsPlaced())
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
