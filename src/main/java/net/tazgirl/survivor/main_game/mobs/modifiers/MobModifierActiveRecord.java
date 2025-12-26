package net.tazgirl.survivor.main_game.mobs.modifiers;

import net.tazgirl.survivor.main_game.mobs.modifiers.Enums.ModifierTypes;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base.ModifierActiveBase;

import javax.annotation.Nullable;

public record MobModifierActiveRecord(ModifierTypes type, @Nullable ModifierActiveBase finalisedModifierArgs)
{

}
