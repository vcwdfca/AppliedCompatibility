package appeng.api.storage.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IAEItemStack extends IAEStack<IAEItemStack> {

    @Override
    void add(IAEItemStack option);

    @Override
    long getStackSize();

    @Override
    IAEItemStack setStackSize(long size);

    @Override
    IAEItemStack setCountRequestable(long countRequestable);

    @Override
    IAEItemStack setCraftable(boolean craftable);

    @Override
    IAEItemStack reset();

    @Override
    IAEItemStack copy();

    @Override
    IAEItemStack empty();

    ItemStack createItemStack();

    void writeToNBT(NBTTagCompound data);

    ItemStack getDefinition();

    Item getItem();

    int getItemDamage();

    boolean sameOre(IAEItemStack other);

    boolean isSameType(IAEItemStack other);

    boolean isSameType(ItemStack other);

    ItemStack getCachedItemStack(long size);

    void setCachedItemStack(ItemStack stack);
}
