package github.formlessdragon.appcompat.mixins.packagedauto;

import ae2.api.stacks.GenericStack;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thelm.packagedauto.client.gui.GuiEncoder;

@Mixin(value = GuiEncoder.class, remap = false)
public abstract class MixinGuiEncoder extends MixinGuiContainerTileBase {

    public MixinGuiEncoder(final Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    /**
     * @author circulation
     * @reason wrapped GenericStack 的数量在 AE amount 中，不在 ItemStack count 中
     */
    @Overwrite
    public int getItemAmountSpecificationLimit(final Slot slot) {
        final GenericStack stack = GenericStack.unwrapItemStack(slot.getStack());
        if (stack != null) {
            return 1000000000;
        }
        return slot.slotNumber > 81 ? 1000000000 : Math.min(slot.getStack().getMaxStackSize(), 1000000000);
    }
}
