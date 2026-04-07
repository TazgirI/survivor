package net.tazgirl.survivor;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber
public class EntityTickTest
{

    @SubscribeEvent
    public static void OnEntityTick(EntityTickEvent.Post event)
    {
        // RegisterInteractivePositions.executeForBlock(null, null);
    }
}
