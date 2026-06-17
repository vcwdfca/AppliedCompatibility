package github.formlessdragon.appcompat.mixins.mmce.top;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import github.kasuminova.mmce.common.integration.theoneprobe.MachineryHatchInfoProvider;
import hellfirepvp.modularmachinery.common.integration.ModIntegrationTOP;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.apiimpl.TheOneProbeImp;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ModIntegrationTOP.class, remap = false)
public class MixinModIntegrationTOP {

    @Definition(id = "top", local = @Local(type = TheOneProbeImp.class, name = "top"))
    @Definition(id = "registerProvider", method = "Lmcjty/theoneprobe/apiimpl/TheOneProbeImp;registerProvider(Lmcjty/theoneprobe/api/IProbeInfoProvider;)V")
    @Definition(id = "MachineryHatchInfoProvider", type = MachineryHatchInfoProvider.class)
    @Expression("top.registerProvider(new MachineryHatchInfoProvider())")
    @Redirect(method = "registerProviders", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private static void appcompat$skipMachineryHatchInfoProvider(final TheOneProbeImp top,
                                                                 final IProbeInfoProvider provider) {
        top.registerProvider(provider);
    }
}
