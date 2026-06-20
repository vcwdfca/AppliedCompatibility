package github.formlessdragon.appcompat.mixins.packagedauto.jei;

import github.formlessdragon.appcompat.bridge.packagedauto.PackagedAutoJeiTransferBridge;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mezz.jei.api.gui.IRecipeLayout;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thelm.packagedauto.recipe.RecipeTypeProcessingPositioned;

import java.util.List;

@Mixin(value = RecipeTypeProcessingPositioned.class, remap = false)
public abstract class MixinRecipeTypeProcessingPositioned {

    /**
     * @author circulation
     * @reason 覆写 JEI 转移以支持 positioned wrapped GenericStack，避免依赖局部变量注入
     */
    @Overwrite
    public Int2ObjectMap<ItemStack> getRecipeTransferMap(final IRecipeLayout recipeLayout, final String category) {
        final Int2ObjectMap<ItemStack> map = new Int2ObjectOpenHashMap<>();
        List<ItemStack> output = PackagedAutoJeiTransferBridge.addPositionedTransfer(recipeLayout, map, 0);
        output = PackagedAutoJeiTransferBridge.condenseStacks(output, true);
        for (int i = 0; i < output.size() && i < 9; ++i) {
            map.put(i + 81, output.get(i));
        }
        return map;
    }
}
