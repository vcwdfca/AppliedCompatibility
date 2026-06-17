package appeng.tile.networking;

import appeng.api.util.AEColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileCableBus extends TileEntity {

    private AEColor color = AEColor.TRANSPARENT;

    public AEColor getColor() {
        return this.color;
    }

    public boolean recolourBlock(final EnumFacing side, final AEColor colour, final EntityPlayer who) {
        if (colour == null) {
            throw new IllegalArgumentException("colour");
        }
        if (this.color == colour) {
            return false;
        }
        this.color = colour;
        this.markDirty();
        if (this.world != null) {
            this.world.markBlockRangeForRenderUpdate(this.pos, this.pos);
        }
        return true;
    }
}
