package github.formlessdragon.appcompat.bridge.gtceu;

import github.formlessdragon.appcompat.AppliedCompatibility;
import gregtech.api.util.Mods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public final class GtceuAppliedEnergisticsRegistryBridge {

    private GtceuAppliedEnergisticsRegistryBridge() {
    }

    public static ItemStack resolve(final Mods mod, final String name, final int meta, final int amount, final String nbt) {
        if (mod != Mods.AppliedEnergistics2 || amount <= 0) {
            return ItemStack.EMPTY;
        }
        if ("interface".equals(name) || "fluid_interface".equals(name)) {
            return stack("ae2:interface", amount);
        }
        if ("material".equals(name) && meta == 30) {
            return stack("ae2:speed_card", amount);
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack stack(final String id, final int amount) {
        final Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
        if (item == null) {
            AppliedCompatibility.LOGGER.warn("Unable to resolve GTCEu old AE registry item {}", id);
            return ItemStack.EMPTY;
        }
        return new ItemStack(item, amount);
    }
}
