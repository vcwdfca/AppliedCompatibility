package github.formlessdragon.appcompat.bridge.mmce.mekeng;

import github.formlessdragon.appcompat.bridge.mmce.AppCompatInitHooks;
import hellfirepvp.modularmachinery.common.lib.ItemsMM;

public final class AppCompatMekEngInitHooks {

    private static boolean initialized;

    private AppCompatMekEngInitHooks() {
    }

    public static synchronized void init() {
        if (initialized) {
            return;
        }
        if (ItemsMM.meGasInputBus == null || ItemsMM.meGasOutputBus == null) {
            return;
        }
        initialized = true;
        AppCompatInitHooks.register(ItemsMM.meGasInputBus);
        AppCompatInitHooks.register(ItemsMM.meGasOutputBus);
        PatternProviderGasCacheExtension.register();
    }
}
