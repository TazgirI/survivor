package net.tazgirl.survivor.main_game.mobs;

import java.util.*;
import java.util.function.Consumer;

public abstract class ActiveMobEvent<T extends ActiveMobEvent<T>>
{
    List<MobEventSubscription<T>> subscribers = new ArrayList<>();

    public void subscribe(Consumer<T> consumer, Integer priority)
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
        T event = newEvent();
        for(MobEventSubscription<T> subscription: subscribers)
        {
            subscription.consumer.accept(event);
        }
    }

    abstract T newEvent();

    public record MobEventSubscription<T>(Consumer<T> consumer, int priority)
    {

    }
}
