package net.tazgirl.survivor.mixins;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.survivor.buffs.Buff;
import net.tazgirl.survivor.buffs.BuffHolder;
import net.tazgirl.survivor.players.RegisterPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, priority = 500)
public class PlayerArmourMixin
{
//    @Unique
//    private double survivor$armour;

    @Unique
    private final static ResourceLocation survivor$armourLocation = ResourceLocation.parse("survivor:armour_temp_buff");

    @Inject(method = "actuallyHurt",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F", shift = At.Shift.BEFORE))
    public void setArmour(DamageSource damageSource, float damageAmount, CallbackInfo ci)
    {
        Player player = (Player) (Object) this;

        double armour = player.getAttributeValue(Attributes.ARMOR);

        BuffHolder buffHolder = RegisterPlayer.get(player.getUUID());
        AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.ARMOR);

        if(buffHolder != null && attributeInstance != null)
        {
            double buffedValue = buffHolder.getBuffedValue(Buff.Type.ARMOUR, armour);
            attributeInstance.addPermanentModifier(new AttributeModifier(survivor$armourLocation, buffedValue - armour, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    @Inject(method = "actuallyHurt",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F", shift = At.Shift.AFTER))
    public void returnArmour(DamageSource damageSource, float damageAmount, CallbackInfo ci)
    {
        Player player = (Player) (Object) this;

        AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.ARMOR);

        if(attributeInstance != null)
        {
            attributeInstance.removeModifier(survivor$armourLocation);
        }
    }

}
