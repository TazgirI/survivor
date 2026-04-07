package net.tazgirl.survivor.players;

import net.neoforged.neoforge.common.NeoForge;
import net.tazgirl.survivor.events.WaveStartedEvent;

public class PlayerEvents
{

    public static class WaveStartTrigger extends PlayerEvent<WaveStartTrigger>
    {
        public WaveStartTrigger(PlayerData myPlayer)
        {
            super(myPlayer);

            NeoForge.EVENT_BUS.addListener(this::onWaveStart);
        }

        @Override
        WaveStartTrigger newEvent()
        {
            return new WaveStartTrigger(myPlayer);
        }

        public void onWaveStart(WaveStartedEvent event)
        {
            this.fire();
        }
    }
}
