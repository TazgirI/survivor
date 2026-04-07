package net.tazgirl.survivor.mobs.modifiers.active;

import java.util.ArrayList;
import java.util.List;

public class ModifierActiveSet
{
    private List<ModifierActiveRecord> modifiers = new ArrayList<>();

    public void addModifier(ModifierActiveRecord newModifier)
    {
        modifiers.add(newModifier);
    }

}
