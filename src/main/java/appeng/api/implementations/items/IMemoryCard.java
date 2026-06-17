package appeng.api.implementations.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IMemoryCard {

    void setMemoryCardContents(ItemStack is, String settingsName, NBTTagCompound data);

    String getSettingsName(ItemStack is);

    NBTTagCompound getData(ItemStack is);

    void notifyUser(EntityPlayer player, MemoryCardMessages message);
}
