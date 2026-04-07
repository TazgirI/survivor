package net.tazgirl.survivor.events.wave_setup;

import net.tazgirl.survivor.wave.Wave;
import net.tazgirl.tutilz.events.priority_events.EventAccessor;


public class WaveSetupEventAccessor extends EventAccessor<WaveSetupEventAccessor>
{
    public WaveSetupEventAccessor()
    {
        super(null);
    }

    public static class Pre extends WaveSetupEventAccessor
    {
        WaveSetupEvent.Pre pre;

        public Pre(WaveSetupEvent.Pre mySource)
        {
            pre = mySource;
        }
    }

    public static class Post extends WaveSetupEventAccessor
    {
        WaveSetupEvent.Post post;

        public Post(WaveSetupEvent.Post mySource)
        {
            post = mySource;
        }

        public Wave getIncomingWave()
        {
            return post.incomingWave;
        }
    }

}
