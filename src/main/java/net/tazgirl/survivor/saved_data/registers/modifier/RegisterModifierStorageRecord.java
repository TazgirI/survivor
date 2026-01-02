package net.tazgirl.survivor.saved_data.registers.modifier;

import net.tazgirl.survivor.main_game.mobs.modifiers.storage.ModifierStorageRecord;

import java.util.*;

public class RegisterModifierStorageRecord
{
    static final Map<String, ModifierStorageRecord<?>> register = new HashMap<>();

    public static ModifierStorageRecord<?> get(String address)
    {
        return register.get(address);
    }

    public static List<ModifierStorageRecord<?>> getList(List<String> addresses)
    {
        List<ModifierStorageRecord<?>> returnList = new ArrayList<>();

        addresses.forEach(string -> returnList.add(get(string)));


        return returnList.stream().filter(Objects::nonNull).toList();
    }

    public static void put(String address, ModifierStorageRecord<?> record)
    {
        register.put(address, record);
    }

    public static boolean hasAddress(String address)
    {
        return register.containsKey(address);
    }

    public static boolean hasRecord(ModifierStorageRecord<?> record)
    {
        return register.containsValue(record);
    }

    public static String recordToAddress(ModifierStorageRecord<?> record)
    {
        if(!hasRecord(record))
        {
            return null;
        }

        for(Map.Entry<String, ModifierStorageRecord<?>> entry : register.entrySet())
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
