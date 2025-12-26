package net.tazgirl.survivor.main_game.mobs.modifiers.Enums;

import net.neoforged.bus.api.Event;
import net.tazgirl.survivor.main_game.mobs.ActiveMob;
import net.tazgirl.survivor.main_game.mobs.ActiveMobEvent;

import java.util.function.Function;

public enum ModifierTriggers
{
    SPAWN(activeMob -> activeMob.spawnTrigger),
    DAMAGE_PRE(activeMob -> activeMob.damageTriggerPre),
    DAMAGE_POST(activeMob -> activeMob.damageTriggerPost);

    public final Function<ActiveMob, ActiveMobEvent<?>> get;


    ModifierTriggers(Function<ActiveMob, ActiveMobEvent<?>> get)
    {
        this.get = get;
    }
}
