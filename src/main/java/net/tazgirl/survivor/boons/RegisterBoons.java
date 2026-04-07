package net.tazgirl.survivor.boons;

import java.util.HashMap;
import java.util.Map;

public class RegisterBoons
{
    static Map<String, Boon> register = new HashMap<>();

    public static Boon put(String string, Boon boon)
    {
        return register.put(string, boon);
    }

}
