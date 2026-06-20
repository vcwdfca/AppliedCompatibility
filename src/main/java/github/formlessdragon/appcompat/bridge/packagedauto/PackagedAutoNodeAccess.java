package github.formlessdragon.appcompat.bridge.packagedauto;

import ae2.api.networking.IManagedGridNode;
import appeng.api.networking.IGridNode;
import net.minecraft.nbt.NBTTagCompound;

public interface PackagedAutoNodeAccess {

    IManagedGridNode appcompat$mainNode();

    IGridNode appcompat$legacyNode();

    void appcompat$setLegacyNode(IGridNode node);

    void appcompat$loadLegacyNode(NBTTagCompound data);

    void appcompat$saveLegacyNode(NBTTagCompound data);

    void appcompat$createLegacyNode();

    void appcompat$destroyLegacyNode();

    void appcompat$requestCraftingUpdate();
}
