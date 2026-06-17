package appeng.api.networking;

import appeng.api.networking.events.MENetworkEvent;

public interface IGrid {

    MENetworkEvent postEvent(MENetworkEvent ev);

    IGridCache getCache(Class<? extends IGridCache> iface);
}
