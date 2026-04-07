package net.tazgirl.survivor.saved_data.interactive_positions;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.events.CollectCommandBranchesEvent;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.tutilz.commands.ArgBranch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EventBusSubscriber
public class InteractionPointsCommandBranch
{
    final static String branchName = "interactionPoints";

    static final ArgBranch[] argPairs = List.of(
            ArgBranch.New("show", InteractionPointsCommandBranch::show),
            ArgBranch.New("hide", InteractionPointsCommandBranch::hide),
            ArgBranch.New("clear", InteractionPointsCommandBranch::clear)
    ).toArray(new ArgBranch[0]);

    @SubscribeEvent
    public static void OnRegisterCommands(CollectCommandBranchesEvent event)
    {
        event.AddBranch(branchName, argPairs);
    }

    public static boolean show(CommandContext<?> stack)
    {
        for(Map.Entry<BlockPos, String> entry : CoreGameData.getInteractionPositions().entrySet())
        {
            ManageInteractionPositions.addInteractionPositionVisual(entry.getKey(), entry.getValue());
        }

        InteractivePositionsSavedData.show = true;
        return true;
    }

    public static boolean hide(CommandContext<?> stack)
    {
        ServerLevel level = Globals.server.overworld();

        List<ArmorStand> armorStands = new ArrayList<>(level.getEntities(EntityTypeTest.forClass(ArmorStand.class), ManageInteractionPositions::hasName));

        for(ArmorStand armorStand : armorStands)
        {
            armorStand.discard();
        }

        InteractivePositionsSavedData.show = false;

        return true;
    }

    // Clears the entire saved data maps then hides any armor stands and resets the show flag that the hide function removes if necessary
    public static boolean clear(CommandContext<?> stack)
    {
        InteractivePositionsSavedData.getForOverworld().dangerClearAll();
        boolean oldShow = InteractivePositionsSavedData.show;
        hide(null);
        InteractivePositionsSavedData.show = oldShow;

        return true;
    }

}
