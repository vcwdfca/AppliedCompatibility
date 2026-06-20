package github.formlessdragon.appcompat.mixins.packagedauto;

import github.formlessdragon.appcompat.bridge.packagedauto.PackagedPatternStacks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import thelm.packagedauto.client.gui.GuiItemAmountSpecifying;
import thelm.packagedauto.network.PacketHandler;
import thelm.packagedauto.network.packet.PacketSetItemStack;

@Mixin(value = GuiItemAmountSpecifying.class, remap = false)
public abstract class MixinGuiItemAmountSpecifying extends MixinGuiAmountSpecifying {

    @Shadow
    private int containerSlot;
    @Shadow
    private ItemStack stack;
    @Shadow
    private int maxAmount;

    public MixinGuiItemAmountSpecifying(final Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    /**
     * @author circulation
     * @reason wrapped GenericStack 默认数量来自 AE amount
     */
    @Overwrite
    protected int getDefaultAmount() {
        return (int) Math.min(PackagedPatternStacks.amount(this.stack), Integer.MAX_VALUE);
    }

    /**
     * @author circulation
     * @reason wrapped GenericStack 写回时必须修改 AE amount，不能修改 ItemStack count
     */
    @Overwrite
    protected void onOkButtonPressed(final boolean shiftDown) {
        try {
            final int amount = MathHelper.clamp(Integer.parseInt(this.amountField.getText()), 0, this.maxAmount);
            PacketHandler.INSTANCE.sendToServer(new PacketSetItemStack(this.containerSlot, PackagedPatternStacks.withAmount(this.stack, amount)));
            close();
        } catch (final NumberFormatException e) {
            this.amountField.setText(String.valueOf(getDefaultAmount()));
        }
    }
}
