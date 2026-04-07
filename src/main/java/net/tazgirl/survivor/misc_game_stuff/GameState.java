package net.tazgirl.survivor.misc_game_stuff;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import java.util.List;

public class GameState
{
    public static State currentState = State.SETTING_UP;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerStarted(ServerStartedEvent event)
    {
        currentState = State.LOBBY;
    }

    public enum State
    {
        SETTING_UP, // Loading registers
        PREPARING, // Anytime assorted data loading is occurring to facilitate gameplay
        LOBBY, // While the game isn't being played and all players are in the lobby area
        WAVE_ACTIVE, // Wave has been spawned, players are currently in combat
        WAVE_PREPARE, // Wave has been defeated and the next wave is being generated
        WAVE_BREAK, // Wave has been generated, players can press the button to start next wave
        BUILDING; // For use while constructing or testing

        public boolean isPast(State state)
        {
            return orderedStates.indexOf(this) > orderedStates.indexOf(state);
        }

        public boolean isBefore(State state)
        {
            return orderedStates.indexOf(this) < orderedStates.indexOf(state);
        }

        public boolean isInGame()
        {
            int index = orderedStates.indexOf(this);
            return index >= 3 && index <= 5;
        }
    }

    // Review .isInGame() if modified
    static List<State> orderedStates = List.of(
            State.SETTING_UP,
            State.PREPARING,
            State.LOBBY,
            State.WAVE_ACTIVE,
            State.WAVE_PREPARE,
            State.WAVE_BREAK,
            State.BUILDING
    );
}
