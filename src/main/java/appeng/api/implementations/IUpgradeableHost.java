package appeng.api.implementations;

import appeng.api.config.Upgrades;
import net.minecraftforge.items.IItemHandler;

public interface IUpgradeableHost {

    int getInstalledUpgrades(Upgrades u);

    IItemHandler getInventoryByName(String name);
}
