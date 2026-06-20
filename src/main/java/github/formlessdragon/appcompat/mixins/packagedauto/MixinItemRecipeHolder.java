package github.formlessdragon.appcompat.mixins.packagedauto;

import github.formlessdragon.appcompat.bridge.packagedauto.PackagedPatternStacks;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import thelm.packagedauto.item.ItemRecipeHolder;

import java.util.function.Function;

@Mixin(value = ItemRecipeHolder.class, remap = false)
public abstract class MixinItemRecipeHolder {

    @ModifyArg(method = "addInformation", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;map(Ljava/util/function/Function;)Ljava/util/stream/Stream;", remap = false))
    private Function<ItemStack, String> appcompat$formatRecipeTooltip(final Function<ItemStack, String> original) {
        return PackagedPatternStacks::format;
    }
}
