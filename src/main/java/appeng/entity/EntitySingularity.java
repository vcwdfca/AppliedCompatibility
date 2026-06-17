package appeng.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public final class EntitySingularity extends EntityItem {

    public EntitySingularity(final World world) {
        super(world);
    }

    public EntitySingularity(final World world, final double x, final double y, final double z, final ItemStack stack) {
        super(world, x, y, z, stack);
    }
}
