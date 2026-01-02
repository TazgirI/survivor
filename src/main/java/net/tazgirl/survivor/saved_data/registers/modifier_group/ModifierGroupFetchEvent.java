package net.tazgirl.survivor.saved_data.registers.modifier_group;

import net.tazgirl.tutilz.registers.RegistryEvent;

import java.util.List;

public class ModifierGroupFetchEvent extends RegistryEvent<List<String>>
{
    public ModifierGroupFetchEvent()
    {
        super(RegisterModifierGroup.register);
    }
}
