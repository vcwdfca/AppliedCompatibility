package appeng.core.api.definitions;

import appeng.api.definitions.IBlockDefinition;
import appeng.api.definitions.IBlocks;
import appeng.core.AELog;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Optional;

public final class ApiBlocks implements IBlocks {

    private final IBlockDefinition quartzOre = new RegistryBlockDefinition("ae2:quartz_ore");
    private final IBlockDefinition quartzOreCharged = new RegistryBlockDefinition("ae2:charged_quartz_ore");
    private final IBlockDefinition fluixBlock = new RegistryBlockDefinition("ae2:fluix_block");

    @Override
    public IBlockDefinition quartzOre() {
        return this.quartzOre;
    }

    @Override
    public IBlockDefinition quartzOreCharged() {
        return this.quartzOreCharged;
    }

    public IBlockDefinition fluixBlock() {
        return this.fluixBlock;
    }

    private static final class RegistryBlockDefinition implements IBlockDefinition {

        private final String id;

        private RegistryBlockDefinition(final String id) {
            this.id = id;
        }

        @Override
        public String identifier() {
            return this.id;
        }

        @Override
        public Optional<Block> maybeBlock() {
            final Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(this.id));
            if (block == null) {
                AELog.debug("Unable to resolve AE block definition {}", this.id);
                return Optional.empty();
            }
            return Optional.of(block);
        }

        @Override
        public Optional<Item> maybeItem() {
            return this.maybeBlock().map(Item::getItemFromBlock).filter(item -> item != Item.getItemById(0));
        }

        @Override
        public Optional<ItemBlock> maybeItemBlock() {
            return this.maybeItem().filter(ItemBlock.class::isInstance).map(ItemBlock.class::cast);
        }

        @Override
        public Optional<ItemStack> maybeStack(final int stackSize) {
            return this.maybeItem().map(item -> new ItemStack(item, stackSize));
        }

        @Override
        public boolean isEnabled() {
            return this.maybeBlock().isPresent();
        }
    }
}
