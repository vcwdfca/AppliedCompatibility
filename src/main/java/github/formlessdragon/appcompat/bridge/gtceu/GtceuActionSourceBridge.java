package github.formlessdragon.appcompat.bridge.gtceu;

import appeng.api.networking.security.IActionSource;
import appeng.me.helpers.MachineSource;

public final class GtceuActionSourceBridge {

    private GtceuActionSourceBridge() {
    }

    public static ae2.api.networking.security.IActionSource toNew(final IActionSource source) {
        if (source instanceof MachineSource machineSource && machineSource.machine().isPresent()) {
            return ae2.api.networking.security.IActionSource.ofMachine(new GtceuActionHostBridge(machineSource.machine().get()));
        }
        return ae2.api.networking.security.IActionSource.empty();
    }
}
