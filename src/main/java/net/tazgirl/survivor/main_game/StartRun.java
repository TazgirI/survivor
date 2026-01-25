package net.tazgirl.survivor.main_game;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.tazgirl.survivor.main_game.data.seeds.structure.SeedHolder;
import net.tazgirl.survivor.main_game.wave.wave_constructor.WaveConstructor;
import net.tazgirl.survivor.saved_data.registers.wave_mob.RegisterWaveMob;

import java.util.Objects;

@EventBusSubscriber
public class StartRun
{
    public static void start(StartRunContext context)
    {
        CoreGameData.seedHolder = Objects.requireNonNullElseGet(context.preppedSeeds, SeedHolder::newRandom);

        GameState.currentState = GameState.State.PREPARING;

        StartRunEvent.fire();

        CoreGameData.currentWave = WaveConstructor.constructWave(10, 1, RegisterWaveMob.debugTestSet());
        GameState.currentState = GameState.State.WAVE_ACTIVE;

        CoreGameData.currentWave.placeMobs();
    }


    public static class StartRunEvent extends Event
    {
        static void fire()
        {
            NeoForge.EVENT_BUS.post(new StartRunEvent());
        }
    }

    @SubscribeEvent
    public static void OnRegisterCommands(RegisterCommandsEvent event)
    {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("StartRun")
                .requires(source -> source.hasPermission(4))
                .executes(context ->
                {

                    start(new StartRunContext(null));

                    return 1;
                })
        );
    }
}
