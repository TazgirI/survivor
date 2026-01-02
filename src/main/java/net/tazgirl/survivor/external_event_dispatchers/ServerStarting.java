package net.tazgirl.survivor.external_event_dispatchers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.Inits.InitSavedData;
import net.tazgirl.survivor.saved_data.registers.mob_sets.MobSetsDataProcessing;
import net.tazgirl.survivor.saved_data.registers.mob_sets.MobSetsFetchEvent;
import net.tazgirl.survivor.saved_data.registers.modifier.ModifierDataProcessing;
import net.tazgirl.survivor.saved_data.registers.modifier.ModifierFetchEvent;
import net.tazgirl.survivor.saved_data.registers.modifier_group.ModifierGroupDataProcessing;
import net.tazgirl.survivor.saved_data.registers.modifier_group.ModifierGroupFetchEvent;
import net.tazgirl.survivor.saved_data.registers.wave_mob.WaveMobDataProcessing;
import net.tazgirl.survivor.saved_data.registers.wave_mob.WaveMobFetchEvent;

@EventBusSubscriber(modid = Survivor.MODID)
public class ServerStarting
{
    @SubscribeEvent
    public static void OnServerStarting(ServerStartingEvent event)
    {
        Globals.OnServerStarting(event);

        InitSavedData.Init();

        ModifierDataProcessing.LoopJsons();
        NeoForge.EVENT_BUS.post(new ModifierFetchEvent());

        ModifierGroupDataProcessing.LoopJsons();
        NeoForge.EVENT_BUS.post(new ModifierGroupFetchEvent());

        WaveMobDataProcessing.ProcessWaveMobData();
        NeoForge.EVENT_BUS.post(new WaveMobFetchEvent());

        MobSetsDataProcessing.LoopJsons();
        NeoForge.EVENT_BUS.post(new MobSetsFetchEvent());


    }

}
