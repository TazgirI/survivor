package net.tazgirl.survivor.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.vanilla_registration.Items;

import javax.annotation.Nullable;

public class Lang extends LanguageProvider
{
    public Lang(PackOutput output, String locale)
    {
        super(output, Survivor.MODID, locale);
    }

    @Override
    protected void addTranslations()
    {
        add(Items.UMBRELLA_SHIELD.get(), "Umbrella Shield");
        add(Items.INTERACTION_POINT_TOOL.get(), "Interaction Point Tool");
    }
}
