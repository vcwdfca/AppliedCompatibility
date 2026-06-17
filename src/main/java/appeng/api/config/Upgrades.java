package appeng.api.config;

import net.minecraft.item.ItemStack;

public enum Upgrades {
    CAPACITY,
    REDSTONE,
    SPEED,
    FUZZY,
    INVERTER,
    CRAFTING,
    PATTERN_CAPACITY;

    public void registerItem(final ItemStack stack, final int maxSupported) {
    }
}
