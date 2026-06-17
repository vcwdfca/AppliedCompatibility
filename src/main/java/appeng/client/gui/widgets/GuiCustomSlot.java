package appeng.client.gui.widgets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class GuiCustomSlot {

    public abstract int xPos();

    public abstract int yPos();

    public abstract int getWidth();

    public abstract int getHeight();

    public boolean canClick(final EntityPlayer player) {
        return true;
    }

    public void slotClicked(final ItemStack clickStack, final int mouseButton) {
    }
}
