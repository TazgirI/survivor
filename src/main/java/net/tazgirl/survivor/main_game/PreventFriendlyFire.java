package net.tazgirl.survivor.main_game;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.tazgirl.tutilz.events.EventPriorityPair;
import net.tazgirl.tutilz.events.priority_events.vanilla_overrides.LivingDamageEvent.LivingDamageOverride;
import net.tazgirl.tutilz.events.priority_events.vanilla_overrides.LivingDamageEvent.LivingDamageOverrideAccessor;

@EventBusSubscriber
public class PreventFriendlyFire
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void OnLivingAttack(LivingIncomingDamageEvent event)
    {
        if(event.getEntity() instanceof Player && event.getSource().getEntity() instanceof Player)
        {
            event.setCanceled(true);
        }
    }
}
