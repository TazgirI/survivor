package net.tazgirl.survivor.main_game.mobs.modifiers.active;

import net.tazgirl.survivor.main_game.mobs.modifiers.Enums.ModifierArgTypes;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base.ModifierActiveBase;

import javax.annotation.Nullable;

public record ModifierActiveRecord(ModifierArgTypes type, @Nullable ModifierActiveBase finalisedModifierArgs)
{

}
