package net.tazgirl.survivor.main_game.mobs.modifiers.storage;

import net.tazgirl.survivor.Survivor;
import net.tazgirl.tutilz.admin.Logging;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class ModifierStorageSet
{
    private final List<ModifierStorageRecord<?>> modifiers = new ArrayList<>();

    private boolean dirty = false;

    private int totalModifierWeight;
    private List<ModifierStorageRecord<?>> modifiersDescendingCost;


    public ModifierStorageSet(List<ModifierStorageRecord<?>> constructRecords)
    {
        addSetOfMobModifiers(constructRecords);
    }

    public void UpdateAll()
    {
        if(IsModifiersEmpty()){return;}


        CalculateModifierWeight();
        CalculateModifiersByCostDescending();

        dirty = false;
    }

    public void addSetOfMobModifiers(Collection<ModifierStorageRecord<?>> constructRecords)
    {
        modifiers.addAll(constructRecords);
        dirty = false;
    }

    public void addMobModifier(ModifierStorageRecord<?> record)
    {
        modifiers.add(record);
        dirty = true;
    }

    public List<ModifierStorageRecord<?>> getModifiersByCostDescending()
    {
        if(IsModifiersEmpty()){return null;}

        if(dirty){UpdateAll();}

        return modifiersDescendingCost;
    }

    public List<ModifierStorageRecord<?>> getModifiers()
    {
        return new ArrayList<>(modifiers);
    }

    public List<ModifierStorageRecord<?>> getModifiersInBudget(int budget)
    {
        if(budget == 0 || IsModifiersEmpty()){return null;}

        List<ModifierStorageRecord<?>> returnList = new ArrayList<>();

        for(ModifierStorageRecord<?> record: modifiers)
        {
            if(record.cost() <= budget)
            {
                returnList.add(record);
            }
        }

        return returnList;
    }

    public ModifierStorageRecord<?> randomModifier()
    {
        if(IsModifiersEmpty()){return null;}

        return modifiers.get(new Random().nextInt(modifiers.size()));
    }

    public ModifierStorageRecord<?> randomModifierByWeight()
    {
        if(IsModifiersEmpty()){return null;}


        if(dirty){UpdateAll();}

        // TODO: Figure out what the +1 is for
        int index = new Random().nextInt(totalModifierWeight) + 1;
        int total = 0;

        for(ModifierStorageRecord<?> record: modifiers)
        {
            total += record.weight();

            if(total >= index)
            {
                return record;
            }
        }

        return modifiers.get(new Random().nextInt(modifiers.size()));
    }

    public void CalculateModifierWeight()
    {
        if(IsModifiersEmpty()){return;}


         totalModifierWeight = 0;

        for(ModifierStorageRecord<?> record: modifiers)
        {
             totalModifierWeight += record.weight();
        }
    }

    public void CalculateModifiersByCostDescending()
    {
        if(IsModifiersEmpty()){return;}


        modifiersDescendingCost = modifiers;
        modifiersDescendingCost.sort(Comparator.comparingInt(record -> ((ModifierStorageRecord<?>) record).cost()).reversed());
    }

    private boolean IsModifiersEmpty()
    {
        if(modifiers.isEmpty())
        {
            Logging.Error("Attempted to fetch a mobs modifiers but the list was empty", Survivor.LOGGER);
            return true;
        }

        return false;
    }

    @Override
    public String toString()
    {
        return modifiers.toString();
    }
}
