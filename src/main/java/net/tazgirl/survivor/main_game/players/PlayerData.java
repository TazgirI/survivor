package net.tazgirl.survivor.main_game.players;

import net.minecraft.server.level.ServerPlayer;
import net.tazgirl.survivor.main_game.buffs.BuffHolder;
import net.tazgirl.survivor.main_game.relics.Relic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData extends BuffHolder
{
    public UUID uuid;

    public List<Relic> relics = new ArrayList<>();

    public PlayerEvents.WaveStartTrigger waveStartTrigger = new PlayerEvents.WaveStartTrigger(this);

    public PlayerData(ServerPlayer player)
    {
        this.uuid = player.getUUID();
    }



}
