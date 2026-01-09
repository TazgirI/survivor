package net.tazgirl.survivor.translation_keys.ui;

public enum relic_screen
{
    MENU_NAME("survivor.ui.relic_screen.menu_name");

    private final String location;


    relic_screen(String location)
    {
        this.location = location;
    }

    public String getLocation()
    {
        return this.location;
    }
}
