package net.tazgirl.survivor.main_game.registers;

import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tazgirl.survivor.main_game.mobs.WaveMob;

import java.util.ArrayList;
import java.util.List;

public class WaveMobRegister
{
    List<WaveMob<?>> waveMobs = new ArrayList<>();

    public static void OnServerStarting(ServerStartingEvent event)
    {

    }



}
