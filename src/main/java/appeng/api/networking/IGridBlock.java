package appeng.api.networking;

import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import java.util.EnumSet;

public interface IGridBlock {

    double getIdlePowerUsage();

    EnumSet<GridFlags> getFlags();

    boolean isWorldAccessible();

    DimensionalCoord getLocation();

    AEColor getGridColor();

    void onGridNotification(GridNotification gridNotification);

    void setNetworkStatus(IGrid grid, int usedChannels);

    EnumSet<EnumFacing> getConnectableSides();

    IGridHost getMachine();

    void gridChanged();

    ItemStack getMachineRepresentation();
}
