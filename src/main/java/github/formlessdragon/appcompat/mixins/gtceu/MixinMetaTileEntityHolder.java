package github.formlessdragon.appcompat.mixins.gtceu;

import ae2.api.AECapabilities;
import ae2.api.networking.IInWorldGridNodeHost;
import appeng.me.helpers.IGridProxyable;
import github.formlessdragon.appcompat.bridge.gtceu.GtceuInWorldGridNodeHostAdapter;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MetaTileEntityHolder.class, remap = false)
public abstract class MixinMetaTileEntityHolder implements IGridProxyable {

    @Inject(method = "hasCapability", at = @At("HEAD"), cancellable = true)
    private void appcompat$hasAeGridNodeHostCapability(final Capability<?> capability, final EnumFacing facing,
                                                       final CallbackInfoReturnable<Boolean> cir) {
        if (capability == AECapabilities.IN_WORLD_GRID_NODE_HOST) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getCapability", at = @At("HEAD"), cancellable = true)
    private <T> void appcompat$getAeGridNodeHostCapability(final Capability<T> capability, final EnumFacing facing,
                                                          final CallbackInfoReturnable<T> cir) {
        if (capability == AECapabilities.IN_WORLD_GRID_NODE_HOST) {
            final IInWorldGridNodeHost adapter = new GtceuInWorldGridNodeHostAdapter(this);
            cir.setReturnValue(AECapabilities.IN_WORLD_GRID_NODE_HOST.cast(adapter));
        }
    }
}
