package appeng.api.networking.crafting;

import appeng.api.storage.data.IAEItemStack;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ICraftingPatternDetails {

    ItemStack getPattern();

    boolean isValidItemForSlot(int slotIndex, ItemStack itemStack, World world);

    boolean isCraftable();

    IAEItemStack[] getInputs();

    IAEItemStack[] getOutputs();

    IAEItemStack[] getCondensedInputs();

    IAEItemStack[] getCondensedOutputs();

    boolean canSubstitute();

    ItemStack getOutput(InventoryCrafting craftingInv, World world);

    int getPriority();

    void setPriority(int priority);
}
