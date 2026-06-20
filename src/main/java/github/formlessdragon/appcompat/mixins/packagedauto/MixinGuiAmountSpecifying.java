package github.formlessdragon.appcompat.mixins.packagedauto;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import thelm.packagedauto.client.gui.GuiAmountSpecifying;

@Mixin(value = GuiAmountSpecifying.class, remap = false)
public abstract class MixinGuiAmountSpecifying extends MixinGuiContainerTileBase {

    @Shadow
    protected GuiTextField amountField;

    public MixinGuiAmountSpecifying(final Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Shadow
    public abstract void close();
}
