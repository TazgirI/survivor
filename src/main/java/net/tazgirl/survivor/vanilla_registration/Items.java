package net.tazgirl.survivor.vanilla_registration;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.main_game.relics.RelicProperties;
import net.tazgirl.survivor.main_game.relics.RelicTrigger;
import net.tazgirl.survivor.main_game.relics.relics.UmbrellaShield;

public class Items
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Survivor.MODID);

    public static final DeferredItem<Item> UMBRELLA_SHIELD = ITEMS.register("umbrella_shield", ()-> new UmbrellaShield(new Item.Properties(), new RelicProperties().trigger(RelicTrigger.ON_WAVE_START)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
