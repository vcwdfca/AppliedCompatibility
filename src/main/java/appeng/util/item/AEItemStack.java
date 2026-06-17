package appeng.util.item;

import ae2.api.stacks.AEItemKey;
import appeng.api.AEApi;
import appeng.api.config.FuzzyMode;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEItemStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Objects;

public class AEItemStack implements IAEItemStack {

    private final AEItemKey key;
    private long amount;
    private long countRequestable;
    private boolean craftable;
    private ItemStack cachedItemStack = ItemStack.EMPTY;

    private AEItemStack(final AEItemKey key, final long amount) {
        this.key = key;
        this.amount = amount;
    }

    public static AEItemStack fromKey(final AEItemKey key, final long amount) {
        if (key == null || amount <= 0) {
            return null;
        }
        return new AEItemStack(key, amount);
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

    public static AEItemStack fromNBT(final NBTTagCompound nbt) {
        if (nbt == null || nbt.isEmpty()) {
            return null;
        }
        final AEItemKey key = AEItemKey.fromTag(nbt.getCompoundTag("key"));
        if (key == null) {
            return null;
        }
        final AEItemStack stack = new AEItemStack(key, nbt.getLong("amount"));
        stack.countRequestable = nbt.getLong("requestable");
        stack.craftable = nbt.getBoolean("craftable");
        return stack;
    }

    public static AEItemStack fromPacket(final ByteBuf data) {
        final PacketBuffer packet = new PacketBuffer(data);
        final AEItemKey key = AEItemKey.fromPacket(packet);
        if (key == null) {
            return null;
        }
        final AEItemStack stack = new AEItemStack(key, packet.readVarLong());
        stack.countRequestable = packet.readVarLong();
        stack.craftable = packet.readBoolean();
        return stack;
    }

    public AEItemKey getKey() {
        return this.key;
    }

    @Override
    public void add(final IAEItemStack option) {
        if (option == null) {
            return;
        }
        if (!isSameType(option)) {
            throw new IllegalArgumentException("Cannot add different item stack types");
        }
        this.amount = saturatedAdd(this.amount, option.getStackSize());
        this.countRequestable = saturatedAdd(this.countRequestable, option.getCountRequestable());
        this.craftable |= option.isCraftable();
    }

    @Override
    public long getStackSize() {
        return this.amount;
    }

    @Override
    public AEItemStack setStackSize(final long size) {
        this.amount = Math.max(0, size);
        return this;
    }

    @Override
    public long getCountRequestable() {
        return this.countRequestable;
    }

    @Override
    public AEItemStack setCountRequestable(final long countRequestable) {
        this.countRequestable = Math.max(0, countRequestable);
        return this;
    }

    @Override
    public boolean isCraftable() {
        return this.craftable;
    }

    @Override
    public AEItemStack setCraftable(final boolean craftable) {
        this.craftable = craftable;
        return this;
    }

    @Override
    public AEItemStack reset() {
        this.amount = 0;
        this.countRequestable = 0;
        this.craftable = false;
        return this;
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
    public boolean fuzzyComparison(final IAEItemStack stack, final FuzzyMode mode) {
        if (stack instanceof AEItemStack other) {
            return this.key != null && other.key != null && this.key.fuzzyEquals(other.key, mode.toNewMode());
        }
        return stack != null && this.getItem() == stack.getItem();
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
    public AEItemStack copy() {
        final AEItemStack copy = new AEItemStack(this.key, this.amount);
        copy.countRequestable = this.countRequestable;
        copy.craftable = this.craftable;
        copy.cachedItemStack = this.cachedItemStack.copy();
        return copy;
    }

    @Override
    public AEItemStack empty() {
        return new AEItemStack(this.key, 0);
    }

    @Override
    public boolean isItem() {
        return true;
    }

    @Override
    public boolean isFluid() {
        return false;
    }

    @Override
    public IStorageChannel<IAEItemStack> getChannel() {
        return AEApi.instance().storage().getStorageChannel(IItemStorageChannel.class);
    }

    @Override
    public ItemStack asItemStackRepresentation() {
        return createItemStack();
    }

    @Override
    public ItemStack createItemStack() {
        if (this.key == null) {
            return ItemStack.EMPTY;
        }
        return this.key.toStack((int) Math.min(this.amount, Integer.MAX_VALUE));
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
    public ItemStack getDefinition() {
        if (this.key == null) {
            return ItemStack.EMPTY;
        }
        return this.key.toStack(1);
    }

    @Override
    public Item getItem() {
        return this.key == null ? null : this.key.getItem();
    }

    @Override
    public int getItemDamage() {
        return this.key == null ? 0 : this.key.getReadOnlyStack().getItemDamage();
    }

    @Override
    public boolean sameOre(final IAEItemStack other) {
        if (other == null) {
            return false;
        }
        final int[] left = OreDictionary.getOreIDs(getDefinition());
        final int[] right = OreDictionary.getOreIDs(other.getDefinition());
        for (final int leftId : left) {
            for (final int rightId : right) {
                if (leftId == rightId) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isSameType(final IAEItemStack other) {
        if (other instanceof AEItemStack itemStack) {
            return Objects.equals(this.key, itemStack.key);
        }
        return other != null && isSameType(other.getDefinition());
    }

    @Override
    public boolean isSameType(final ItemStack other) {
        return this.key != null && this.key.matches(other);
    }

    @Override
    public ItemStack getCachedItemStack(final long size) {
        if (this.cachedItemStack.isEmpty() || this.cachedItemStack.getCount() != (int) Math.min(size, Integer.MAX_VALUE)) {
            this.cachedItemStack = this.key == null ? ItemStack.EMPTY : this.key.toStack((int) Math.min(size, Integer.MAX_VALUE));
        }
        return this.cachedItemStack.copy();
    }

    @Override
    public void setCachedItemStack(final ItemStack stack) {
        this.cachedItemStack = stack == null ? ItemStack.EMPTY : stack.copy();
    }

    public boolean equals(final ItemStack stack) {
        return isSameType(stack);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AEItemStack other)) {
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
