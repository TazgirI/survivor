package net.tazgirl.survivor.gui.relic_inventory.components;

import com.daqem.uilib.client.gui.component.ItemComponent;
import com.daqem.uilib.client.gui.component.scroll.ScrollBarComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.client.ClientTooltipFlag;
import net.tazgirl.survivor.gui.relic_inventory.RelicInventoryScreen;

import java.util.List;
import java.util.stream.Collectors;

public class RelicItemDisplayComponent extends ItemComponent
{
    ItemStack itemStack;
    RelicInventoryScreen screen;
    ScrollBarComponent scrollBar;
    Float scale;

    public RelicItemDisplayComponent(int x, int y, ItemStack itemStack, RelicInventoryScreen screen)
    {
        super(screen.margin, 0,x, y, itemStack, true);

        this.itemStack = itemStack;
        this.screen = screen;
        this.scrollBar = screen.scrollBar;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta)
    {
        float scaleMultiplier = screen.itemSize / 16f;
        graphics.pose().scale(scaleMultiplier, scaleMultiplier, 1);
        graphics.renderFakeItem(this.itemStack, RelicInventoryScreen.guiScale() != 3 ? screen.margin : (int) (screen.margin - (screen.gap * 0.5)), 0);
        graphics.pose().scale(1, 1, 1);
        graphics.renderItemDecorations(Minecraft.getInstance().font.self(), itemStack, screen.margin,0);
    }

    public void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY)
    {
        int x = mouseX - scrollBar.getParent().getX();
        int y = mouseY + scrollBar.getScrollWheel().get().getScrollValue() - scrollBar.getParent().getY() - getParent().getY();
        renderTooltipsBase(graphics, x, y, 0);
        screen.renderTooltip(graphics, itemStack,x,y);
    }


}