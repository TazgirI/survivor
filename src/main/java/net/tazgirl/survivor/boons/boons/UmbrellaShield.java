package net.tazgirl.survivor.boons.boons;

import net.tazgirl.survivor.Priority;
import net.tazgirl.survivor.buffs.Buff;
import net.tazgirl.survivor.buffs.BuffFetchContext;
import net.tazgirl.survivor.buffs.TimeBuff;
import net.tazgirl.survivor.players.PlayerData;
import net.tazgirl.survivor.players.PlayerEvents;
import net.tazgirl.survivor.boons.Boon;
import net.tazgirl.survivor.boons.BoonProperties;

import java.util.function.Consumer;

public class UmbrellaShield extends Boon<PlayerEvents.WaveStartTrigger>
{
    public UmbrellaShield(Properties properties, BoonProperties boonProperties)
    {
        super(properties, boonProperties);
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
