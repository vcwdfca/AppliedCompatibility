package github.formlessdragon.appcompat.bridge.mmce.mekeng;

import me.ramidzkh.mekae2.ae2.AMGasStackRenderer;

public final class AppCompatMekEngClientInitHooks {

    private static boolean initialized;

    private AppCompatMekEngClientInitHooks() {
    }

    public static synchronized void init() {
        if (initialized) {
            return;
        }
        initialized = true;
        AMGasStackRenderer.initialize();
    }
}
