package net.tazgirl.survivor.translation_keys;

public enum keybinds
{
    OPEN_RELIC_MENU("survivor.keybinds.open_relic_menu"),
    SURVIVOR_CATEGORY("survivor.keybinds.category");

    private final String location;


    keybinds(String location)
    {
        this.location = location;
    }

    public String getLocation()
    {
        return this.location;
    }
}
