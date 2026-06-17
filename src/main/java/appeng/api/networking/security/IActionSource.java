package appeng.api.networking.security;

import appeng.me.helpers.BaseActionSource;
import appeng.me.helpers.MachineSource;
import appeng.me.helpers.PlayerSource;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Optional;

public interface IActionSource {

    static IActionSource empty() {
        return new BaseActionSource();
    }

    static IActionSource ofPlayer(final EntityPlayer player) {
        return new PlayerSource(player, null);
    }

    static IActionSource ofPlayer(final EntityPlayer player, final IActionHost host) {
        return new PlayerSource(player, host);
    }

    static IActionSource ofMachine(final IActionHost machine) {
        return new MachineSource(machine);
    }

    Optional<EntityPlayer> player();

    Optional<IActionHost> machine();

    <T> Optional<T> context(Class<T> key);
}
