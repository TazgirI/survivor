package net.tazgirl.survivor.saved_data.interactive_positions;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.tazgirl.survivor.data.seeds.structure.SeedHolder;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.survivor.misc_game_stuff.StartRun;
import net.tazgirl.survivor.misc_game_stuff.StartRunContext;
import net.tazgirl.survivor.saved_data.registers.mobs.wave_mob.RegisterWaveMob;
import net.tazgirl.survivor.wave.wave_constructor.WaveConstructor;

@EventBusSubscriber
public class FillInteractionPoints
{
    @SubscribeEvent
    public static void onFetchEvent(RegisterInteractionPositions.FetchEvent event)
    {
        event.add("startGame", FillInteractionPoints::startGame);
        event.add("nextWave", FillInteractionPoints::nextWave);
    }

    public static void startGame(PlayerInteractEvent.RightClickBlock rightClickBlock)
    {
        // TODO: Create actual input menu etc
        StartRun.start(new StartRunContext(SeedHolder.newRandom()));
    }

    public static void nextWave(PlayerInteractEvent.RightClickBlock rightClickBlock)
    {
        // TODO: Fill with correct data and make point calculator
        if(CoreGameData.currentWave != null && CoreGameData.currentWave.waveFinished())
        {
            CoreGameData.currentWave = WaveConstructor.constructWave(10 + CoreGameData.waveNum, CoreGameData.waveNum, RegisterWaveMob.debugTestSet());
        }
    }
}
