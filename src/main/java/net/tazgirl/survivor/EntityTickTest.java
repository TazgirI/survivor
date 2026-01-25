package net.tazgirl.survivor;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber
public class EntityTickTest
{

    @SubscribeEvent
    public static void OnEntityTick(EntityTickEvent.Post event)
    {

        if(event.getEntity() instanceof Skeleton skeleton && !event.getEntity().level().isClientSide())
        {
            System.out.println(skeleton.getActiveEffects());
        }

        if(event.getEntity() instanceof LivingEntity livingEntity && livingEntity.hasEffect(MobEffects.DAMAGE_BOOST))
        {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20));
        }

        if(event.getEntity() instanceof LivingEntity livingEntity && livingEntity.hasEffect(MobEffects.NIGHT_VISION))
        {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20));
        }
    }
}
