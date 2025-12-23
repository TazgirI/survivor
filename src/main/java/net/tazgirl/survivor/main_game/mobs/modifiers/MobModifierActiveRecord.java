package net.tazgirl.survivor.main_game.mobs.modifiers;

import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_arguments.Base;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_arguments.FinalisedBase;

import javax.annotation.Nullable;

public record MobModifierActiveRecord(ModifierTypes type, @Nullable FinalisedBase finalisedModifierArgs)
{

}
