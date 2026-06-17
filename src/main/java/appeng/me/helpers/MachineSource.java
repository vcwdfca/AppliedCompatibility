package appeng.me.helpers;

import appeng.api.networking.security.IActionHost;

import java.util.Optional;

public class MachineSource extends BaseActionSource {

    private final IActionHost host;

    public MachineSource(final IActionHost host) {
        this.host = host;
    }

    @Override
    public Optional<IActionHost> machine() {
        return Optional.of(this.host);
    }
}
