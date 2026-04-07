package net.tazgirl.survivor.mobs.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;
import net.tazgirl.survivor.Priority;
import net.tazgirl.survivor.mobs.ActiveMob;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

public abstract class ActiveMobEvent<T extends ActiveMobEvent<T>>
{
    List<MobEventSubscription<T>> subscribers = new ArrayList<>();

    public final ActiveMob<?> myMob;

    public abstract Entity getSecondaryEntity();

    public ActiveMobEvent(ActiveMob<?> myMob)
    {
        this.myMob = myMob;
    }

    public void subscribe(Consumer<T> consumer, Priority priority)
    {
        subscribe(consumer, priority.value);
    }

    private void subscribe(Consumer<T> consumer, Integer priority)
    {
        int index = 0;

        for(MobEventSubscription<T> subscription: subscribers)
        {
            if(subscription.priority <= priority)
            {
               break;
            }
            index++;
        }


        subscribers.add(index, new MobEventSubscription<>(consumer, priority));
    }

    public void fire()
    {
        T freshEvent = newEvent();
        for(MobEventSubscription<T> subscription: subscribers)
        {
            subscription.consumer.accept(freshEvent);
        }
    }

    public abstract T newEvent();

    public record MobEventSubscription<T>(Consumer<T> consumer, int priority)
    {

    }
}
