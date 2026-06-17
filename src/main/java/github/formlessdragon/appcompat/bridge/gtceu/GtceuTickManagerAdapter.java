package github.formlessdragon.appcompat.bridge.gtceu;

import appeng.api.networking.IGridNode;
import appeng.api.networking.ticking.ITickManager;

public final class GtceuTickManagerAdapter implements ITickManager {

    private final ae2.api.networking.ticking.ITickManager tickManager;

    public GtceuTickManagerAdapter(final ae2.api.networking.ticking.ITickManager tickManager) {
        this.tickManager = tickManager;
    }

    @Override
    public boolean alertDevice(final IGridNode node) {
        return this.tickManager.alertDevice(unwrap(node));
    }

    @Override
    public boolean wakeDevice(final IGridNode node) {
        this.tickManager.wakeDevice(unwrap(node));
        return true;
    }

    private ae2.api.networking.IGridNode unwrap(final IGridNode node) {
        if (node instanceof GtceuGridNodeAdapter adapter) {
            return adapter.unwrap();
        }
        throw new IllegalArgumentException("Unsupported old AE grid node " + node);
    }
}
