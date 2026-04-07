package net.tazgirl.survivor.mobs.modifiers.active;

import net.tazgirl.survivor.mobs.modifiers.modifier_enums.ModifierArgTypes;
import net.tazgirl.survivor.mobs.modifiers.modifier_objects.base.ModifierActiveBase;

import javax.annotation.Nullable;

public record ModifierActiveRecord(ModifierArgTypes type, @Nullable ModifierActiveBase finalisedModifierArgs)
{

}
