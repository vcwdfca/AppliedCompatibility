package appeng.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackHelper {

    public static NBTTagCompound stackToNBT(final ItemStack stack) {
        return new NBTTagCompound();
    }
}
