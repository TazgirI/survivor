package net.tazgirl.survivor.main_game.mobs.modifiers.storage;

import java.util.ArrayList;
import java.util.List;

public class CachedModifierStorageSet extends ModifierStorageSet
{
    public final List<CachedModifierStorageRecord> cachedModifiers = new ArrayList<>();

    public CachedModifierStorageSet(List<ModifierStorageRecord<?>> constructRecords)
    {
        super(constructRecords);

        for(ModifierStorageRecord<?> modifier : constructRecords)
        {
            cachedModifiers.add(modifier.constructCachedValues());
        }
    }
}
