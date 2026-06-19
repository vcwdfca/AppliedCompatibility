package appeng.api.parts;

import appeng.api.util.AEPartLocation;
import net.minecraft.util.EnumFacing;

public interface IPartHost {

    Object getPart(AEPartLocation side);

    default Object getPart(final EnumFacing side) {
        return getPart(AEPartLocation.fromFacing(side));
    }
}
