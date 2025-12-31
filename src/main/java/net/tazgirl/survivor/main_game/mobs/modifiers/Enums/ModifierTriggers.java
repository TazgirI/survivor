package net.tazgirl.survivor.main_game.mobs.modifiers.Enums;

import net.neoforged.bus.api.Event;
import net.tazgirl.survivor.main_game.mobs.ActiveMob;
import net.tazgirl.survivor.main_game.mobs.ActiveMobEvent;

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
