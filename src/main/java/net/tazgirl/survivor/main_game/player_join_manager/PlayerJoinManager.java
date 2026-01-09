package net.tazgirl.survivor.main_game.player_join_manager;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class PlayerJoinManager
{
    public static void Gamemode(ServerPlayer serverPlayer)
    {
        serverPlayer.gameMode.changeGameModeForPlayer(GameType.ADVENTURE);
    }
}
