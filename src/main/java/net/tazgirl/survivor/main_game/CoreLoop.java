package net.tazgirl.survivor.main_game;

public class CoreLoop
{
    public static GameState gameState = GameState.SETTING_UP;


    public enum GameState
    {
        SETTING_UP, // Loading registers
        PREPARING, // Anytime assorted data loading is occurring to facilitate gameplay
        LOBBY, // While the game isn't being played and all players are in the lobby area
        WAVE_ACTIVE, // Wave has been spawned, players are currently in combat
        WAVE_PREPARE, // Wave has been defeated and the next wave is being generated
        WAVE_BREAK; // Wave has been generated, players can press the button to start next wave
    }
}
