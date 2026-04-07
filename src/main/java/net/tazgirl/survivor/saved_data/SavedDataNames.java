package net.tazgirl.survivor.saved_data;

public enum SavedDataNames
{
    MOB_SPAWN_POINTS("MobSpawnEntries"),
    INTERACTION_POINTS_ADDRESS("InteractivePositions");


    public final String string;

    SavedDataNames(String string)
    {
        this.string = string;
    }
}
