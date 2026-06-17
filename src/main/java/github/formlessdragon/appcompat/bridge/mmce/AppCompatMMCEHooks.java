package github.formlessdragon.appcompat.bridge.mmce;

import ae2.api.upgrades.Upgrades;
import ae2.core.definitions.AEItems;
import hellfirepvp.modularmachinery.common.lib.ItemsMM;
import net.minecraft.item.Item;

public final class AppCompatMMCEHooks {

    private static boolean initialized;

    private AppCompatMMCEHooks() {
    }

    public static synchronized void init() {
        if (initialized) {
            return;
        }
        if (ItemsMM.meFluidInputBus == null || ItemsMM.meFluidOutputBus == null) {
            return;
        }
        initialized = true;
        register(ItemsMM.meFluidInputBus);
        register(ItemsMM.meFluidOutputBus);
    }

    public static void register(final Item item) {
        if (item != null) {
            Upgrades.add(AEItems.CAPACITY_CARD.item(), item, 5);
        }
    }
}
