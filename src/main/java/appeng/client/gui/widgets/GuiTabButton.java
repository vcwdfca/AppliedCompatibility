package appeng.client.gui.widgets;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class GuiTabButton extends GuiButton {

    public GuiTabButton(final int x, final int y, final ItemStack ico, final String title, final RenderItem itemRenderer) {
        super(0, x, y, "");
    }
}
