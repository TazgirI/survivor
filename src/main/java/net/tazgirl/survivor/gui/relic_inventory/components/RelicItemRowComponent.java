package net.tazgirl.survivor.gui.relic_inventory.components;

import com.daqem.uilib.api.client.gui.component.IComponent;
import com.daqem.uilib.api.client.gui.component.scroll.ScrollOrientation;
import com.daqem.uilib.client.gui.component.scroll.ScrollBarComponent;
import com.daqem.uilib.client.gui.component.scroll.ScrollContentComponent;
import net.minecraft.client.gui.GuiGraphics;
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

        int y = mouseY + scrollBar.getScrollWheel().get().getScrollValue() - scrollBar.getParent().getY() - screen.gap;

        graphics.fill(mouseX - scrollBar.getParent().getX() - 5, y - 5, mouseX - scrollBar.getParent().getX() + 5, y + 5, 0x80FF0000);



        int yDifferential = (getY() - y) * -1;

        if(yDifferential <= screen.itemSize && yDifferential >= 0)
        {

            SendMessage.All(String.valueOf(yDifferential));

            int x = mouseX - scrollBar.getParent().getX();

            IComponent<?> child = getChildren().get(0);

            int moduloMouseX = x % (getContentSpacing() + child.getWidth());

            graphics.fill(moduloMouseX - 5, getY() - 5, moduloMouseX + 5, getY() + 5, 0x80FF0000);


            if(moduloMouseX <= child.getWidth())
            {
                int index = x / (getContentSpacing() + child.getWidth());

                if(index >= 0 & index <= getChildren().size() - 1)
                {
                    RelicItemDisplayComponent item = (RelicItemDisplayComponent) getChildren().get(index);
                    item.renderTooltip(graphics, mouseX, mouseY);
                }
            }
        }
        // Get if mouse is at correct y for this scroll content
        // Get if mouse is at the x between width values then find the multiple of content spacing (50) to get the index to render a tooltip for

    }
}
