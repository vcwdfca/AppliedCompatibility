package appeng.me.helpers;

import appeng.api.util.DimensionalCoord;

public interface IGridProxyable {

    AENetworkProxy getProxy();

    void gridChanged();

    DimensionalCoord getLocation();
}
