package net.tazgirl.survivor.main_game.relics;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.HashMap;
import java.util.Map;

public class RegisterRelics
{
    static Map<String, Relic> register = new HashMap<>();

    public static Relic put(String string, Relic relic)
    {
        return register.put(string, relic);
    }

}
