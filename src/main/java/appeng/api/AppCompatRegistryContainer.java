package appeng.api;

import appeng.api.features.IPlayerRegistry;
import appeng.api.features.IRegistryContainer;
import com.mojang.authlib.GameProfile;

public final class AppCompatRegistryContainer implements IRegistryContainer {

    private final IPlayerRegistry players = new PlayerRegistry();

    @Override
    public IPlayerRegistry players() {
        return this.players;
    }

    private static final class PlayerRegistry implements IPlayerRegistry {

        @Override
        public int getID(final GameProfile profile) {
            if (profile.getId() == null) {
                return -1;
            }
            return profile.getId().hashCode();
        }
    }
}
