package net.tazgirl.survivor.saved_data.registers.modifier;

import net.tazgirl.survivor.mobs.modifiers.storage.ModifierStorageRecord;
import net.tazgirl.tutilz.registers.RegistryEvent;

public class ModifierFetchEvent extends RegistryEvent<ModifierStorageRecord<?>>
{
    public ModifierFetchEvent()
    {
        super(RegisterModifierStorageRecord.register);
    }
}
