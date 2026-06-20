package github.formlessdragon.appcompat.bridge.packagedauto;

import ae2.api.config.PowerMultiplier;
import ae2.api.networking.energy.IEnergyService;
import appeng.api.config.Actionable;
import appeng.api.networking.energy.IEnergyGrid;

public final class PackagedAutoLegacyEnergyGrid implements IEnergyGrid {

    private final IEnergyService energy;

    public PackagedAutoLegacyEnergyGrid(final IEnergyService energy) {
        this.energy = energy;
    }

    @Override
    public boolean isNetworkPowered() {
        return this.energy.isNetworkPowered();
    }

    @Override
    public double extractAEPower(final double amount, final Actionable mode) {
        return this.energy.extractAEPower(amount, PackagedAutoAeKeyBridge.toNewActionable(mode), PowerMultiplier.CONFIG);
    }

    @Override
    public double extractAEPower(final double amount, final Actionable mode, final appeng.api.config.PowerMultiplier multiplier) {
        return this.energy.extractAEPower(amount, PackagedAutoAeKeyBridge.toNewActionable(mode), PowerMultiplier.CONFIG);
    }
}
