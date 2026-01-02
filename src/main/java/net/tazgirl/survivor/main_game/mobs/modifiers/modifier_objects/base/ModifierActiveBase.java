package net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base;

import net.tazgirl.survivor.main_game.mobs.ActiveMob;
import net.tazgirl.survivor.main_game.mobs.ActiveMobEvent;
import net.tazgirl.survivor.main_game.mobs.modifiers.storage.ModifierStorageRecord;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class ModifierActiveBase<T extends ModifierStorageArgs<T>>
{
    public ActiveMob<?> activeMob;

    public ModifierStorageRecord<T> storageRecord;

    public ActiveMobEvent<?> trigger;

    public ModifierActiveBase(ActiveMob<?> activeMob, ModifierStorageRecord<T> storageRecord, ActiveMobEvent<?> trigger)
    {
        this.activeMob = activeMob;
        this.storageRecord = storageRecord;
        this.trigger = trigger;
        trigger.subscribe(this::trigger, storageRecord.priority().get());
    }

    public abstract void trigger(ActiveMobEvent<?> event);

    public abstract void PutArgument(String string, Object object);

}
