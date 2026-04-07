package net.tazgirl.survivor.vanilla_registration;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.boons.BoonProperties;
import net.tazgirl.survivor.boons.BoonTrigger;
import net.tazgirl.survivor.boons.boons.UmbrellaShield;
import net.tazgirl.survivor.misc_items.InteractionPointTool;

public class Items
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Survivor.MODID);

    public static final DeferredItem<InteractionPointTool> INTERACTION_POINT_TOOL = ITEMS.register("interaction_point_tool", ()-> new InteractionPointTool(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<UmbrellaShield> UMBRELLA_SHIELD = ITEMS.register("umbrella_shield", ()-> new UmbrellaShield(new Item.Properties(), new BoonProperties().trigger(BoonTrigger.ON_WAVE_START)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
