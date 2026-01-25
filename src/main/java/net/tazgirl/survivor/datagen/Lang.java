package net.tazgirl.survivor.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.vanilla_registration.Items;

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
    }
}
