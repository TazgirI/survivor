package net.tazgirl.survivor.external_event_dispatchers;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.saved_data.registers.mob_sets.RegisterMobSets;
import net.tazgirl.survivor.saved_data.registers.modifier.RegisterModifierStorageRecord;
import net.tazgirl.survivor.saved_data.registers.modifier_group.RegisterModifierGroup;
import net.tazgirl.survivor.saved_data.registers.wave_mob.RegisterWaveMob;

@EventBusSubscriber(modid = Survivor.MODID)
public class ServerStopped
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void OnServerStopped(ServerStoppedEvent event)
    {
        RegisterMobSets.ClearWholeRegisterDanger();
        RegisterModifierStorageRecord.ClearWholeRegisterDanger();
        RegisterModifierGroup.ClearWholeRegisterDanger();
        RegisterWaveMob.ClearWholeRegisterDanger();
    }
}
