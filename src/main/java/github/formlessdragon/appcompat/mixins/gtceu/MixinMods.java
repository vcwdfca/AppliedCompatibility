package github.formlessdragon.appcompat.mixins.gtceu;

import github.formlessdragon.appcompat.bridge.gtceu.GtceuAppliedEnergisticsRegistryBridge;
import gregtech.api.util.Mods;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Mods.class, remap = false)
public abstract class MixinMods {

    @Inject(method = "getItem(Ljava/lang/String;IILjava/lang/String;)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void appcompat$resolveOldAppliedEnergisticsLookup(final String name, final int meta, final int amount, final String nbt,
                                                             final CallbackInfoReturnable<ItemStack> cir) {
        final ItemStack resolved = GtceuAppliedEnergisticsRegistryBridge.resolve((Mods) (Object) this, name, meta, amount, nbt);
        if (!resolved.isEmpty()) {
            cir.setReturnValue(resolved);
        }
    }
}
