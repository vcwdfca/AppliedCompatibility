package appeng.util.item;

import ae2.api.stacks.AEItemKey;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import net.minecraft.item.ItemStack;

public class AEItemStack implements IAEItemStack {

    private final AEItemKey key;
    private long amount;

    private AEItemStack(final AEItemKey key, final long amount) {
        this.key = key;
        this.amount = amount;
    }

    public static AEItemStack fromItemStack(final ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        final AEItemKey key = AEItemKey.of(stack);
        if (key == null) {
            return null;
        }
        return new AEItemStack(key, stack.getCount());
    }

    public AEItemKey getKey() {
        return this.key;
    }

    @Override
    public IAEStack copy() {
        return new AEItemStack(this.key, this.amount);
    }

    @Override
    public long getStackSize() {
        return this.amount;
    }

    @Override
    public IAEStack setStackSize(final long size) {
        this.amount = size;
        return this;
    }

    @Override
    public ItemStack createItemStack() {
        if (this.key == null) {
            return ItemStack.EMPTY;
        }
        return this.key.toStack((int) Math.min(this.amount, Integer.MAX_VALUE));
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AEItemStack other)) {
            return false;
        }
        return this.key != null && this.key.equals(other.key);
    }

    @Override
    public int hashCode() {
        return this.key == null ? 0 : this.key.hashCode();
    }
}
