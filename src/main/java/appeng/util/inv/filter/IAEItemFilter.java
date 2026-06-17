package appeng.util.inv.filter;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public interface IAEItemFilter {

    boolean allowExtract(IItemHandler inv, int slot, int amount);

    boolean allowInsert(IItemHandler inv, int slot, ItemStack stack);
}
