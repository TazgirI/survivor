package net.tazgirl.survivor.main_game.relics;

import net.tazgirl.survivor.main_game.players.PlayerData;
import net.tazgirl.survivor.main_game.players.PlayerEvent;

public enum RelicTrigger
{
    ON_WAVE_START;

    public static PlayerEvent<?> getEvent(PlayerData data, RelicTrigger trigger)
    {
        return switch (trigger)
        {
            case ON_WAVE_START -> data.waveStartTrigger;
        };
    }
}