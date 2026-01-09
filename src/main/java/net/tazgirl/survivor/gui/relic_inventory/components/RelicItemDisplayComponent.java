package net.tazgirl.survivor.gui.relic_inventory.components;

import com.daqem.uilib.client.gui.component.ItemComponent;
import com.daqem.uilib.client.gui.component.scroll.ScrollBarComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.tazgirl.survivor.gui.relic_inventory.RelicInventoryScreen;

public class RelicItemDisplayComponent extends ItemComponent
{
    ItemStack itemStack;
    RelicInventoryScreen screen;
    ScrollBarComponent scrollBar;
    Float scale;

    public RelicItemDisplayComponent(int x, int y, ItemStack itemStack, RelicInventoryScreen screen)
    {
        super(0, screen.gap,x, y, itemStack, true);

        this.itemStack = itemStack;
        this.screen = screen;
        this.scrollBar = screen.scrollBar;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta)
    {
        float scaleMultiplier = screen.itemSize / 16f;
        graphics.pose().scale(scaleMultiplier, scaleMultiplier, 1);
        graphics.renderFakeItem(this.itemStack, 0, 0);
        graphics.pose().scale(1, 1, 1);
        graphics.renderItemDecorations(Minecraft.getInstance().font.self(), itemStack, 0,0);
    }

    public void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY)
    {
        int x = mouseX - scrollBar.getParent().getX();
        int y = mouseY + scrollBar.getScrollWheel().get().getScrollValue() - scrollBar.getParent().getY() - getParent().getY();
        graphics.renderTooltip(Minecraft.getInstance().font, itemStack, x, y);
    }
}
