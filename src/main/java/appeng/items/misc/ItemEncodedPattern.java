package appeng.items.misc;

import appeng.api.implementations.ICraftingPatternItem;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEncodedPattern extends Item implements ICraftingPatternItem {

    @Override
    public ICraftingPatternDetails getPatternForItem(final ItemStack stack, final World world) {
        return null;
    }

    public ItemStack getOutput(final ItemStack stack) {
        return stack;
    }
}
