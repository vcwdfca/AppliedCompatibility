package appeng.api.definitions;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Optional;

public interface IBlockDefinition extends IItemDefinition {

    Optional<Block> maybeBlock();

    default Optional<ItemBlock> maybeItemBlock() {
        return this.maybeItem()
            .filter(ItemBlock.class::isInstance)
            .map(ItemBlock.class::cast);
    }

    default boolean isSameAs(final IBlockAccess world, final BlockPos pos) {
        return world != null && pos != null && this.maybeBlock().filter(block -> world.getBlockState(pos).getBlock() == block).isPresent();
    }

    @Override
    default boolean isSameAs(final ItemStack stack) {
        return IItemDefinition.super.isSameAs(stack);
    }
}
