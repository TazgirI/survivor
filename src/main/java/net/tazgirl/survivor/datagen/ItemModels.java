package net.tazgirl.survivor.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.vanilla_registration.Items;

public class ItemModels extends ItemModelProvider
{
    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper)
    {
        super(output, Survivor.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        basicItem(Items.UMBRELLA_SHIELD.get());
    }
}
