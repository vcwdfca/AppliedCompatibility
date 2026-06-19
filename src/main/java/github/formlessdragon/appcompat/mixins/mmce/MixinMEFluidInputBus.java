package github.formlessdragon.appcompat.mixins.mmce;

import ae2.api.networking.IGrid;
import ae2.api.networking.IGridNode;
import ae2.api.networking.IManagedGridNode;
import ae2.api.networking.security.IActionHost;
import ae2.api.networking.ticking.IGridTickable;
import ae2.api.networking.ticking.TickRateModulation;
import ae2.api.networking.ticking.TickingRequest;
import ae2.me.helpers.IGridConnectedTile;
import appeng.api.networking.ticking.ITickManager;
import appeng.api.storage.data.IAEFluidStack;
import appeng.fluids.util.IAEFluidTank;
import github.formlessdragon.appcompat.bridge.mmce.AppCompatFluidBridge;
import github.kasuminova.mmce.common.tile.MEFluidInputBus;
import github.kasuminova.mmce.common.tile.base.MEFluidBus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.locks.ReadWriteLock;

@Mixin(value = MEFluidInputBus.class, remap = false)
public abstract class MixinMEFluidInputBus extends MEFluidBus implements IGridTickable {

    @Shadow
    public abstract IAEFluidTank getConfig();

    @Shadow
    private boolean needsUpdate() {
        throw new AbstractMethodError();
    }

    @Override
    @Unique
    public TickingRequest getTickingRequest(final IGridNode node) {
        return new TickingRequest(10, 120, !needsUpdate(), 10);
    }

    @Override
    @Unique
    public TickRateModulation tickingRequest(final IGridNode gridNode, final int ticksSinceLastCall) {
        final IGridConnectedTile hostTile = (IGridConnectedTile) this;
        final IManagedGridNode managedNode = hostTile.getMainNode();
        if (managedNode == null) {
            return TickRateModulation.IDLE;
        }
        final IGrid grid = managedNode.getGrid();
        if (grid == null) {
            return TickRateModulation.IDLE;
        }

        final int[] needUpdateSlots = getNeedUpdateSlots();
        if (needUpdateSlots.length == 0) {
            return TickRateModulation.SLOWER;
        }

        final IAEFluidTank config = getConfig();
        final ReadWriteLock rwLock = tanks.getRWLock();
        try {
            rwLock.writeLock().lock();
            boolean successAtLeastOnce = false;
            inTick = true;
            final long capacity = tanks.getCapacity();

            for (final int slot : needUpdateSlots) {
                changedSlots[slot] = false;
                final IAEFluidStack cfgStack = config.getFluidInSlot(slot);
                final IAEFluidStack invStack = tanks.getFluidInSlot(slot);

                if (cfgStack == null) {
                    if (invStack == null) {
                        continue;
                    }
                    tanks.setFluidInSlot(slot, AppCompatFluidBridge.insert(grid, hostTile, invStack));
                    continue;
                }

                if (!cfgStack.equals(invStack)) {
                    if (invStack != null) {
                        final IAEFluidStack stack = AppCompatFluidBridge.insert(grid, hostTile, invStack);
                        if (stack != null) {
                            tanks.setFluidInSlot(slot, stack);
                            continue;
                        }
                    }
                    final IAEFluidStack request = cfgStack.copy();
                    request.setStackSize(capacity);
                    final IAEFluidStack stack = AppCompatFluidBridge.extract(grid, hostTile, request);
                    tanks.setFluidInSlot(slot, stack);
                    if (stack != null) {
                        successAtLeastOnce = true;
                    }
                    continue;
                }

                if (capacity == invStack.getStackSize()) {
                    continue;
                }

                if (capacity > invStack.getStackSize()) {
                    final long countToReceive = capacity - invStack.getStackSize();
                    final IAEFluidStack request = invStack.copy();
                    request.setStackSize(countToReceive);
                    final IAEFluidStack stack = AppCompatFluidBridge.extract(grid, hostTile, request);
                    if (stack != null) {
                        final IAEFluidStack updated = invStack.copy();
                        updated.setStackSize(invStack.getStackSize() + stack.getStackSize());
                        tanks.setFluidInSlot(slot, updated);
                        successAtLeastOnce = true;
                    }
                    continue;
                }

                final long countToExtract = invStack.getStackSize() - capacity;
                final IAEFluidStack request = invStack.copy();
                request.setStackSize(countToExtract);
                final IAEFluidStack stack = AppCompatFluidBridge.insert(grid, hostTile, request);
                final IAEFluidStack updated = invStack.copy();
                if (stack == null) {
                    updated.setStackSize(invStack.getStackSize() - countToExtract);
                } else {
                    updated.setStackSize(invStack.getStackSize() - countToExtract + stack.getStackSize());
                }
                tanks.setFluidInSlot(slot, updated);
                successAtLeastOnce = true;
            }

            inTick = false;
            return successAtLeastOnce ? TickRateModulation.FASTER : TickRateModulation.SLOWER;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Redirect(
        method = {"markNoUpdate", "uploadSettings"},
        at = @At(
            value = "INVOKE",
            target = "Lappeng/api/networking/ticking/ITickManager;alertDevice(Lappeng/api/networking/IGridNode;)Z",
            remap = false
        ),
        require = 0
    )
    private boolean appcompat$redirectAlert(final ITickManager tick,
                                            final appeng.api.networking.IGridNode oldNode) {
        final IManagedGridNode node = ((IGridConnectedTile) this).getMainNode();
        if (node != null) {
            final IGrid grid = node.getGrid();
            final IGridNode gridNode = node.getNode();
            if (grid != null && gridNode != null) {
                grid.getTickManager().alertDevice(gridNode);
            }
        }
        return true;
    }
}
