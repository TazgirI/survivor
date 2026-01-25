package net.tazgirl.survivor.main_game.relics.relics;

import net.tazgirl.survivor.Priority;
import net.tazgirl.survivor.main_game.buffs.Buff;
import net.tazgirl.survivor.main_game.buffs.BuffFetchContext;
import net.tazgirl.survivor.main_game.buffs.TimeBuff;
import net.tazgirl.survivor.main_game.players.PlayerData;
import net.tazgirl.survivor.main_game.players.PlayerEvent;
import net.tazgirl.survivor.main_game.players.PlayerEvents;
import net.tazgirl.survivor.main_game.relics.Relic;
import net.tazgirl.survivor.main_game.relics.RelicProperties;

import java.util.function.Consumer;

public class UmbrellaShield extends Relic<PlayerEvents.WaveStartTrigger>
{
    public UmbrellaShield(Properties properties, RelicProperties relicProperties)
    {
        super(properties, relicProperties);
    }

    @Override
    public PlayerEvents.WaveStartTrigger getEvent(PlayerData data)
    {
        return data.waveStartTrigger;
    }

    @Override
    public Consumer<PlayerEvents.WaveStartTrigger> eventConsumer()
    {
        return UmbrellaShield::onWaveStart;
    }

    public static void onWaveStart(PlayerEvents.WaveStartTrigger trigger)
    {
        trigger.myPlayer.buffs.add(new UmbrellaBuff(Buff.Type.ARMOUR, Priority.LOW, 200, 10));
    }

    static class UmbrellaBuff extends TimeBuff
    {

        public UmbrellaBuff(Type type, Priority priority, int duration, double value)
        {
            super(type, priority, duration, value);
        }

        @Override
        public boolean valid(BuffFetchContext context)
        {
            return true;
        }
    }
}
