package github.formlessdragon.appcompat.bridge.gtceu;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridCache;
import appeng.api.networking.events.MENetworkEvent;
import github.formlessdragon.appcompat.bridge.oldae.OldAeGridAdapter;

public final class GtceuGridAdapter implements IGrid {

    private final OldAeGridAdapter delegate;

    public GtceuGridAdapter(final ae2.api.networking.IGrid grid) {
        this.delegate = new OldAeGridAdapter(grid);
    }

    @Override
    public MENetworkEvent postEvent(final MENetworkEvent ev) {
        return this.delegate.postEvent(ev);
    }

    @Override
    public IGridCache getCache(final Class<? extends IGridCache> iface) {
        return this.delegate.getCache(iface);
    }

    public ae2.api.networking.IGrid unwrap() {
        return this.delegate.unwrap();
    }
}
