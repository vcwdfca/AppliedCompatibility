package github.formlessdragon.appcompat.bridge.packagedauto;

import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.IActionSource;
import appeng.me.helpers.MachineSource;

public final class PackagedAutoActionSourceBridge {

    private PackagedAutoActionSourceBridge() {
    }

    public static ae2.api.networking.security.IActionSource toNew(final IActionSource source) {
        if (source instanceof MachineSource machineSource && machineSource.machine().isPresent()) {
            final IActionHost oldHost = machineSource.machine().get();
            if (oldHost instanceof PackagedAutoNodeAccess access) {
                return ae2.api.networking.security.IActionSource.ofMachine(() -> access.appcompat$mainNode().getNode());
            }
        }
        return ae2.api.networking.security.IActionSource.empty();
    }
}
