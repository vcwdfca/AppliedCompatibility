package github.formlessdragon.appcompat.mixins.packagedauto;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import thelm.packagedauto.block.BlockCraftingProxy;
import thelm.packagedauto.tile.TileCraftingProxy;

@Mixin(value = TileCraftingProxy.class, remap = false)
public abstract class MixinTileCraftingProxy extends MixinTileBase {

    @Unique
    protected ItemStack getVisualItemStack() {
        return new ItemStack(BlockCraftingProxy.ITEM_INSTANCE);
    }
}
