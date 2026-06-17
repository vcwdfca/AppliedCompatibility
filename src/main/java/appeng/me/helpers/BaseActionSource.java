package appeng.me.helpers;

import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.IActionSource;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Optional;

public class BaseActionSource implements IActionSource {

    public Optional<EntityPlayer> player() {
        return Optional.empty();
    }

    public Optional<IActionHost> machine() {
        return Optional.empty();
    }

    public <T> Optional<T> context(final Class<T> key) {
        return Optional.empty();
    }
}
