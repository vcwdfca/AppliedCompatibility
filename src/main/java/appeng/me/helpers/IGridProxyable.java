package appeng.me.helpers;

import appeng.api.util.DimensionalCoord;
import appeng.api.util.AECableType;
import appeng.api.util.AEPartLocation;

public interface IGridProxyable {

    AENetworkProxy getProxy();

    void gridChanged();

    DimensionalCoord getLocation();

    AECableType getCableConnectionType(AEPartLocation side);
}
