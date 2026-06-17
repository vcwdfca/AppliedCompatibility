package github.formlessdragon.appcompat.bridge.mmce;

import ae2.api.networking.IGrid;
import ae2.api.networking.security.IActionHost;
import ae2.api.networking.security.IActionSource;
import ae2.api.stacks.AEFluidKey;
import ae2.api.storage.MEStorage;
import ae2.api.storage.StorageHelper;
import ae2.me.helpers.ActionHostEnergySource;
import appeng.api.storage.data.IAEFluidStack;
import appeng.fluids.util.AEFluidStack;
import net.minecraftforge.fluids.FluidStack;

public final class AppCompatFluidBridge {

    private AppCompatFluidBridge() {
    }

    public static IAEFluidStack insert(final IGrid grid, final IActionHost host, final IAEFluidStack fluid) {
        if (fluid == null || fluid.getStackSize() <= 0) {
            return null;
        }
        final FluidStack fs = fluid.getFluidStack();
        if (fs == null) {
            return fluid;
        }
        final AEFluidKey key = AEFluidKey.of(fs);
        if (key == null) {
            return fluid;
        }
        final MEStorage net = grid.getStorageService().getInventory();
        final ActionHostEnergySource energy = new ActionHostEnergySource(host);
        final IActionSource source = IActionSource.ofMachine(host);
        final long amount = fluid.getStackSize();
        final long inserted = StorageHelper.poweredInsert(energy, net, key, amount, source);
        final long remaining = amount - inserted;
        if (remaining <= 0) {
            return null;
        }
        final IAEFluidStack left = fluid.copy();
        left.setStackSize(remaining);
        return left;
    }

    public static IAEFluidStack extract(final IGrid grid, final IActionHost host, final IAEFluidStack template) {
        if (template == null || template.getStackSize() <= 0) {
            return null;
        }
        final FluidStack fs = template.getFluidStack();
        if (fs == null) {
            return null;
        }
        final AEFluidKey key = AEFluidKey.of(fs);
        if (key == null) {
            return null;
        }
        final MEStorage net = grid.getStorageService().getInventory();
        final ActionHostEnergySource energy = new ActionHostEnergySource(host);
        final IActionSource source = IActionSource.ofMachine(host);
        final long extracted = StorageHelper.poweredExtraction(energy, net, key, template.getStackSize(), source);
        if (extracted <= 0) {
            return null;
        }
        return AEFluidStack.fromFluidStack(key.toStack((int) Math.min(extracted, Integer.MAX_VALUE)));
    }
}
