package github.formlessdragon.appcompat.bridge.packagedauto;

import ae2.api.stacks.AEItemKey;
import ae2.api.stacks.AEKey;
import ae2.api.stacks.AEKeyType;
import ae2.api.stacks.GenericStack;
import appeng.api.config.Actionable;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.fluids.util.AEFluidStack;
import appeng.util.item.AEItemStack;
import net.minecraft.item.ItemStack;

public final class PackagedAutoAeKeyBridge {

    private PackagedAutoAeKeyBridge() {
    }

    public static ae2.api.config.Actionable toNewActionable(final Actionable mode) {
        return mode == Actionable.SIMULATE ? ae2.api.config.Actionable.SIMULATE : ae2.api.config.Actionable.MODULATE;
    }

    public static AEKey toKey(final IAEStack<?> stack) {
        if (stack instanceof AEItemStack itemStack) {
            return itemStack.getKey();
        }
        if (stack instanceof AEFluidStack fluidStack) {
            return fluidStack.getKey();
        }
        if (stack instanceof IAEItemStack itemStack) {
            return AEItemKey.of(itemStack.createItemStack());
        }
        if (stack instanceof IAEFluidStack fluidStack) {
            final GenericStack genericStack = GenericStack.fromFluidStack(fluidStack.getFluidStack());
            if (genericStack != null) {
                return genericStack.what();
            }
            throw new IllegalArgumentException("Unsupported PackagedAuto fluid stack content " + fluidStack.getFluidStack());
        }
        throw new IllegalArgumentException("Unsupported PackagedAuto old AE stack " + stack.getClass().getName());
    }

    public static IAEStack<?> toOldStack(final AEKey key, final long amount) {
        if (key instanceof AEItemKey itemKey) {
            return AEItemStack.fromKey(itemKey, amount);
        }
        if (key.getType() == AEKeyType.fluids()) {
            return AEFluidStack.fromGenericKey(key, amount);
        }
        throw new IllegalArgumentException("Unsupported PackagedAuto new AE key " + key.getClass().getName());
    }

    public static GenericStack toGenericStack(final IAEStack<?> stack) {
        final ItemStack representation = stack.asItemStackRepresentation();
        final GenericStack genericStack = GenericStack.unwrapItemStack(representation);
        if (genericStack != null) {
            return genericStack;
        }
        return new GenericStack(toKey(stack), stack.getStackSize());
    }

    public static ItemStack toPatternInputStack(final AEKey key, final long amount) {
        if (key instanceof AEItemKey itemKey) {
            return itemKey.toStack((int) Math.min(amount, Integer.MAX_VALUE));
        }
        return GenericStack.wrapInItemStack(key, amount);
    }

    public static boolean matchesChannel(final IStorageChannel<?> channel, final AEKey key) {
        if (channel instanceof IItemStorageChannel) {
            return key instanceof AEItemKey;
        }
        if (channel instanceof IFluidStorageChannel) {
            return key.getType() == AEKeyType.fluids();
        }
        return false;
    }
}
