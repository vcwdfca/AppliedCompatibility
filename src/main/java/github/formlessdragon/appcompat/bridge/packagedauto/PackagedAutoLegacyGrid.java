package github.formlessdragon.appcompat.bridge.packagedauto;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridCache;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.events.MENetworkCraftingPatternChange;
import appeng.api.networking.events.MENetworkEvent;
import appeng.api.networking.storage.IStorageGrid;

public final class PackagedAutoLegacyGrid implements IGrid {

    private final PackagedAutoNodeAccess access;

    public PackagedAutoLegacyGrid(final PackagedAutoNodeAccess access) {
        this.access = access;
    }

    @Override
    public MENetworkEvent postEvent(final MENetworkEvent ev) {
        if (ev instanceof MENetworkCraftingPatternChange) {
            this.access.appcompat$requestCraftingUpdate();
        }
        return ev;
    }

    @Override
    public IGridCache getCache(final Class<? extends IGridCache> iface) {
        final ae2.api.networking.IGrid grid = this.access.appcompat$mainNode().getGrid();
        if (grid == null) {
            throw new IllegalStateException("PackagedAuto old AE grid cache requested before grid boot");
        }
        if (iface == IStorageGrid.class) {
            return new PackagedAutoLegacyStorageGrid(grid.getStorageService(), grid.getEnergyService());
        }
        if (iface == IEnergyGrid.class) {
            return new PackagedAutoLegacyEnergyGrid(grid.getEnergyService());
        }
        throw new IllegalArgumentException("Unsupported PackagedAuto old AE grid cache " + iface.getName());
    }
}
