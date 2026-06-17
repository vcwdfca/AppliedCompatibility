package github.formlessdragon.appcompat.mixins.mmce;

import ae2.me.helpers.IGridConnectedTile;
import appeng.me.helpers.AENetworkProxy;
import github.kasuminova.mmce.common.tile.base.MEMachineComponent;
import hellfirepvp.modularmachinery.common.item.ItemBlockMEMachineComponent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemBlockMEMachineComponent.class, remap = false)
public abstract class MixinItemBlockMEMachineComponent {

    @Redirect(
        method = "placeBlockAt",
        at = @At(
            value = "INVOKE",
            target = "Lgithub/kasuminova/mmce/common/tile/base/MEMachineComponent;getProxy()Lappeng/me/helpers/AENetworkProxy;",
            remap = false
        )
    )
    private AENetworkProxy appcompat$setOwnerOnNode(final MEMachineComponent self,
                                                    final ItemStack stack,
                                                    final EntityPlayer player,
                                                    final World world,
                                                    final BlockPos pos,
                                                    final EnumFacing side,
                                                    final float hitX,
                                                    final float hitY,
                                                    final float hitZ,
                                                    final IBlockState newState) {
        if (self instanceof IGridConnectedTile host) {
            host.setOwner(player);
        }
        return self.getProxy();
    }
}
