package appeng.api.networking;

import appeng.api.networking.security.IActionHost;
import appeng.api.util.AECableType;
import appeng.api.util.AEPartLocation;

public interface IGridHost extends IActionHost {

    IGridNode getGridNode(AEPartLocation dir);

    @Override
    default IGridNode getActionableNode() {
        return getGridNode(AEPartLocation.INTERNAL);
    }

    AECableType getCableConnectionType(AEPartLocation dir);

    void securityBreak();
}
