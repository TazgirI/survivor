package net.tazgirl.survivor.player_join_manager;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.tazgirl.survivor.players.RegisterPlayer;

@EventBusSubscriber
public class PlayerJoinManager
{
    public static void gamemode(ServerPlayer serverPlayer)
    {
        serverPlayer.setGameMode(GameType.ADVENTURE);
    }

    public static void register(ServerPlayer serverPlayer)
    {
        RegisterPlayer.put(serverPlayer);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void OnPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if(event.getEntity() instanceof ServerPlayer serverPlayer)
        {
            gamemode(serverPlayer);
            register(serverPlayer);
        }
    }
}
