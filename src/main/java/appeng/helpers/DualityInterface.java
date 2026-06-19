package appeng.helpers;

import appeng.api.config.Actionable;
import appeng.api.config.Upgrades;
import appeng.api.networking.crafting.ICraftingLink;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.IConfigManager;
import appeng.me.helpers.AENetworkProxy;
import appeng.tile.inventory.AppEngInternalInventory;
import com.google.common.collect.ImmutableSet;
import net.minecraftforge.items.IItemHandler;

public class DualityInterface {

    private AppEngInternalInventory patterns;

    public DualityInterface(final AENetworkProxy networkProxy, final IInterfaceHost ih) {
    }

    public int getInstalledUpgrades(final Upgrades u) {
        return 0;
    }

    public String getTermName() {
        return "";
    }

    public IConfigManager getConfigManager() {
        return null;
    }

    public IItemHandler getInventoryByName(final String name) {
        return null;
    }

    public ImmutableSet<ICraftingLink> getRequestedJobs() {
        return ImmutableSet.of();
    }

    public IAEItemStack injectCraftedItems(final ICraftingLink link, final IAEItemStack acquired, final Actionable mode) {
        return null;
    }

    public void jobStateChange(final ICraftingLink link) {
    }
}
