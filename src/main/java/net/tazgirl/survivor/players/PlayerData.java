package net.tazgirl.survivor.players;

import net.minecraft.server.level.ServerPlayer;
import net.tazgirl.survivor.buffs.BuffHolder;
import net.tazgirl.survivor.boons.Boon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData extends BuffHolder
{
    public final ServerPlayer player;

    public final UUID uuid;

    public List<Boon> boons = new ArrayList<>();

    public long tickWhenRightClick = -1;

    public PlayerEvents.WaveStartTrigger waveStartTrigger = new PlayerEvents.WaveStartTrigger(this);

    public PlayerData(ServerPlayer player)
    {
        this.player = player;
        this.uuid = player.getUUID();
    }



}
