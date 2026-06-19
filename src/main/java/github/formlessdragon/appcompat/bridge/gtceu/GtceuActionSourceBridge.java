package github.formlessdragon.appcompat.bridge.gtceu;

import appeng.api.networking.security.IActionSource;
import github.formlessdragon.appcompat.bridge.oldae.OldAeActionSourceAdapter;

public final class GtceuActionSourceBridge {

    private GtceuActionSourceBridge() {
    }

    public static ae2.api.networking.security.IActionSource toNew(final IActionSource source) {
        return OldAeActionSourceAdapter.toNew(source);
    }
}
