package appeng.tile.inventory;

import appeng.util.inv.IAEAppEngInventory;
import appeng.util.inv.InvOperation;
import appeng.util.inv.filter.IAEItemFilter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;

public class AppEngInternalInventory implements IItemHandlerModifiable {

    private final IAEAppEngInventory host;
    private final ItemStack[] stacks;
    private final int maxStack;
    private final IAEItemFilter filter;

    public AppEngInternalInventory(final IAEAppEngInventory inventory, final int size) {
        this(inventory, size, 64, null);
    }

    public AppEngInternalInventory(final IAEAppEngInventory host, final int slots, final int maxStack, final IAEItemFilter filter) {
        this.host = host;
        this.stacks = new ItemStack[slots];
        for (int i = 0; i < slots; i++) {
            this.stacks[i] = ItemStack.EMPTY;
        }
        this.maxStack = maxStack;
        this.filter = filter;
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
        final ItemStack old = this.stacks[slot];
        this.stacks[slot] = stack == null ? ItemStack.EMPTY : stack;
        if (this.host != null) {
            this.host.onChangeInventory(this, slot, InvOperation.insert, old, this.stacks[slot]);
            this.host.saveChanges();
        }
    }

    @Override
    public int getSlotLimit(final int slot) {
        return this.maxStack;
    }

    @Override
    public @NonNull ItemStack insertItem(final int slot, final @NonNull ItemStack stack, final boolean simulate) {
        if (stack == null || stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        if (this.filter != null && !this.filter.allowInsert(this, slot, stack)) {
            return stack;
        }
        final ItemStack existing = this.stacks[slot];
        final int limit = Math.min(this.maxStack, stack.getMaxStackSize());
        if (!existing.isEmpty()) {
            if (!ItemStack.areItemsEqual(existing, stack) || !ItemStack.areItemStackTagsEqual(existing, stack)) {
                return stack;
            }
            final int space = limit - existing.getCount();
            if (space <= 0) {
                return stack;
            }
            final int toAdd = Math.min(space, stack.getCount());
            if (!simulate) {
                final ItemStack newStack = existing.copy();
                newStack.grow(toAdd);
                setStackInSlot(slot, newStack);
            }
            if (stack.getCount() <= toAdd) {
                return ItemStack.EMPTY;
            }
            final ItemStack remainder = stack.copy();
            remainder.shrink(toAdd);
            return remainder;
        }
        final int toAdd = Math.min(limit, stack.getCount());
        if (!simulate) {
            final ItemStack newStack = stack.copy();
            newStack.setCount(toAdd);
            setStackInSlot(slot, newStack);
        }
        if (stack.getCount() <= toAdd) {
            return ItemStack.EMPTY;
        }
        final ItemStack remainder = stack.copy();
        remainder.shrink(toAdd);
        return remainder;
    }

    @Override
    public @NonNull ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
        if (amount <= 0) {
            return ItemStack.EMPTY;
        }
        if (this.filter != null && !this.filter.allowExtract(this, slot, amount)) {
            return ItemStack.EMPTY;
        }
        final ItemStack existing = this.stacks[slot];
        if (existing.isEmpty()) {
            return ItemStack.EMPTY;
        }
        final int toExtract = Math.min(amount, existing.getCount());
        final ItemStack extracted = existing.copy();
        extracted.setCount(toExtract);
        if (!simulate) {
            if (existing.getCount() <= toExtract) {
                setStackInSlot(slot, ItemStack.EMPTY);
            } else {
                final ItemStack newStack = existing.copy();
                newStack.shrink(toExtract);
                setStackInSlot(slot, newStack);
            }
        }
        return extracted;
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
