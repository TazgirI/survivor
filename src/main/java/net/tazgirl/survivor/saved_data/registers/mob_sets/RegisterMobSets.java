package net.tazgirl.survivor.saved_data.registers.mob_sets;

import net.tazgirl.survivor.main_game.mobs.wave_mobs.WaveMobStorageSet;

import java.util.*;

public class RegisterMobSets
{
    static final Map<String, WaveMobStorageSet> register = new HashMap<>();

    public static WaveMobStorageSet get(String address)
    {
        return register.get(address);
    }

    public static WaveMobStorageSet put(String address, WaveMobStorageSet value)
    {
        return register.put(address, value);
    }

    public static List<WaveMobStorageSet> putAll(Map<String, WaveMobStorageSet> map)
    {
        List<WaveMobStorageSet> returnList = new ArrayList<>();

        map.forEach((key, value) -> returnList.add(put(key, value)));

        return returnList;
    }


    public static List<WaveMobStorageSet> getList(List<String> addresses)
    {
        List<WaveMobStorageSet> returnList = new ArrayList<>();

        addresses.forEach(string -> returnList.add(get(string)));


        return returnList.stream().filter(Objects::nonNull).toList();
    }


    public static boolean hasAddress(String address)
    {
        return register.containsKey(address);
    }

    public static boolean hasWaveMobStorageSet(WaveMobStorageSet record)
    {
        return register.containsValue(record);
    }

    public static String addressFromWaveMobStorageSet(WaveMobStorageSet record)
    {
        if(!hasWaveMobStorageSet(record))
        {
            return null;
        }

        for(Map.Entry<String, WaveMobStorageSet> entry : register.entrySet())
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

    public static void ClearWholeRegisterDanger()
    {
        register.clear();
    }
}
