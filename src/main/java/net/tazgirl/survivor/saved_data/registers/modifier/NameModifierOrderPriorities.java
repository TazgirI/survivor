package net.tazgirl.survivor.saved_data.registers.modifier;

public enum NameModifierOrderPriorities
{
    OPINION(8),
    SIZE(7),
    AGE(6),
    SHAPE(5),
    COLOUR(4),
    ORIGIN(3),
    MATERIAL(2),
    PURPOSE(1),
    EQUAL(0), // Currently unused index buffering TODO: Find use that retains readability
    POST_NOUN(-1),
    POST_TITLE(-2);


    public final int priority;

    static final NameModifierOrderPriorities[] values = NameModifierOrderPriorities.values(); // Cache for repeated calls? Called for every ModifierStorageRecord json that has the name_modifier object so may be useful, no good way to soft remove the reference once most construction is completer. i.e after server started it just calls .values() each time as hotswapping is supported.
    static final int highestPriority = 8; // Irrelevant if you put the enum in reverse order, I prefer it being in accurate priority call order

    NameModifierOrderPriorities(int priority)
    {
        this.priority = priority;
    }

    public static NameModifierOrderPriorities get(String string)
    {

        for(NameModifierOrderPriorities priority : NameModifierOrderPriorities.values())
        {
            if(priority.name().equalsIgnoreCase(string))
            {
                return priority;
            }
        }


        try
        {
            return NameModifierOrderPriorities.values[highestPriority - Integer.parseInt(string)];
        }
        catch (Exception e)
        {
            return null;
        }
    }

}

