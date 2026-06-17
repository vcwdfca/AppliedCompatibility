package github.formlessdragon.appcompat.bridge.gtceu;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridCache;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.events.MENetworkEvent;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.networking.ticking.ITickManager;

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
            return getStorageGrid();
        }
        if (iface == IEnergyGrid.class) {
            return getEnergyGrid();
        }
        if (iface == ITickManager.class) {
            return getTickManager();
        }
        throw new IllegalArgumentException("Unsupported old AE grid cache " + iface.getName());
    }

    public IStorageGrid getStorageGrid() {
        return new GtceuStorageGridAdapter(this.grid.getStorageService(), this.grid.getEnergyService());
    }

    public IEnergyGrid getEnergyGrid() {
        return new GtceuEnergyGridAdapter(this.grid.getEnergyService());
    }

    public ITickManager getTickManager() {
        return new GtceuTickManagerAdapter(this.grid.getTickManager());
    }

    public ae2.api.networking.IGrid unwrap() {
        return this.grid;
    }
}
