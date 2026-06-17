package appeng.api.storage.data;

import net.minecraft.item.ItemStack;

public interface IAEItemStack extends IAEStack {

    long getStackSize();

    IAEStack setStackSize(long size);

    ItemStack createItemStack();
}
