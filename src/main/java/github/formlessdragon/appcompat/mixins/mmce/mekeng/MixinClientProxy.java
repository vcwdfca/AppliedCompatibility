package github.formlessdragon.appcompat.mixins.mmce.mekeng;

import github.formlessdragon.appcompat.client.gui.mmce.GuiMEGasInputBus;
import github.formlessdragon.appcompat.client.gui.mmce.GuiMEGasOutputBus;
import github.kasuminova.mmce.common.tile.MEGasInputBus;
import github.kasuminova.mmce.common.tile.MEGasOutputBus;
import hellfirepvp.modularmachinery.client.ClientProxy;
import hellfirepvp.modularmachinery.common.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ClientProxy.class, remap = false)
public abstract class MixinClientProxy {

    @Inject(method = "getClientGuiElement", at = @At("HEAD"), cancellable = true)
    private void appcompat$getClientGuiElement(final int ID, final EntityPlayer player, final World world,
                                               final int x, final int y, final int z,
                                               final CallbackInfoReturnable<Object> cir) {
        final CommonProxy.GuiType[] values = CommonProxy.GuiType.values();
        final CommonProxy.GuiType type = values[MathHelper.clamp(ID, 0, values.length - 1)];
        if (type.requiredTileEntity == null) return;

        switch (type) {
            case ME_GAS_INPUT_BUS -> {
                final TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof MEGasInputBus bus) {
                    cir.setReturnValue(new GuiMEGasInputBus(bus, player));
                }
            }
            case ME_GAS_OUTPUT_BUS -> {
                final TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof MEGasOutputBus bus) {
                    cir.setReturnValue(new GuiMEGasOutputBus(bus, player));
                }
            }
            default -> {
            }
        }
    }
}
