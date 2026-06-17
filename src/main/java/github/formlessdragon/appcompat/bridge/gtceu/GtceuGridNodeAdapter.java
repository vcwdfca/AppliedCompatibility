package github.formlessdragon.appcompat.bridge.gtceu;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;

public final class GtceuGridNodeAdapter implements IGridNode {

    private final ae2.api.networking.IGridNode node;

    public GtceuGridNodeAdapter(final ae2.api.networking.IGridNode node) {
        this.node = node;
    }

    @Override
    public IGrid getGrid() {
        return new GtceuGridAdapter(this.node.grid());
    }

    public ae2.api.networking.IGridNode unwrap() {
        return this.node;
    }
}
