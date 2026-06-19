package appeng.api.networking.crafting;

public interface ICraftingProvider extends ICraftingMedium {

    void provideCrafting(ICraftingProviderHelper craftingTracker);
}
