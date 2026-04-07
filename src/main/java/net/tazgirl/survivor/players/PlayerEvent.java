package net.tazgirl.survivor.players;

import net.tazgirl.survivor.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class PlayerEvent<T extends PlayerEvent<T>>
{
    List<PlayerEventSubscription<T>> subscribers = new ArrayList<>();

    public PlayerData myPlayer;

    public PlayerEvent(PlayerData myPlayer)
    {
        this.myPlayer = myPlayer;
    }

    public void subscribe(Consumer<T> consumer, Priority priority)
    {
        int index = 0;

        for(PlayerEventSubscription<T> subscription: subscribers)
        {
            if(subscription.priority.value <= priority.value)
            {
                break;
            }
            index++;
        }


        subscribers.add(index, new PlayerEventSubscription<>(consumer, priority));
    }

    public void fire()
    {
        T event = newEvent();
        for(PlayerEventSubscription<T> subscription: subscribers)
        {
            subscription.consumer.accept(event);
        }
    }

    abstract T newEvent();

    public record PlayerEventSubscription<T>(Consumer<T> consumer, Priority priority)
    {

    }
}
