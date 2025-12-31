package net.tazgirl.survivor.main_game.mobs.wave_mobs;

import java.util.List;

public class WaveMobStorageSet
{
    public List<String> addresses;

    public WaveMobStorageSet(List<String> addresses)
    {
        this.addresses = addresses;
    }

    public WaveMobActiveSet construct()
    {
        return WaveMobActiveSet.fromAddressList(addresses);
    }

    @Override
    public String toString()
    {
        return addresses.toString();
    }
}
