package appeng.me.cluster.implementations;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.container.ContainerNull;
import appeng.me.cache.CraftingGridCache;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.util.math.BlockPos;

public class CraftingCPUCluster extends ae2.me.cluster.implementations.CraftingCPUCluster {

    public CraftingCPUCluster(final BlockPos boundsMin, final BlockPos boundsMax) {
        super(boundsMin, boundsMax);
    }

    @SuppressWarnings("unused")
    private void executeCrafting(final IEnergyGrid eg, final CraftingGridCache cc) {
        final ICraftingPatternDetails details = null;
        final InventoryCrafting ic = new InventoryCrafting(new ContainerNull(), 3, 3);
        if (details == null || ic.isEmpty()) {
            throw new UnsupportedOperationException("Old AE CraftingCPUCluster execution is replaced by new AE crafting provider adapters");
        }
    }
}
