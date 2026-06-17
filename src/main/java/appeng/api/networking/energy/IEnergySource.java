package appeng.api.networking.energy;

import appeng.api.config.Actionable;

public interface IEnergySource {

    double extractAEPower(double amount, Actionable mode);
}
