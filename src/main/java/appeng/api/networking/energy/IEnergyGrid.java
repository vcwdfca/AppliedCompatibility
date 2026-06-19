package appeng.api.networking.energy;

import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.networking.IGridCache;

public interface IEnergyGrid extends IEnergySource, IGridCache {

    boolean isNetworkPowered();

    double extractAEPower(double amount, Actionable mode, PowerMultiplier multiplier);
}
