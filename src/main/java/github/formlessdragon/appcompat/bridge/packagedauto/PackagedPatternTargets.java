package github.formlessdragon.appcompat.bridge.packagedauto;

import ae2.api.config.Actionable;
import ae2.api.config.PatternProviderInsertionMode;
import ae2.api.networking.security.IActionSource;
import ae2.api.stacks.GenericStack;
import ae2.api.storage.MEStorage;
import ae2.api.storage.StorageHelper;
import ae2.helpers.patternprovider.PatternProviderTarget;
import ae2.me.helpers.ActionHostEnergySource;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public final class PackagedPatternTargets {

    private PackagedPatternTargets() {
    }

    public static long insertIntoNetwork(final PackagedAutoNodeAccess access, final GenericStack stack) {
        if (access.appcompat$mainNode() == null || !access.appcompat$mainNode().isActive() || access.appcompat$mainNode().getGrid() == null) {
            return 0;
        }
        final MEStorage storage = access.appcompat$mainNode().getGrid().getStorageService().getInventory();
        final PackagedAutoActionHost host = new PackagedAutoActionHost(access);
        return StorageHelper.poweredInsert(new ActionHostEnergySource(host), storage, stack.what(), stack.amount(), IActionSource.ofMachine(host));
    }

    public static ItemStack insertIntoNetwork(final PackagedAutoNodeAccess access, final ItemStack stack) {
        final GenericStack genericStack = PackagedPatternStacks.fromItemStack(stack);
        if (genericStack == null) {
            return stack;
        }
        final long inserted = insertIntoNetwork(access, genericStack);
        return remainingStack(genericStack, inserted);
    }

    public static long insertIntoTarget(final PackagedAutoNodeAccess access, final TileEntity source,
                                        final EnumFacing facing, final GenericStack stack,
                                        final Actionable mode, final PatternProviderInsertionMode insertionMode) {
        final PatternProviderTarget target = PatternProviderTarget.get(source.getWorld(), source.getPos().offset(facing),
            facing.getOpposite(), IActionSource.ofMachine(new PackagedAutoActionHost(access)));
        if (target == null) {
            return 0;
        }
        return target.insert(stack.what(), stack.amount(), mode, insertionMode);
    }

    public static long insertIntoDirectTarget(final PackagedAutoNodeAccess access, final TileEntity targetTile,
                                              final EnumFacing side, final GenericStack stack,
                                              final Actionable mode, final PatternProviderInsertionMode insertionMode) {
        final PatternProviderTarget target = PatternProviderTarget.get(targetTile.getWorld(), targetTile.getPos(),
            side, IActionSource.ofMachine(new PackagedAutoActionHost(access)));
        if (target == null) {
            return 0;
        }
        return target.insert(stack.what(), stack.amount(), mode, insertionMode);
    }

    public static ItemStack insertIntoTarget(final PackagedAutoNodeAccess access, final TileEntity source,
                                             final EnumFacing facing, final ItemStack stack,
                                             final Actionable mode, final PatternProviderInsertionMode insertionMode) {
        final GenericStack genericStack = PackagedPatternStacks.fromItemStack(stack);
        if (genericStack == null) {
            return stack;
        }
        final long inserted = insertIntoTarget(access, source, facing, genericStack, mode, insertionMode);
        return remainingStack(genericStack, inserted);
    }

    public static ItemStack insertIntoDirectTarget(final PackagedAutoNodeAccess access, final TileEntity targetTile,
                                                   final EnumFacing side, final ItemStack stack,
                                                   final Actionable mode, final PatternProviderInsertionMode insertionMode) {
        final GenericStack genericStack = PackagedPatternStacks.fromItemStack(stack);
        if (genericStack == null) {
            return stack;
        }
        final long inserted = insertIntoDirectTarget(access, targetTile, side, genericStack, mode, insertionMode);
        return remainingStack(genericStack, inserted);
    }

    public static boolean acceptsAny(final PackagedAutoNodeAccess access, final TileEntity source,
                                     final EnumFacing facing, final ItemStack stack,
                                     final PatternProviderInsertionMode insertionMode) {
        final GenericStack genericStack = PackagedPatternStacks.fromItemStack(stack);
        return genericStack != null && insertIntoTarget(access, source, facing, genericStack, Actionable.SIMULATE, insertionMode) > 0;
    }

    public static boolean containsAnyStack(final PackagedAutoNodeAccess access, final TileEntity source,
                                           final EnumFacing facing) {
        final PatternProviderTarget target = PatternProviderTarget.get(source.getWorld(), source.getPos().offset(facing),
            facing.getOpposite(), IActionSource.ofMachine(new PackagedAutoActionHost(access)));
        return target != null && target.containsAnyStack();
    }

    public static boolean directTargetContainsAnyStack(final PackagedAutoNodeAccess access, final TileEntity targetTile,
                                                       final EnumFacing side) {
        final PatternProviderTarget target = PatternProviderTarget.get(targetTile.getWorld(), targetTile.getPos(),
            side, IActionSource.ofMachine(new PackagedAutoActionHost(access)));
        return target != null && target.containsAnyStack();
    }

    private static ItemStack remainingStack(final GenericStack original, final long inserted) {
        final long remaining = original.amount() - inserted;
        if (remaining <= 0) {
            return ItemStack.EMPTY;
        }
        return PackagedPatternStacks.toItemStack(new GenericStack(original.what(), remaining));
    }
}
