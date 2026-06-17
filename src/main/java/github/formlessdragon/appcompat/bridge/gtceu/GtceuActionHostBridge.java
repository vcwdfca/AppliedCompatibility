package github.formlessdragon.appcompat.bridge.gtceu;

import appeng.api.networking.security.IActionHost;

public final class GtceuActionHostBridge implements ae2.api.networking.security.IActionHost {

    private final IActionHost host;

    public GtceuActionHostBridge(final IActionHost host) {
        this.host = host;
    }

    @Override
    public ae2.api.networking.IGridNode getActionableNode() {
        final appeng.api.networking.IGridNode node = this.host.getActionableNode();
        if (node instanceof GtceuGridNodeAdapter adapter) {
            return adapter.unwrap();
        }
        return null;
    }
}
