package appeng.api.networking.events;

import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingProvider;

public class MENetworkCraftingPatternChange extends MENetworkEvent {

    private final ICraftingProvider craftingProvider;
    private final IGridNode node;

    public MENetworkCraftingPatternChange(final ICraftingProvider craftingProvider, final IGridNode node) {
        this.craftingProvider = craftingProvider;
        this.node = node;
    }

    public ICraftingProvider getCraftingProvider() {
        return this.craftingProvider;
    }

    public IGridNode getNode() {
        return this.node;
    }
}
