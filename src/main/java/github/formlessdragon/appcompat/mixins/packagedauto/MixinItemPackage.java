package github.formlessdragon.appcompat.mixins.packagedauto;

import com.llamalad7.mixinextras.sugar.Local;
import github.formlessdragon.appcompat.bridge.packagedauto.PackagedPatternStacks;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import thelm.packagedauto.item.ItemPackage;

@Mixin(value = ItemPackage.class)
public abstract class MixinItemPackage {

    @ModifyArg(method = "addInformation", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1,remap = false))
    private Object appcompat$formatOutputTooltip(final Object original, @Local(name = "is") final ItemStack is) {
        return PackagedPatternStacks.format(is);
    }

    @ModifyArg(method = "addInformation", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 4,remap = false))
    private Object appcompat$formatContentTooltip(final Object original, @Local(name = "is") final ItemStack is) {
        return PackagedPatternStacks.format(is);
    }
}
