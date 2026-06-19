package appeng.core.api.definitions;

import appeng.api.definitions.IItemDefinition;
import appeng.api.definitions.IMaterials;
import appeng.core.AELog;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Optional;

public final class ApiMaterials implements IMaterials {

    private final IItemDefinition certusQuartzCrystal = new RegistryItemDefinition("ae2:certus_quartz_crystal");
    private final IItemDefinition certusQuartzDust = new RegistryItemDefinition("ae2:certus_quartz_dust");
    private final IItemDefinition purifiedCertusQuartzCrystal = new RegistryItemDefinition("ae2:certus_quartz_crystal");
    private final IItemDefinition certusQuartzCrystalCharged = new RegistryItemDefinition("ae2:charged_certus_quartz_crystal");
    private final IItemDefinition fluixCrystal = new RegistryItemDefinition("ae2:fluix_crystal");
    private final IItemDefinition purifiedFluixCrystal = new RegistryItemDefinition("ae2:fluix_crystal");
    private final IItemDefinition fluixDust = new RegistryItemDefinition("ae2:fluix_dust");
    private final IItemDefinition purifiedNetherQuartzCrystal = new RegistryItemDefinition("minecraft:quartz");

    @Override
    public IItemDefinition certusQuartzCrystal() {
        return this.certusQuartzCrystal;
    }

    @Override
    public IItemDefinition certusQuartzDust() {
        return this.certusQuartzDust;
    }

    @Override
    public IItemDefinition purifiedCertusQuartzCrystal() {
        return this.purifiedCertusQuartzCrystal;
    }

    @Override
    public IItemDefinition certusQuartzCrystalCharged() {
        return this.certusQuartzCrystalCharged;
    }

    @Override
    public IItemDefinition fluixCrystal() {
        return this.fluixCrystal;
    }

    @Override
    public IItemDefinition purifiedFluixCrystal() {
        return this.purifiedFluixCrystal;
    }

    @Override
    public IItemDefinition fluixDust() {
        return this.fluixDust;
    }

    @Override
    public IItemDefinition purifiedNetherQuartzCrystal() {
        return this.purifiedNetherQuartzCrystal;
    }

    private record RegistryItemDefinition(String id) implements IItemDefinition {

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
