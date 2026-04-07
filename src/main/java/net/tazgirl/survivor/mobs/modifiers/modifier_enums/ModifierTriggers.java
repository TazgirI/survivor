package net.tazgirl.survivor.mobs.modifiers.modifier_enums;

import net.tazgirl.survivor.mobs.ActiveMob;
import net.tazgirl.survivor.mobs.events.ActiveMobEvent;

import java.util.function.Function;

public enum ModifierTriggers
{
    SPAWN("spawn",activeMob -> activeMob.spawnTrigger),
    DAMAGE_PRE("damage_pre",activeMob -> activeMob.damageTriggerPre),
    DAMAGE_POST("damage_post", activeMob -> activeMob.damageTriggerPost);

    public final String name;
    public final Function<ActiveMob<?>, ActiveMobEvent<?>> get;


    ModifierTriggers(String name, Function<ActiveMob<?>, ActiveMobEvent<?>> get)
    {
        this.name = name;
        this.get = get;
    }
}
