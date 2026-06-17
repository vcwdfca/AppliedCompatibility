package appeng.parts.automation;

import appeng.api.config.Upgrades;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;

public class UpgradeInventory implements IItemHandlerModifiable {

    protected final ItemStack[] stacks;

    public UpgradeInventory() {
        this(5);
    }

    public UpgradeInventory(final int slots) {
        this.stacks = new ItemStack[slots];
        for (int i = 0; i < slots; i++) {
            this.stacks[i] = ItemStack.EMPTY;
        }
    }

    public int getInstalledUpgrades(final Upgrades u) {
        return 0;
    }

    @Override
    public int getSlots() {
        return this.stacks.length;
    }

    @Override
    public @NonNull ItemStack getStackInSlot(final int slot) {
        return this.stacks[slot];
    }

    @Override
    public void setStackInSlot(final int slot, final @NonNull ItemStack stack) {
        this.stacks[slot] = stack == null ? ItemStack.EMPTY : stack;
    }

    @Override
    public int getSlotLimit(final int slot) {
        return 1;
    }

    @Override
    public @NonNull ItemStack insertItem(final int slot, final @NonNull ItemStack stack, final boolean simulate) {
        if (stack == null || stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        if (!this.stacks[slot].isEmpty()) {
            return stack;
        }
        if (!simulate) {
            final ItemStack toStore = stack.copy();
            toStore.setCount(1);
            this.stacks[slot] = toStore;
        }
        if (stack.getCount() <= 1) {
            return ItemStack.EMPTY;
        }
        final ItemStack remainder = stack.copy();
        remainder.shrink(1);
        return remainder;
    }

    @Override
    public @NonNull ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
        if (amount <= 0 || this.stacks[slot].isEmpty()) {
            return ItemStack.EMPTY;
        }
        final ItemStack existing = this.stacks[slot];
        if (!simulate) {
            this.stacks[slot] = ItemStack.EMPTY;
        }
        return existing.copy();
    }

    public void readFromNBT(final NBTTagCompound data, final String name) {
        Arrays.fill(this.stacks, ItemStack.EMPTY);
        final NBTTagList list = data.getTagList(name, 10);
        for (int i = 0; i < list.tagCount(); i++) {
            final NBTTagCompound tag = list.getCompoundTagAt(i);
            final int slot = tag.getByte("Slot") & 255;
            if (slot >= 0 && slot < this.stacks.length) {
                this.stacks[slot] = new ItemStack(tag);
            }
        }
    }

    public void writeToNBT(final NBTTagCompound data, final String name) {
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.stacks.length; i++) {
            if (!this.stacks[i].isEmpty()) {
                final NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                this.stacks[i].writeToNBT(tag);
                list.appendTag(tag);
            }
        }
        data.setTag(name, list);
    }
}
