package appeng.api.networking.ticking;

import appeng.api.networking.IGridCache;
import appeng.api.networking.IGridNode;

public interface ITickManager extends IGridCache {

    boolean alertDevice(IGridNode node);

    boolean wakeDevice(IGridNode node);
}
