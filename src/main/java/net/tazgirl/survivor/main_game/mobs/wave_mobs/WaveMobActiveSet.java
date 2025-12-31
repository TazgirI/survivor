package net.tazgirl.survivor.main_game.mobs.wave_mobs;

import net.tazgirl.survivor.saved_data.registers.wave_mob.WaveMobRegister;

import java.util.ArrayList;
import java.util.List;

public class WaveMobActiveSet
{
    public final List<WaveMob<?>> waveMobs;


    public WaveMobActiveSet(List<WaveMob<?>> waveMobs)
    {
        this.waveMobs = waveMobs;
    }

    public static WaveMobActiveSet fromAddressList(List<String> addresses)
    {
        List<WaveMob<?>> mobs = new ArrayList<>(WaveMobRegister.getList(addresses));
        return new WaveMobActiveSet(mobs);
    }


}
