package net.tazgirl.survivor.gui.relic_inventory.components;

import com.daqem.uilib.api.client.gui.component.IComponent;
import com.daqem.uilib.api.client.gui.component.scroll.ScrollOrientation;
import com.daqem.uilib.client.gui.component.scroll.ScrollBarComponent;
import com.daqem.uilib.client.gui.component.scroll.ScrollContentComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.tazgirl.magicjson.SendMessage;
import net.tazgirl.survivor.gui.relic_inventory.RelicInventoryScreen;

import java.util.List;

public class RelicItemRowComponent extends ScrollContentComponent
{

    ScrollBarComponent scrollBar;
    RelicInventoryScreen screen;

    public RelicItemRowComponent(int x, int y, int contentSpacing, RelicInventoryScreen screen)
    {
        super(x, y, contentSpacing, ScrollOrientation.HORIZONTAL);
        this.scrollBar = screen.scrollBar;
        this.screen = screen;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta)
    {
        super.render(graphics, mouseX, mouseY, delta);
    }

    public ItemStack getTooltip(int mouseX, int mouseY)
    {
        int y = mouseY + scrollBar.getScrollWheel().get().getScrollValue() - scrollBar.getParent().getY() - screen.gap;

        int yDifferential = (getY() - y) * -1;

        if(yDifferential <= screen.itemSize - screen.gap && yDifferential >= -screen.gap)
        {
            int x = mouseX - scrollBar.getParent().getX() - screen.margin;

            int moduloMouseX = x % (getContentSpacing() + screen.itemSize);

            SendMessage.All("Mod Mouse X: " + moduloMouseX + "screen.gap: " + screen.gap + " screen.itemSize: " + screen.itemSize + "screen.margin: " + screen.margin);
            System.out.println("Mod Mouse X: " + moduloMouseX + "screen.gap: " + screen.gap + " screen.itemSize: " + screen.itemSize + "screen.margin: " + screen.margin);

            if(moduloMouseX - screen.gap - screen.margin <= screen.itemSize && moduloMouseX - screen.gap >= 0)
            {
                int index = x / (getContentSpacing() + screen.itemSize );

                if(index >= 0 & index <= getChildren().size() - 1)
                {
                    RelicItemDisplayComponent item = (RelicItemDisplayComponent) getChildren().get(index);
                    return item.itemStack;
                }
            }
        }

        return null;
    }
}
