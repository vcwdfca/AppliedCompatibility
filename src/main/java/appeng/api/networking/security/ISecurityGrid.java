package appeng.api.networking.security;

import appeng.api.config.SecurityPermissions;
import appeng.api.networking.IGridCache;
import net.minecraft.entity.player.EntityPlayer;

public interface ISecurityGrid extends IGridCache {

    boolean hasPermission(EntityPlayer player, SecurityPermissions perm);
}
