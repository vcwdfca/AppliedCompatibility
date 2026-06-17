package appeng.fluids.util;

import ae2.api.stacks.AEFluidKey;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class AEFluidStack implements IAEFluidStack {

    private final AEFluidKey key;
    private long amount;

    private AEFluidStack(final AEFluidKey key, final long amount) {
        this.key = key;
        this.amount = amount;
    }

    public static AEFluidStack fromFluidStack(final FluidStack stack) {
        if (stack == null || stack.getFluid() == null || stack.amount <= 0) {
            return null;
        }
        final AEFluidKey key = AEFluidKey.of(stack);
        if (key == null) {
            return null;
        }
        return new AEFluidStack(key, stack.amount);
    }

    public static IAEFluidStack fromNBT(final NBTTagCompound nbt) {
        if (nbt == null || nbt.isEmpty()) {
            return null;
        }
        final AEFluidKey key = AEFluidKey.fromTag(nbt.getCompoundTag("key"));
        if (key == null) {
            return null;
        }
        return new AEFluidStack(key, nbt.getLong("amount"));
    }

    public AEFluidKey getKey() {
        return this.key;
    }

    @Override
    public IAEFluidStack copy() {
        return new AEFluidStack(this.key, this.amount);
    }

    @Override
    public Fluid getFluid() {
        return this.key == null ? null : this.key.getFluid();
    }

    @Override
    public FluidStack getFluidStack() {
        if (this.key == null) {
            return null;
        }
        return this.key.toStack((int) Math.min(this.amount, Integer.MAX_VALUE));
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
    public void writeToNBT(final NBTTagCompound data) {
        if (this.key != null) {
            data.setTag("key", this.key.toTag());
            data.setLong("amount", this.amount);
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AEFluidStack other)) {
            return false;
        }
        return this.key != null && this.key.equals(other.key);
    }

    @Override
    public int hashCode() {
        return this.key == null ? 0 : this.key.hashCode();
    }
}
