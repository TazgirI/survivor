package net.tazgirl.survivor.main_game.registers;

import net.tazgirl.survivor.main_game.mobs.modifiers.ModifierStorageRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobModifierStorageRecordRegister
{
    private static final Map<String, ModifierStorageRecord> storageRecords = new HashMap<>();

    public static ModifierStorageRecord get(String address)
    {
        return storageRecords.get(address);
    }

    public static List<ModifierStorageRecord> getList(List<String> addresses)
    {
        List<ModifierStorageRecord> returnList = new ArrayList<>();

        addresses.forEach(string -> returnList.add(get(string)));

        return returnList;
    }

}
