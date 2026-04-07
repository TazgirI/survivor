package net.tazgirl.survivor.boons;

import net.tazgirl.survivor.players.PlayerData;
import net.tazgirl.survivor.players.PlayerEvent;

public enum BoonTrigger
{
    ON_WAVE_START;

    public static PlayerEvent<?> getEvent(PlayerData data, BoonTrigger trigger)
    {
        return switch (trigger)
        {
            case ON_WAVE_START -> data.waveStartTrigger;
        };
    }
}