package net.tazgirl.survivor.translation_keys.messages;

public enum mob_spawn_points
{
    SAVE_FAILURE("survivor.messages.mob_spawn_points.save_failure"),
    SAVE_SUCCESS("survivor.messages.mob_spawn_points.save_success"),
    CLEAR_SUCCESS("survivor.messages.mob_spawn_points.clear_success"),
    SHOW_SUCCESS("survivor.messages.mob_spawn_points.show_success");

    private final String location;


    mob_spawn_points(String location)
    {
        this.location = location;

    }

    public String getLocation()
    {
        return this.location;
    }

}
