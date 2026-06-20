package appeng.fluids.util;

import ae2.api.stacks.AEFluidKey;
import ae2.api.stacks.AEKey;
import appeng.api.AEApi;
import appeng.api.config.FuzzyMode;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Objects;

public class AEFluidStack implements IAEFluidStack {

    private final AEFluidKey key;
    private long amount;
    private long countRequestable;
    private boolean craftable;

    private AEFluidStack(final AEFluidKey key, final long amount) {
        this.key = key;
        this.amount = amount;
    }

    public static AEFluidStack fromKey(final AEFluidKey key, final long amount) {
        if (key == null || amount <= 0) {
            return null;
        }
        return new AEFluidStack(key, amount);
    }

    public static AEFluidStack fromGenericKey(final AEKey key, final long amount) {
        if (!(key instanceof AEFluidKey fluidKey)) {
            return null;
        }
        return fromKey(fluidKey, amount);
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

    public static AEFluidStack fromNBT(final NBTTagCompound nbt) {
        if (nbt == null || nbt.isEmpty()) {
            return null;
        }
        final AEFluidKey key = AEFluidKey.fromTag(nbt.getCompoundTag("key"));
        if (key == null) {
            return null;
        }
        final AEFluidStack stack = new AEFluidStack(key, nbt.getLong("amount"));
        stack.countRequestable = nbt.getLong("requestable");
        stack.craftable = nbt.getBoolean("craftable");
        return stack;
    }

    public static AEFluidStack fromPacket(final ByteBuf data) {
        final PacketBuffer packet = new PacketBuffer(data);
        final AEFluidKey key = AEFluidKey.fromPacket(packet);
        if (key == null) {
            return null;
        }
        final AEFluidStack stack = new AEFluidStack(key, packet.readVarLong());
        stack.countRequestable = packet.readVarLong();
        stack.craftable = packet.readBoolean();
        return stack;
    }

    public AEFluidKey getKey() {
        return this.key;
    }

    @Override
    public void add(final IAEFluidStack option) {
        if (option == null) {
            return;
        }
        if (!fuzzyComparison(option, FuzzyMode.IGNORE_ALL)) {
            throw new IllegalArgumentException("Cannot add different fluid stack types");
        }
        this.amount = saturatedAdd(this.amount, option.getStackSize());
        this.countRequestable = saturatedAdd(this.countRequestable, option.getCountRequestable());
        this.craftable |= option.isCraftable();
    }

    @Override
    public AEFluidStack copy() {
        final AEFluidStack copy = new AEFluidStack(this.key, this.amount);
        copy.countRequestable = this.countRequestable;
        copy.craftable = this.craftable;
        return copy;
    }

    @Override
    public AEFluidStack setStackSize(final long size) {
        this.amount = Math.max(0, size);
        return this;
    }

    @Override
    public AEFluidStack setCountRequestable(final long countRequestable) {
        this.countRequestable = Math.max(0, countRequestable);
        return this;
    }

    @Override
    public AEFluidStack setCraftable(final boolean craftable) {
        this.craftable = craftable;
        return this;
    }

    @Override
    public AEFluidStack reset() {
        this.amount = 0;
        this.countRequestable = 0;
        this.craftable = false;
        return this;
    }

    @Override
    public AEFluidStack empty() {
        return new AEFluidStack(this.key, 0);
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
    public long getCountRequestable() {
        return this.countRequestable;
    }

    @Override
    public boolean isCraftable() {
        return this.craftable;
    }

    @Override
    public boolean isMeaningful() {
        return this.key != null && (this.amount > 0 || this.countRequestable > 0 || this.craftable);
    }

    @Override
    public void incStackSize(final long amount) {
        setStackSize(saturatedAdd(this.amount, amount));
    }

    @Override
    public void decStackSize(final long amount) {
        setStackSize(this.amount - amount);
    }

    @Override
    public void incCountRequestable(final long amount) {
        setCountRequestable(saturatedAdd(this.countRequestable, amount));
    }

    @Override
    public void decCountRequestable(final long amount) {
        setCountRequestable(this.countRequestable - amount);
    }

    @Override
    public boolean fuzzyComparison(final IAEFluidStack stack, final FuzzyMode mode) {
        if (stack instanceof AEFluidStack other) {
            return Objects.equals(this.key, other.key);
        }
        return stack != null && Objects.equals(getFluid(), stack.getFluid());
    }

    @Override
    public void writeToPacket(final ByteBuf data) {
        final PacketBuffer packet = new PacketBuffer(data);
        this.key.writeToPacket(packet);
        packet.writeVarLong(this.amount);
        packet.writeVarLong(this.countRequestable);
        packet.writeBoolean(this.craftable);
    }

    @Override
    public boolean isItem() {
        return false;
    }

    @Override
    public boolean isFluid() {
        return true;
    }

    @Override
    public IStorageChannel<IAEFluidStack> getChannel() {
        return AEApi.instance().storage().getStorageChannel(IFluidStorageChannel.class);
    }

    @Override
    public ItemStack asItemStackRepresentation() {
        return ItemStack.EMPTY;
    }

    @Override
    public void writeToNBT(final NBTTagCompound data) {
        if (this.key != null) {
            data.setTag("key", this.key.toTag());
            data.setLong("amount", this.amount);
            data.setLong("requestable", this.countRequestable);
            data.setBoolean("craftable", this.craftable);
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
        return Objects.equals(this.key, other.key);
    }

    @Override
    public int hashCode() {
        return this.key == null ? 0 : this.key.hashCode();
    }

    private static long saturatedAdd(final long left, final long right) {
        final long result = left + right;
        if (((left ^ result) & (right ^ result)) < 0) {
            return left < 0 ? Long.MIN_VALUE : Long.MAX_VALUE;
        }
        return result;
    }
}
