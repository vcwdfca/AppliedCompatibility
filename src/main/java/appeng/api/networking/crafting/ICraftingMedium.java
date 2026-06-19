package appeng.api.networking.crafting;

import net.minecraft.inventory.InventoryCrafting;

public interface ICraftingMedium {

    boolean pushPattern(ICraftingPatternDetails patternDetails, InventoryCrafting table);

    boolean isBusy();
}
