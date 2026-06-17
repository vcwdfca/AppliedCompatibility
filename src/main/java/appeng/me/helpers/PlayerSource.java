package appeng.me.helpers;

import appeng.api.networking.security.IActionHost;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Optional;

public class PlayerSource extends BaseActionSource {

    private final EntityPlayer player;
    private final IActionHost host;

    public PlayerSource(final EntityPlayer player, final IActionHost host) {
        this.player = player;
        this.host = host;
    }

    @Override
    public Optional<EntityPlayer> player() {
        return Optional.ofNullable(this.player);
    }

    @Override
    public Optional<IActionHost> machine() {
        return Optional.ofNullable(this.host);
    }
}
