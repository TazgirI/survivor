package net.tazgirl.survivor.main_game.mobs.modifiers;

import net.tazgirl.survivor.Survivor;
import net.tazgirl.tutilz.admin.Logging;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class ModifierStorageSet
{
    private List<MobModifierStorageRecord> modifiers = new ArrayList<>();

    private boolean dirty = false;

    private int totalModifierWeight;
    private List<MobModifierStorageRecord> modifiersDescendingCost;


    public ModifierStorageSet(){}

    public ModifierStorageSet(List<MobModifierStorageRecord> constructRecords){addSetOfMobModifiers(constructRecords);}

    public void UpdateAll()
    {
        if(IsModifiersEmpty()){return;}


        CalculateModifierWeight();
        CalculateModifiersByCostDescending();

        dirty = false;
    }

    private void addSetOfMobModifiers(Collection<MobModifierStorageRecord> constructRecords)
    {
        modifiers.addAll(constructRecords);
        dirty = false;
    }

    public void AddMobModifier(MobModifierStorageRecord record)
    {
        modifiers.add(record);
        dirty = true;
    }

    public List<MobModifierStorageRecord> getModifiersByCostDescending()
    {
        if(IsModifiersEmpty()){return null;}

        if(dirty){UpdateAll();}

        return modifiersDescendingCost;
    }

    public List<MobModifierStorageRecord> getModifiers()
    {
        return new ArrayList<>(modifiers);
    }

    public List<MobModifierStorageRecord> getModifiersInBudget(int budget)
    {
        if(budget == 0 || IsModifiersEmpty()){return null;}

        List<MobModifierStorageRecord> returnList = new ArrayList<>();

        for(MobModifierStorageRecord record: modifiers)
        {
            if(record.cost() <= budget)
            {
                returnList.add(record);
            }
        }

        return returnList;
    }

    public MobModifierStorageRecord randomModifier()
    {
        if(IsModifiersEmpty()){return null;}

        return modifiers.get(new Random().nextInt(modifiers.size()));
    }

    public MobModifierStorageRecord randomModifierByWeight()
    {
        if(IsModifiersEmpty()){return null;}


        if(dirty){UpdateAll();}

        int index = new Random().nextInt(totalModifierWeight) + 1;
        int total = 0;

        for(MobModifierStorageRecord record: modifiers)
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

        for(MobModifierStorageRecord record: modifiers)
        {
             totalModifierWeight += record.weight();
        }
    }

    public void CalculateModifiersByCostDescending()
    {
        if(IsModifiersEmpty()){return;}


        modifiersDescendingCost = modifiers;
        modifiersDescendingCost.sort(Comparator.comparingInt(MobModifierStorageRecord::cost).reversed());
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

}
