package appeng.api.features;

import com.mojang.authlib.GameProfile;

public interface IPlayerRegistry {

    int getID(GameProfile profile);
}
