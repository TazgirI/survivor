package net.tazgirl.survivor.main_game.players;

import net.minecraft.server.level.ServerPlayer;

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
