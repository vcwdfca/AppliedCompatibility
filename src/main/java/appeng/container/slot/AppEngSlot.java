package appeng.container.slot;

import net.minecraft.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AppEngSlot extends SlotItemHandler {

    public AppEngSlot(final IItemHandler inv, final int idx, final int x, final int y) {
        super(inv, idx, x, y);
    }

    public Slot setNotDraggable() {
        return this;
    }

    public boolean isSlotEnabled() {
        return true;
    }
}
