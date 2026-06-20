package github.formlessdragon.appcompat.bridge.gtceu;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridCache;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.events.MENetworkEvent;
import appeng.api.networking.storage.IStorageGrid;

public final class GtceuGridAdapter implements IGrid {

    private final ae2.api.networking.IGrid grid;

    public GtceuGridAdapter(final ae2.api.networking.IGrid grid) {
        this.grid = grid;
    }

    @Override
    public MENetworkEvent postEvent(final MENetworkEvent ev) {
        return ev;
    }

    @Override
    public IGridCache getCache(final Class<? extends IGridCache> iface) {
        if (iface == IStorageGrid.class) {
            return new GtceuStorageGridAdapter(this.grid.getStorageService(), this.grid.getEnergyService());
        }
        if (iface == IEnergyGrid.class) {
            return new GtceuEnergyGridAdapter(this.grid.getEnergyService());
        }
        throw new IllegalArgumentException("Unsupported GTCEu old AE grid cache " + iface.getName());
    }

    public ae2.api.networking.IGrid unwrap() {
        return this.grid;
    }
}
