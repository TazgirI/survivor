package net.tazgirl.survivor;

import net.tazgirl.survivor.misc_game_stuff.CoreGameData;

public class TickDistributor
{
    private static final int cycles = 20;

    private static int wrap = 0;

    public static DistributedTick newDistributedTick(double perSecond)
    {
        return new DistributedTick(increment(), perSecond);
    }

    private static int increment()
    {
        wrap++;
        if(wrap >= cycles)
        {
            wrap = 0;
        }

        return wrap;
    }

    public static class DistributedTick
    {
        private final int myTick;
        private double perSecond;
        private int interval;
        private int lerpedTick;

        private DistributedTick(int myTick, double perSecond)
        {
            if(perSecond <= 0)
            {
                throw new IllegalArgumentException("perSecond cannot be <= 0");
            }

            this.myTick = myTick;
            this.perSecond = perSecond;
            recalculateInterval();
            recalculateLerpedTick();
        }

        private void recalculateInterval()
        {
            interval = Math.toIntExact(Math.max(Math.round(cycles / perSecond), 1));
        }


        private void recalculateLerpedTick()
        {
            lerpedTick = Math.toIntExact(Math.round(myTick / perSecond));
        }

        public boolean isActivationTick()
        {
            return CoreGameData.zeroedTicks % interval == lerpedTick;
        }

        public double getPerSecond()
        {
            return perSecond;
        }

        public void setPerSecond(double perSecond)
        {
            if(perSecond <= 0)
            {
                throw new IllegalArgumentException("perSecond cannot be <= 0");
            }

            this.perSecond = perSecond;
            recalculateInterval();
            recalculateLerpedTick();
        }

        public int getMyTick()
        {
            return myTick;
        }

        public int getCycles()
        {
            return cycles;
        }
    }
}
