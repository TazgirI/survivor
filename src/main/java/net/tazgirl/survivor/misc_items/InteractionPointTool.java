package net.tazgirl.survivor.misc_items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.tazgirl.survivor.saved_data.interactive_positions.InteractivePositionsSavedData;
import net.tazgirl.survivor.saved_data.interactive_positions.RegisterInteractionPositions;
import net.tazgirl.tutilz.misc_helpers.RaycastHelpers;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class InteractionPointTool extends Item
{
    public InteractionPointTool(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand)
    {
        if(player.isCrouching() && !level.isClientSide())
        {
            cycle(player.getItemInHand(usedHand));
        }

        return super.use(level, player, usedHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Player player = context.getPlayer();

        if(player == null)
        {
            return InteractionResult.PASS;
        }

        if(RaycastHelpers.isSelectingBlock(player))
        {
            player.startUsingItem(context.getHand());
        }
        else if(player.isCrouching())
        {
            cycle(player.getItemInHand(context.getHand()));
        }


        return InteractionResult.SUCCESS_NO_ITEM_USED;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity)
    {
        if(livingEntity instanceof ServerPlayer player && player.gameMode.isCreative())
        {
            if(player.isCrouching())
            {
                cycle(stack);
            }
            else
            {
                Component nameComponent = stack.get(DataComponents.CUSTOM_NAME);

                if(nameComponent == null)
                {
                    cycle(stack);
                }
                else
                {
                    HitResult hitResult = RaycastHelpers.playerBlockCheck(player);
                    if(RaycastHelpers.isTypeBlock(hitResult))
                    {
                        Vec3 position = RaycastHelpers.translateHitPos(hitResult);
                        if(nameComponent.getString().equals("REMOVE"))
                        {
                            InteractivePositionsSavedData.getForOverworld().removeInteractionPoint(BlockPos.containing(position));
                        }
                        else
                        {
                            InteractivePositionsSavedData.getForOverworld().addInteractionPoint(BlockPos.containing(position), nameComponent.getString());
                        }
                    }
                }
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.SPYGLASS;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity)
    {
        return 10;
    }

    public void cycle(ItemStack stack)
    {
        Component nameComponent = stack.get(DataComponents.CUSTOM_NAME);
        String name = "";
        if(nameComponent != null)
        {
            name = nameComponent.getString();
        }


        String first = null;
        boolean flag = name.equals("REMOVE");
        for(String string : RegisterInteractionPositions.keySet())
        {
            if(first == null)
            {
                first = string;
            }
            if(flag || name.isEmpty())
            {
                name = string;
                flag = false;
                break;
            }
            if(string.equals(name))
            {
                flag = true;
            }
        }
        if(flag)
        {
            name = "REMOVE";
        }

        stack.set(DataComponents.CUSTOM_NAME, Component.literal(name));
    }

}
