package github.formlessdragon.appcompat.mixins.packagedauto;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import thelm.packagedauto.block.BlockPackagerExtension;
import thelm.packagedauto.tile.TilePackagerExtension;

@Mixin(value = TilePackagerExtension.class, remap = false)
public abstract class MixinTilePackagerExtension extends MixinTileBase {

    @Unique
    protected ItemStack getVisualItemStack() {
        return new ItemStack(BlockPackagerExtension.ITEM_INSTANCE);
    }
}
