package net.tazgirl.survivor.main_game.player_join_manager;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.tazgirl.survivor.main_game.players.RegisterPlayer;

public class PlayerJoinManager
{
    public static void Gamemode(ServerPlayer serverPlayer)
    {
        serverPlayer.setGameMode(GameType.ADVENTURE);
    }

    public static void register(ServerPlayer serverPlayer)
    {
        RegisterPlayer.put(serverPlayer);
    }
}
