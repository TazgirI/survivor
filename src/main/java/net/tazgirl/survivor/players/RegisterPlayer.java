package net.tazgirl.survivor.players;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.GameType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterPlayer
{
    private static Map<UUID, PlayerData> register = new HashMap<>();

    public static PlayerData get(UUID uuid)
    {
        return register.get(uuid);
    }

    public static Collection<PlayerData> values()
    {
        return register.values();
    }

    public static Collection<PlayerData> livingValues()
    {
        return register.values().stream().filter(data -> !data.player.gameMode.getGameModeForPlayer().equals(GameType.SPECTATOR)).toList();
    }

    public static Collection<PlayerData> deadValues()
    {
        return register.values().stream().filter(data -> data.player.gameMode.getGameModeForPlayer().equals(GameType.SPECTATOR)).toList();
    }

    public static void put(ServerPlayer player)
    {
        if(!register.containsKey(player.getUUID()))
        {
            register.put(player.getUUID(), new PlayerData(player));
        }
    }

    public static void clearWholeRegisterDanger()
    {
        register = new HashMap<>();
    }
}
