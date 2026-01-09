package net.tazgirl.survivor.external_event_dispatchers;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.tazgirl.survivor.main_game.player_join_manager.PlayerJoinManager;

@EventBusSubscriber
public class PlayerJoinsServer
{
    @SubscribeEvent
    public static void OnPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if(event.getEntity() instanceof ServerPlayer serverPlayer)
        {
            PlayerJoinManager.Gamemode(serverPlayer);
        }
    }
}
