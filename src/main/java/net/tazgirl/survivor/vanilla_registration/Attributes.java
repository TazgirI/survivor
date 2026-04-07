package net.tazgirl.survivor.vanilla_registration;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tazgirl.survivor.Survivor;

@EventBusSubscriber
public class Attributes
{
    public static DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, Survivor.MODID);

    public static DeferredHolder<Attribute, Attribute> THREAT = ATTRIBUTES.register("threat", () -> new RangedAttribute("attribute.survivor.threat",0, -Double.MAX_VALUE, Double.MAX_VALUE));

    @SubscribeEvent
    public static void addAttributes(EntityAttributeModificationEvent event)
    {
        event.add(EntityType.PLAYER, THREAT.getDelegate());
    }

    public static void register(IEventBus eventBus)
    {
        ATTRIBUTES.register(eventBus);
    }
}
