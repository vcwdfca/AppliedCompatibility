package github.formlessdragon.appcompat.client.gui.mmce;

import ae2.client.gui.style.Blitter;
import ae2.client.gui.widgets.ITooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class PatternProviderTextureButton extends GuiButton implements ITooltip {

    private final ResourceLocation texture;
    private final int normalU;
    private final int hoveredU;
    private final int pressedU;
    private final int uV;
    private final Supplier<List<ITextComponent>> tooltip;
    private final Runnable onPress;
    private boolean pressedState;
    private int overlayU = -1;
    private int overlayV = -1;
    private int overlayWidth = 16;
    private int overlayHeight = 16;

    public PatternProviderTextureButton(final int id, final int x, final int y, final int width, final int height,
                                        final ResourceLocation texture, final int normalU, final int hoveredU, final int pressedU, final int uV,
                                        final Supplier<List<ITextComponent>> tooltip, final Runnable onPress) {
        super(id, x, y, width, height, "");
        this.texture = texture;
        this.normalU = normalU;
        this.hoveredU = hoveredU;
        this.pressedU = pressedU;
        this.uV = uV;
        this.tooltip = tooltip;
        this.onPress = onPress;
    }

    public void setOverlay(final int u, final int v, final int width, final int height) {
        this.overlayU = u;
        this.overlayV = v;
        this.overlayWidth = width;
        this.overlayHeight = height;
    }

    public boolean isMouseOver(final int mouseX, final int mouseY) {
        return this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }

    public void press() {
        if (this.enabled && this.visible) {
            this.onPress.run();
        }
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY) {
        super.mouseReleased(mouseX, mouseY);
        this.pressedState = false;
    }

    @Override
    public void drawButton(final Minecraft minecraft, final int mouseX, final int mouseY, final float partialTicks) {
        if (!this.visible) {
            return;
        }
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        final int u = this.pressedState ? this.pressedU : this.hovered ? this.hoveredU : this.normalU;
        Blitter.texture(this.texture).src(u, this.uV, this.width, this.height).dest(this.x, this.y, this.width, this.height).blit();
        if (this.overlayU >= 0 && this.overlayV >= 0) {
            Blitter.texture(this.texture).src(this.overlayU, this.overlayV, this.overlayWidth, this.overlayHeight)
                   .dest(this.x, this.y, this.overlayWidth, this.overlayHeight).blit();
        }
    }

    @Override
    public boolean mousePressed(final Minecraft minecraft, final int mouseX, final int mouseY) {
        this.pressedState = super.mousePressed(minecraft, mouseX, mouseY);
        return this.pressedState;
    }

    public boolean isPressedState() {
        return this.pressedState;
    }

    @Override
    public List<ITextComponent> getTooltipMessage() {
        return this.tooltip == null ? Collections.emptyList() : this.tooltip.get();
    }

    @Override
    public Rectangle getTooltipArea() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    @Override
    public boolean isTooltipAreaVisible() {
        return this.visible;
    }
}
