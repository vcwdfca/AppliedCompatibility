package github.formlessdragon.appcompat.bridge.gtceu;

import github.formlessdragon.appcompat.AppliedCompatibility;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class GtceuMekanismRecipeBridge {

    private static final GtceuMekanismRecipeBridge INSTANCE = new GtceuMekanismRecipeBridge();
    private static boolean registered;

    private GtceuMekanismRecipeBridge() {
    }

    public static synchronized void init() {
        if (registered || !Loader.isModLoaded("gregtech") || !Loader.isModLoaded("mekanism")) {
            return;
        }
        MinecraftForge.EVENT_BUS.register(INSTANCE);
        registered = true;
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        int removed = 0;
        removed += removeMekanismOreSmelting(0);
        removed += removeMekanismOreSmelting(1);
        removed += removeMekanismOreSmelting(2);
        if (removed > 0) {
            AppliedCompatibility.LOGGER.info("Removed {} Mekanism furnace ore recipes before GTCEu material handlers", removed);
        }
    }

    private static int removeMekanismOreSmelting(int metadata) {
        int removed = 0;
        Iterator<Map.Entry<ItemStack, ItemStack>> iterator = FurnaceRecipes.instance().getSmeltingList().entrySet().iterator();
        while (iterator.hasNext()) {
            ItemStack input = iterator.next().getKey();
            if (isMekanismOre(input, metadata)) {
                iterator.remove();
                removed++;
            }
        }
        return removed;
    }

    private static boolean isMekanismOre(ItemStack stack, int metadata) {
        if (stack.isEmpty() || stack.getMetadata() != metadata) {
            return false;
        }
        ResourceLocation id = stack.getItem().getRegistryName();
        return id != null && "mekanism".equals(id.getNamespace()) && "oreblock".equals(id.getPath());
    }
}
