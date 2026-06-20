package github.formlessdragon.appcompat.mixins.packagedauto.jei;

import github.formlessdragon.appcompat.bridge.packagedauto.PackagedAutoJeiTransferBridge;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mezz.jei.api.gui.IRecipeLayout;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thelm.packagedauto.api.IRecipeType;
import thelm.packagedauto.recipe.RecipeTypeProcessing;

import java.util.List;

@Mixin(value = RecipeTypeProcessing.class, remap = false)
public abstract class MixinRecipeTypeProcessing implements IRecipeType {

    /**
     * @author circulation
     * @reason 覆写 JEI 转移以支持 wrapped GenericStack，避免依赖局部变量注入
     */
    @Overwrite
    public Int2ObjectMap<ItemStack> getRecipeTransferMap(final IRecipeLayout recipeLayout, final String category) {
        final Int2ObjectMap<ItemStack> map = new Int2ObjectOpenHashMap<>();
        final var stacks = PackagedAutoJeiTransferBridge.getProcessingStacks(recipeLayout);
        List<ItemStack> input = stacks.left();
        List<ItemStack> output = stacks.right();
        if (!isOrdered()) {
            input = PackagedAutoJeiTransferBridge.condenseStacks(input, false);
        }
        output = PackagedAutoJeiTransferBridge.condenseStacks(output, true);
        for (int i = 0; i < input.size() && i < 81; ++i) {
            map.put(i, input.get(i));
        }
        for (int i = 0; i < output.size() && i < 9; ++i) {
            map.put(i + 81, output.get(i));
        }
        return map;
    }
}
