package github.formlessdragon.appcompat.bridge.gtceu;

import appeng.api.networking.security.IActionSource;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Optional;

public final class GtceuActionSourceBridge {

    private GtceuActionSourceBridge() {
    }

    public static ae2.api.networking.security.IActionSource toNew(final IActionSource source) {
        if (source == null) {
            return ae2.api.networking.security.IActionSource.empty();
        }
        return new Ae2ActionSource(source);
    }

    private record Ae2ActionSource(IActionSource source) implements ae2.api.networking.security.IActionSource {

        @Override
        public Optional<EntityPlayer> player() {
            return this.source.player();
        }

        @Override
        public Optional<ae2.api.networking.security.IActionHost> machine() {
            return this.source.machine().map(GtceuActionHostBridge::new);
        }

        @Override
        public <T> Optional<T> context(final Class<T> key) {
            return this.source.context(key);
        }
    }
}
