package net.tazgirl.survivor.mobs.events;

import net.neoforged.bus.api.Event;
import net.tazgirl.survivor.SurvivorLogging;
import net.tazgirl.survivor.mobs.ActiveMob;

public abstract class ActiveMobInheritanceEvent<E extends Event, T extends ActiveMobInheritanceEvent<E, T>> extends ActiveMobEvent<T>
{
    public final E sourceEvent;

    public ActiveMobInheritanceEvent(ActiveMob<?> myMob, E sourceEvent)
    {
        super(myMob);

        this.sourceEvent = sourceEvent;
    }

    @Override
    public void fire()
    {
        SurvivorLogging.Error("Called empty fire() on an inherited trigger");
        throw new RuntimeException("Called empty fire() on an inherited trigger");
    }

    @Override
    public T newEvent()
    {
        SurvivorLogging.Error("Called empty newEvent() on an inherited trigger");
        throw new RuntimeException("Called empty newEvent() on an inherited trigger");
    }

    public void fire(E sourceEvent)
    {
        T freshEvent = newEvent(sourceEvent);
        for(MobEventSubscription<T> subscription: subscribers)
        {
            subscription.consumer().accept(freshEvent);
        }
    }

    public abstract T newEvent(E sourceEvent);
}
