package net.tazgirl.survivor.main_game.mobs.modifiers;

import java.util.ArrayList;
import java.util.List;

public class ModifierActiveSet
{
    private List<MobModifierActiveRecord> modifiers = new ArrayList<>();

    public void addModifier(MobModifierActiveRecord newModifier)
    {
        modifiers.add(newModifier);
    }

}
