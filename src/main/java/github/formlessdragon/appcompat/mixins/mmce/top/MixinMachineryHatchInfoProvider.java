package github.formlessdragon.appcompat.mixins.mmce.top;

import github.kasuminova.mmce.common.integration.theoneprobe.MachineryHatchInfoProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = MachineryHatchInfoProvider.class, remap = false, priority = 999)
public class MixinMachineryHatchInfoProvider {

    /**
     * @author circulation
     * @reason 覆写到新实现
     */
    @Overwrite
    public void addProbeInfo(final ProbeMode probeMode,
                             final IProbeInfo probeInfo,
                             final EntityPlayer player,
                             final World world,
                             final IBlockState blockState,
                             final IProbeHitData hitData) {
    }
}
