package appeng.api.networking.ticking;

import appeng.api.networking.IGridNode;

public interface ITickManager {

    boolean alertDevice(IGridNode node);

    boolean wakeDevice(IGridNode node);
}
