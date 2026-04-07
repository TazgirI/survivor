package net.tazgirl.survivor.mobs.modifiers.storage;

import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.tutilz.admin.Logging;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class ModifierStorageSet
{
    private final List<ModifierStorageRecord<?>> modifiers = new ArrayList<>();

    private List<CachedModifierStorageRecord> cachedModifiers = null;

    private boolean dirty = false;
    private boolean cacheMode = false;

    private int totalModifierWeight;
    private List<ModifierStorageRecord<?>> modifiersDescendingCost;


    public ModifierStorageSet(List<ModifierStorageRecord<?>> constructRecords)
    {
        addSetOfMobModifiers(constructRecords);
    }

    public void UpdateAll()
    {
        if(IsModifiersEmpty()){return;}

        if(cacheMode)
        {
            totalModifierWeight = cachedModifiers.stream().mapToInt(CachedModifierStorageRecord::weight).sum();
            cachedModifiers.sort(Comparator.comparingInt(CachedModifierStorageRecord::cost).reversed());
        }
        else
        {
            totalModifierWeight = modifiers.stream().mapToInt(ModifierStorageRecord::getWeight).sum();
            modifiers.sort(Comparator.comparingInt(record -> ((ModifierStorageRecord<?>) record).getCost()).reversed());
        }

        dirty = false;
    }

    public void addSetOfMobModifiers(Collection<ModifierStorageRecord<?>> constructRecords)
    {
        modifiers.addAll(constructRecords);
        dirty = true;
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

    public List<CachedModifierStorageRecord> getModifiersInBudget(int budget)
    {
        if(budget == 0 || IsModifiersEmpty()){return null;}

        List<CachedModifierStorageRecord> returnList = new ArrayList<>();

        if(cacheMode)
        {
            for(CachedModifierStorageRecord record: cachedModifiers)
            {
                if(record.cost() <= budget)
                {
                    returnList.add(record);
                }
            }
        }
        else
        {
            for(ModifierStorageRecord<?> record: modifiers)
            {
                if(record.cost().get() <= budget)
                {
                    returnList.add(record.constructCachedValues());
                }
            }
        }


        return returnList;
    }

    public CachedModifierStorageRecord randomModifier()
    {
        if(IsModifiersEmpty()){return null;}

        if(cacheMode)
        {
            return cachedModifiers.get(new Random().nextInt(cachedModifiers.size()));
        }
        else
        {
            return modifiers.get(new Random().nextInt(modifiers.size())).constructCachedValues();
        }
    }

    public CachedModifierStorageRecord randomModifierByWeight()
    {
        if(IsModifiersEmpty()){return null;}


        if(dirty || !cacheMode){UpdateAll();}

        // TO-not-DO: Figure out what the +1 is for
        // +1 is so Random can hit max index
        int index = new Random().nextInt(totalModifierWeight) + 1;
        int total = 0;

        if(cacheMode)
        {
            for(CachedModifierStorageRecord record: cachedModifiers)
            {
                total += record.weight();

                if(total >= index)
                {
                    return record;
                }
            }

            return cachedModifiers.get(new Random().nextInt(cachedModifiers.size()));
        }
        else
        {
            for(ModifierStorageRecord<?> record: modifiers)
            {
                total += record.weight().get();

                if(total >= index)
                {
                    return record.constructCachedValues();
                }
            }

            return modifiers.get(new Random().nextInt(modifiers.size())).constructCachedValues();
        }
    }

    public CachedModifierStorageRecord getCachedModifierByWeight(List<CachedModifierStorageRecord> modifierList)
    {
        int totalWeight = modifierList.stream().mapToInt(CachedModifierStorageRecord::weight).sum();

        // TO-not-DO: Figure out what the +1 is for
        // +1 is so Random can hit max index
        int index = new Random().nextInt(0, totalWeight) + 1;
        int total = 0;

        for(CachedModifierStorageRecord record: modifierList)
        {
            total += record.weight();

            if(total >= index)
            {
                return record;
            }
        }

        return cachedModifiers.get(new Random(CoreGameData.seedHolder.waveSeed.newSeed()).nextInt(cachedModifiers.size()));
    }

    public CachedModifierStorageRecord getInBudgetRandomByWeight(int budget)
    {
        List<CachedModifierStorageRecord> inBudget = getModifiersInBudget(budget);

        if(inBudget == null)
        {
            return null;
        }

        return getCachedModifierByWeight(inBudget);
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

    public void enterCacheMode()
    {
        cacheMode = true;

        cachedModifiers = new ArrayList<>();

        for(ModifierStorageRecord<?> modifier : modifiers)
        {
            cachedModifiers.add(modifier.constructCachedValues());
        }

    }

    public void exitCacheMode()
    {
        cacheMode = false;
    }

    public int cheapestCachedCost()
    {
        if(cacheMode && !cachedModifiers.isEmpty())
        {
            return cachedModifiers.stream().mapToInt(CachedModifierStorageRecord::cost).min().orElse(-1);
        }

        return -1;
    }

    @Override
    public String toString()
    {
        return modifiers.toString();
    }
}
