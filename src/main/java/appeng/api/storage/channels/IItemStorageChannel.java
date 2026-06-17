package appeng.api.storage.channels;

import appeng.api.storage.IStorageChannel;
import appeng.api.storage.data.IAEStack;
import appeng.util.item.AEItemStack;
import net.minecraft.item.ItemStack;

public interface IItemStorageChannel extends IStorageChannel {

    default IAEStack createStack(final Object input) {
        if (input instanceof ItemStack stack) {
            return AEItemStack.fromItemStack(stack);
        }
        return null;
    }
}
