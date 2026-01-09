package net.tazgirl.survivor.gui.relic_inventory;

import com.daqem.uilib.api.client.gui.component.IComponent;
import com.daqem.uilib.api.client.gui.component.scroll.ScrollOrientation;
import com.daqem.uilib.client.gui.AbstractScreen;
import com.daqem.uilib.client.gui.background.Backgrounds;
import com.daqem.uilib.client.gui.component.scroll.ScrollBarComponent;
import com.daqem.uilib.client.gui.component.scroll.ScrollContentComponent;
import com.daqem.uilib.client.gui.component.scroll.ScrollPanelComponent;
import com.daqem.uilib.client.gui.component.scroll.ScrollWheelComponent;
import com.daqem.uilib.client.gui.texture.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.tazgirl.survivor.gui.relic_inventory.components.RelicItemDisplayComponent;
import net.tazgirl.survivor.gui.relic_inventory.components.RelicItemRowComponent;
import net.tazgirl.survivor.translation_keys.ui.relic_screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RelicInventoryScreen extends AbstractScreen
{
    public RelicInventoryScreen()
    {
        super(Component.translatable(relic_screen.MENU_NAME.getLocation()));
    }

    public int itemSize;
    public int gap;
    public ScrollBarComponent scrollBar;

    @Override
    public void startScreen()
    {
        int windowWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int windowHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        setBackground(Backgrounds.getDefaultBackground(windowWidth, windowHeight));



        int menuWidth = (int) ((float) windowWidth / 2 * scaleMultiplier());
        int menuHeight = (int) ((float) windowHeight / 2 * scaleMultiplier());


        int scrollThickness = menuWidth / 30;

        itemSize = (int) (16 * scaleMultiplier()) ;
        gap = itemSize / 6;

        int itemsPerRow = (menuWidth - scrollThickness - (gap * 2)) / (itemSize + gap);



        ScrollContentComponent scrollContentComponent = new ScrollContentComponent(0, 0, gap + itemSize, ScrollOrientation.VERTICAL);

        ScrollWheelComponent scrollWheelComponent = new ScrollWheelComponent(Textures.SCROLL_WHEEL, 0, 0, scrollThickness + 1);
        ScrollBarComponent scrollBarComponent = new ScrollBarComponent(menuWidth - scrollThickness, 0, scrollThickness, menuHeight + 1, ScrollOrientation.VERTICAL, scrollWheelComponent);
        scrollBar = scrollBarComponent;

        List<IComponent<?>> rowComponents = new ArrayList<>();

        List<ItemStack> items = getItems();
        int rows = items.size() / itemsPerRow + 1;

        for(int i = 0; i < rows; i++)
        {
            List<IComponent<?>> rowItems = new ArrayList<>();

            for(int j = 0; j < itemsPerRow; j++)
            {
                if(!items.isEmpty())
                {
                    rowItems.add(createItem(itemSize, itemSize, items.getFirst(),this));
                    items.removeFirst();
                }
            }

            RelicItemRowComponent row = createRow(0, 0, gap, this, rowItems);

            if(!row.getChildren().isEmpty())
            {
                rowComponents.add(row);
            }
        }

        scrollContentComponent.addChildren(rowComponents);

        ScrollPanelComponent scrollPanelComponent = new ScrollPanelComponent(Textures.SCROLL_PANE, 0, 0, menuWidth, menuHeight, ScrollOrientation.VERTICAL, scrollContentComponent, scrollBarComponent);
        scrollPanelComponent.center();

        this.addComponent(scrollPanelComponent);

    }

    @Override
    public void onTickScreen(GuiGraphics guiGraphics, int i, int i1, float v)
    {

    }

    public static float scaleMultiplier()
    {
        return 1 + Minecraft.getInstance().options.guiScale().get() * 0.25f;
    }

    public static RelicItemDisplayComponent createItem(int x, int y, ItemStack stack, RelicInventoryScreen screen)
    {
        return new RelicItemDisplayComponent(x, y, stack, screen);
    }

    public static RelicItemRowComponent createRow(int x, int y, int spacing, RelicInventoryScreen screen, List<IComponent<?>> items)
    {
        RelicItemRowComponent row = new RelicItemRowComponent(x, y, spacing, screen);
        row.addChildren(items);

        return row;
    }

    static List<ItemLike> tempItems = List.of(
            Items.WARPED_FENCE,
            Items.ANDESITE,
            Items.NAME_TAG,
            Items.APPLE,
            Items.BOOK,
            Items.DIAMOND,
            Items.ELYTRA,
            Items.CLOCK,
            Items.COMPASS,
            Items.CROSSBOW,
            Items.FLOWER_POT,
            Items.GOLDEN_APPLE,
            Items.IRON_INGOT
    );

    public static List<ItemStack> getItems()
    {
        // TODO: IMPLEMENT COMPONENT!!!
        List<ItemStack> returnList = new ArrayList<>();

        for(int i = 0; i < 100; i++)
        {
            returnList.add(new ItemStack(tempItems.get(new Random().nextInt(0, tempItems.size()))));
        }

        return returnList;
    }
}
