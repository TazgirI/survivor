package net.tazgirl.survivor.saved_data.registers.wave_mob;

import net.tazgirl.survivor.main_game.mobs.wave_mobs.WaveMob;

import java.util.*;

public class WaveMobRegister
{
    static final Map<String, WaveMob<?>> register = new HashMap<>();

    public static WaveMob<?> get(String address)
    {
        return register.get(address);
    }

    public static WaveMob<?> put(String address, WaveMob<?> value)
    {
        return register.put(address, value);
    }

    public static List<WaveMob<?>> putAll(Map<String, WaveMob<?>> map)
    {
        List<WaveMob<?>> returnList = new ArrayList<>();

        map.forEach((key, value) -> returnList.add(put(key, value)));

        return returnList;
    }


    public static List<WaveMob<?>> getList(List<String> addresses)
    {
        List<WaveMob<?>> returnList = new ArrayList<>();

        addresses.forEach(string -> returnList.add(get(string)));


        return returnList.stream().filter(Objects::nonNull).toList();
    }



    public static boolean hasAddress(String address)
    {
        return register.containsKey(address);
    }

    public static boolean hasWaveMob(WaveMob<?> record)
    {
        return register.containsValue(record);
    }

    public static String addressFromWaveMob(WaveMob<?> record)
    {
        if(!hasWaveMob(record))
        {
            return null;
        }

        for(Map.Entry<String, WaveMob<?>> entry : register.entrySet())
        {
            if(entry.getValue().equals(record))
            {
                return entry.getKey();
            }
        }

        return null;
    }

    public static String registerString()
    {
        return register.toString();
    }

}
