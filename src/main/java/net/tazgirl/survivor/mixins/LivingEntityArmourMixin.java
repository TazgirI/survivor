package net.tazgirl.survivor.mixins;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.survivor.buffs.Buff;
import net.tazgirl.survivor.buffs.BuffHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class, priority = 500)
public class LivingEntityArmourMixin
{
    @Unique
    private double survivor$armour;

    @Unique
    private final static ResourceLocation survivor$armourLocation = ResourceLocation.parse("survivor:armour_temp_buff");

    @Inject(method = "actuallyHurt",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F", shift = At.Shift.BEFORE))
    public void setArmour(DamageSource damageSource, float damageAmount, CallbackInfo ci)
    {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        survivor$armour = livingEntity.getAttributeValue(Attributes.ARMOR);

        if(CoreGameData.currentWave != null)
        {
            BuffHolder buffHolder = CoreGameData.currentWave.mobsInWave.get(livingEntity);
            AttributeInstance attributeInstance = livingEntity.getAttributes().getInstance(Attributes.ARMOR);

            if(buffHolder != null && attributeInstance != null)
            {
                double buffedValue = buffHolder.getBuffedValue(Buff.Type.ARMOUR, survivor$armour);
                attributeInstance.addPermanentModifier(new AttributeModifier(survivor$armourLocation, buffedValue - survivor$armour, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Inject(method = "actuallyHurt",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F", shift = At.Shift.AFTER))
    public void returnArmour(DamageSource damageSource, float damageAmount, CallbackInfo ci)
    {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        AttributeInstance attributeInstance = livingEntity.getAttributes().getInstance(Attributes.ARMOR);

        if(attributeInstance != null)
        {
            attributeInstance.removeModifier(survivor$armourLocation);
        }
    }

}
