package github.formlessdragon.appcompat.mixins.packagedauto.jei;

import github.formlessdragon.appcompat.bridge.packagedauto.PackagedEncoderGhostBridge;
import github.formlessdragon.appcompat.bridge.packagedauto.PackagedEncoderGhostState;
import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import thelm.packagedauto.client.gui.GuiEncoder;
import thelm.packagedauto.integration.jei.EncoderGhostIngredientHandler;

import java.util.List;

@Mixin(value = EncoderGhostIngredientHandler.class, remap = false, priority = 999)
public abstract class MixinEncoderGhostIngredientHandler implements IGhostIngredientHandler<GuiEncoder>, PackagedEncoderGhostState {

    @Unique
    private Object appcompat$currentGhostIngredient;
    @Unique
    private int appcompat$currentGhostMouseButton = -1;

    /**
     * @author circulation
     * @reason ItemStack 左键写物品、右键写容器内容
     */
    @Overwrite
    public <I> List<Target<I>> getTargets(final @NonNull GuiEncoder gui, final @NonNull I ingredient, final boolean doStart) {
        if (doStart) {
            appcompat$setCurrentGhostIngredient(ingredient);
            PackagedEncoderGhostBridge.setGhostState(this);
        }
        appcompat$updateCurrentGhostMouseButton();
        return PackagedEncoderGhostBridge.getTargets(gui, ingredient, this.appcompat$currentGhostMouseButton);
    }

    @Override
    public void onComplete() {
        appcompat$clearCurrentGhostIngredient();
    }

    /**
     * @author circulation
     * @reason 重写
     */
    @Overwrite
    private static ItemStack wrapStack(final Object ingredient) {
        return PackagedEncoderGhostBridge.toLeftClickStack(ingredient);
    }

    @Override
    public void appcompat$setCurrentGhostIngredient(final Object ingredient) {
        this.appcompat$currentGhostIngredient = ingredient;
        this.appcompat$currentGhostMouseButton = -1;
        appcompat$updateCurrentGhostMouseButton();
    }

    @Override
    public void appcompat$updateCurrentGhostMouseButton() {
        final int mouseButton = PackagedEncoderGhostBridge.activeMouseButton();
        if (mouseButton >= 0) {
            this.appcompat$currentGhostMouseButton = mouseButton;
        }
    }

    @Override
    public void appcompat$clearCurrentGhostIngredient() {
        this.appcompat$currentGhostIngredient = null;
        this.appcompat$currentGhostMouseButton = -1;
        PackagedEncoderGhostBridge.clearGhostState(this);
    }

    @Override
    public Object appcompat$getCurrentGhostIngredient() {
        return this.appcompat$currentGhostIngredient;
    }

    @Override
    public int appcompat$getCurrentGhostMouseButton() {
        return this.appcompat$currentGhostMouseButton;
    }

}
