package github.formlessdragon.appcompat.bridge.mmce;

import ae2.api.networking.IGrid;
import ae2.api.networking.security.IActionHost;
import ae2.api.networking.security.IActionSource;
import ae2.api.storage.MEStorage;
import ae2.api.storage.StorageHelper;
import ae2.me.helpers.ActionHostEnergySource;
import me.ramidzkh.mekae2.ae2.AEGasKey;
import mekanism.api.gas.GasStack;

public final class AppCompatGasBridge {

    private AppCompatGasBridge() {
    }

    public static GasStack insert(final IGrid grid, final IActionHost host, final GasStack gas) {
        if (gas == null || gas.amount <= 0) {
            return null;
        }
        final AEGasKey key = AEGasKey.of(gas);
        if (key == null) {
            return gas;
        }
        final MEStorage net = grid.getStorageService().getInventory();
        final ActionHostEnergySource energy = new ActionHostEnergySource(host);
        final IActionSource source = IActionSource.ofMachine(host);
        final long amount = gas.amount;
        final long inserted = StorageHelper.poweredInsert(energy, net, key, amount, source);
        final long remaining = amount - inserted;
        return remaining <= 0 ? null : key.toStack(remaining);
    }

    public static GasStack extract(final IGrid grid, final IActionHost host, final GasStack template) {
        if (template == null || template.amount <= 0) {
            return null;
        }
        final AEGasKey key = AEGasKey.of(template);
        if (key == null) {
            return null;
        }
        final MEStorage net = grid.getStorageService().getInventory();
        final ActionHostEnergySource energy = new ActionHostEnergySource(host);
        final IActionSource source = IActionSource.ofMachine(host);
        final long extracted = StorageHelper.poweredExtraction(energy, net, key, template.amount, source);
        return extracted <= 0 ? null : key.toStack(extracted);
    }
}
