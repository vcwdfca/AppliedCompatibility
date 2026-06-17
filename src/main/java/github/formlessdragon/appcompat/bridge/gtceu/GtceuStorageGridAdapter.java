package github.formlessdragon.appcompat.bridge.gtceu;

import ae2.api.networking.energy.IEnergyService;
import ae2.api.networking.storage.IStorageService;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.data.IAEStack;

public final class GtceuStorageGridAdapter implements IStorageGrid {

    private final IStorageService storage;
    private final IEnergyService energy;

    public GtceuStorageGridAdapter(final IStorageService storage, final IEnergyService energy) {
        this.storage = storage;
        this.energy = energy;
    }

    @Override
    public <T extends IAEStack<T>> IMEMonitor<T> getInventory(final IStorageChannel<T> channel) {
        return new GtceuMEMonitorAdapter<>(channel, this.storage, this.energy);
    }
}
