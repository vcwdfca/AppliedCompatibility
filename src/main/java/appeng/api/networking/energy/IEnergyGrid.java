package appeng.api.networking.energy;

import appeng.api.networking.IGridCache;

public interface IEnergyGrid extends IEnergySource, IGridCache {

    boolean isNetworkPowered();
}
