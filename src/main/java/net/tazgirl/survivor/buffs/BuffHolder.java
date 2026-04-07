package net.tazgirl.survivor.buffs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public abstract class BuffHolder
{
    public List<Buff> buffs = new ArrayList<>();

    public double getBuffedValue(Buff.Type type, double originalValue)
    {
        List<Buff> valid = new ArrayList<>();
        double newValue = 0;

        for(Iterator<Buff> it = buffs.iterator(); it.hasNext(); )
        {
            Buff buff = it.next();

            if(buff.type == type)
            {
                if(buff.expired())
                {
                    it.remove();
                }
                else if(buff.valid(new BuffFetchContext(false)))
                {
                    valid.add(buff);
                }
            }
        }

        valid.sort(Comparator.comparingInt(b -> b.priority.value));

        for(Buff buff: valid)
        {
            Buff.Modifier modifier = buff.modifier;
            if(modifier == Buff.Modifier.RAW)
            {
                newValue += buff.value;
            }
            else if(modifier == Buff.Modifier.ORIGINAL_PERCENTILE)
            {
                newValue = originalValue * (1 + buff.value);
            }
            else
            {
                newValue *= (1 + buff.value);
            }
        }

        return newValue;
    }

}
