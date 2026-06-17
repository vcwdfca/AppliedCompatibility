package appeng.core.api.definitions;

import appeng.api.definitions.IItemDefinition;
import appeng.api.definitions.IItems;
import appeng.core.AELog;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Optional;

public final class ApiItems implements IItems {

    private final IItemDefinition crystalSeed = new RegistryItemDefinition("ae2:crystal_seed");

    @Override
    public IItemDefinition crystalSeed() {
        return this.crystalSeed;
    }

    private static final class RegistryItemDefinition implements IItemDefinition {

        private final String id;

        private RegistryItemDefinition(final String id) {
            this.id = id;
        }

        @Override
        public String identifier() {
            return this.id;
        }

        @Override
        public Optional<Item> maybeItem() {
            final Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.id));
            if (item == null) {
                AELog.debug("Unable to resolve AE item definition {}", this.id);
                return Optional.empty();
            }
            return Optional.of(item);
        }

        @Override
        public Optional<ItemStack> maybeStack(final int stackSize) {
            return this.maybeItem().map(item -> new ItemStack(item, stackSize));
        }

        @Override
        public boolean isEnabled() {
            return this.maybeItem().isPresent();
        }
    }
}
