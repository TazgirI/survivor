package net.tazgirl.survivor.main_game.players;

public class PlayerEvents
{
    public static class WaveStartTrigger extends PlayerEvent<WaveStartTrigger>
    {
        public WaveStartTrigger(PlayerData myPlayer)
        {
            super(myPlayer);
        }

        @Override
        WaveStartTrigger newEvent()
        {
            return new WaveStartTrigger(myPlayer);
        }
    }
}
