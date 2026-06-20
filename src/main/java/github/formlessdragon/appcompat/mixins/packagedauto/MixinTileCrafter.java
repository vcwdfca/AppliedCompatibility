package github.formlessdragon.appcompat.mixins.packagedauto;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import thelm.packagedauto.block.BlockCrafter;
import thelm.packagedauto.tile.TileCrafter;

@Mixin(value = TileCrafter.class, remap = false)
public abstract class MixinTileCrafter extends MixinTileBase {

    @Unique
    protected ItemStack getVisualItemStack() {
        return new ItemStack(BlockCrafter.ITEM_INSTANCE);
    }
}
