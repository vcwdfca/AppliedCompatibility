package github.formlessdragon.appcompat.bridge.packagedauto;

import ae2.api.networking.IGridNode;
import ae2.api.networking.IManagedGridNode;
import ae2.api.networking.security.IActionHost;

public final class PackagedAutoActionHost implements IActionHost {

    private final PackagedAutoNodeAccess access;

    public PackagedAutoActionHost(final PackagedAutoNodeAccess access) {
        this.access = access;
    }

    @Override
    public IGridNode getActionableNode() {
        final IManagedGridNode node = this.access.appcompat$mainNode();
        if (node == null || node.getNode() == null) {
            throw new IllegalStateException("PackagedAuto action host requested before AE node creation");
        }
        return node.getNode();
    }
}
