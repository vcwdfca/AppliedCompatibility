package appeng.container.slot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jspecify.annotations.NonNull;

public class SlotFake extends AppEngSlot {

    public SlotFake(final IItemHandler inv, final int idx, final int x, final int y) {
        super(inv, idx, x, y);
    }

    @Override
    public @NonNull ItemStack getStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public void putStack(final @NonNull ItemStack stack) {
    }
}
