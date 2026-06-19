package github.formlessdragon.appcompat.bridge.gtceu;

import appeng.api.networking.security.IActionHost;
import github.formlessdragon.appcompat.bridge.oldae.OldAeActionHostAdapter;

public final class GtceuActionHostBridge implements ae2.api.networking.security.IActionHost {

    private final OldAeActionHostAdapter delegate;

    public GtceuActionHostBridge(final IActionHost host) {
        this.delegate = new OldAeActionHostAdapter(host);
    }

    @Override
    public ae2.api.networking.IGridNode getActionableNode() {
        return this.delegate.getActionableNode();
    }
}
