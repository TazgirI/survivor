package net.tazgirl.survivor.saved_data.registers.mobs.ai;

import com.google.gson.JsonElement;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tazgirl.survivor.SurvivorLogging;
import net.tazgirl.survivor.mobs.ActiveMob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RegisterAi
{
    private static final Map<String, RegisterAi> ais = new HashMap<>();

    abstract void setObjective(ActiveMob<?> activeMob);

    public static void aiTick(ActiveMob<?> activeMob)
    {
        RegisterAi ai = ais.get(activeMob.waveMob.getAiType());
        if(ai != null)
        {
            ai.setObjective(activeMob);
        }
        else
        {
            SurvivorLogging.Debug("WaveMob contains unrecognised ai type", List.of("WaveMob", activeMob.waveMob, "AiTypeString", activeMob.waveMob.getAiType(), "AiRegister", ais.toString()));
        }
    }

    private static RegisterAi addToMap(String address, RegisterAi ai)
    {
        return ais.put(address, ai);
    }

    @SubscribeEvent
    public static void onAiFetchEvent(RegisterAi.FetchEvent event)
    {
        event.add("survivor:hunter", new PlayerHunter());
    }

    public static String fromElement(JsonElement aiTypeElement)
    {
        if(aiTypeElement.isJsonPrimitive() && ais.containsKey(aiTypeElement.getAsString()))
        {
            return aiTypeElement.getAsString();
        }

        return "survivor:hunter";
    }

    public static class FetchEvent extends Event
    {
        public RegisterAi add(String address, RegisterAi ai)
        {
            return addToMap(address, ai);
        }

        @SubscribeEvent(priority = EventPriority.HIGH)
        public static void onServerStarting(ServerStartingEvent event)
        {
            NeoForge.EVENT_BUS.post(new RegisterAi.FetchEvent());
        }
    }
}
