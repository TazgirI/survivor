package net.tazgirl.survivor.saved_data.registers.modifier_group;

import java.util.*;

public class ModifierGroupRegister
{
    static final Map<String, List<String>> register = new HashMap<>();

    public static List<String> get(String address)
    {
        return register.get(address);
    }

    public static List<String> put(String address, List<String> value)
    {
        return register.put(address, value);
    }

    public static List<List<String>> putAll(Map<String, List<String>> map)
    {
        List<List<String>> returnList = new ArrayList<>();

        map.forEach((key, value) -> returnList.add(put(key, value)));

        return returnList;
    }

    public static List<List<String>> getList(List<String> addresses)
    {
        List<List<String>> returnList = new ArrayList<>();

        addresses.forEach(string -> returnList.add(get(string)));


        return returnList.stream().filter(Objects::nonNull).toList();
    }

    public static boolean hasAddress(String address)
    {
        return register.containsKey(address);
    }

    public static boolean hasList(List<String> value)
    {
        return register.containsValue(value);
    }

    public static String addressFromList(List<String> value)
    {
        if(!hasList(value))
        {
            return null;
        }

        for(Map.Entry<String, List<String>> entry : register.entrySet())
        {
            if(entry.getValue().equals(value))
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
