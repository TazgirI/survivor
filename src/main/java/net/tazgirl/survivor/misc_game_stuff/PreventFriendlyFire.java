package net.tazgirl.survivor.misc_game_stuff;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

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
