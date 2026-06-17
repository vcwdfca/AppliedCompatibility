package github.formlessdragon.appcompat.bridge.gtceu;

import ae2.api.networking.IGridNode;
import ae2.api.networking.IInWorldGridNodeHost;
import ae2.api.util.AECableType;
import appeng.api.util.AEPartLocation;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;
import net.minecraft.util.EnumFacing;

public final class GtceuInWorldGridNodeHostAdapter implements IInWorldGridNodeHost {

    private final IGridProxyable host;

    public GtceuInWorldGridNodeHostAdapter(final IGridProxyable host) {
        this.host = host;
    }

    @Override
    public IGridNode getGridNode(final EnumFacing dir) {
        final AENetworkProxy proxy = this.host.getProxy();
        if (proxy == null) {
            return null;
        }
        return proxy.getManagedHost().newNode();
    }

    @Override
    public AECableType getCableConnectionType(final EnumFacing dir) {
        final appeng.api.util.AECableType type = this.host.getCableConnectionType(AEPartLocation.fromFacing(dir));
        return switch (type) {
            case NONE -> AECableType.NONE;
            case GLASS -> AECableType.GLASS;
            case COVERED -> AECableType.COVERED;
            case SMART -> AECableType.SMART;
            case DENSE_COVERED -> AECableType.DENSE_COVERED;
            case DENSE_SMART -> AECableType.DENSE_SMART;
        };
    }
}
