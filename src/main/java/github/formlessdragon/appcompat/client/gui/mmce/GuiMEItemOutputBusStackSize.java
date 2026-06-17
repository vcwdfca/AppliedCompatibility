package github.formlessdragon.appcompat.client.gui.mmce;

import ae2.client.gui.style.GuiStyleManager;
import github.formlessdragon.appcompat.common.container.mmce.ContainerMEItemOutputBusStackSize;
import github.kasuminova.mmce.common.tile.MEItemOutputBus;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiMEItemOutputBusStackSize extends GuiMEBusBase<ContainerMEItemOutputBusStackSize> {

    private final ContainerMEItemOutputBusStackSize container;
    private GuiTextField stackSizeBox;

    public GuiMEItemOutputBusStackSize(final MEItemOutputBus te, final EntityPlayer player) {
        super(new ContainerMEItemOutputBusStackSize(player.inventory, te), player.inventory,
            GuiStyleManager.loadStyleDoc("/screens/appcompat_me_item_output_bus_stack_size.json"));
        this.container = (ContainerMEItemOutputBusStackSize) this.inventorySlots;
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();

        this.addButton(new GuiButton(0, guiLeft + 20, guiTop + 32, 22, 20, "+1"));
        this.addButton(new GuiButton(1, guiLeft + 48, guiTop + 32, 28, 20, "+10"));
        this.addButton(new GuiButton(2, guiLeft + 82, guiTop + 32, 32, 20, "+100"));
        this.addButton(new GuiButton(3, guiLeft + 120, guiTop + 32, 38, 20, "+1000"));
        this.addButton(new GuiButton(4, guiLeft + 20, guiTop + 69, 22, 20, "-1"));
        this.addButton(new GuiButton(5, guiLeft + 48, guiTop + 69, 28, 20, "-10"));
        this.addButton(new GuiButton(6, guiLeft + 82, guiTop + 69, 32, 20, "-100"));
        this.addButton(new GuiButton(7, guiLeft + 120, guiTop + 69, 38, 20, "-1000"));

        this.stackSizeBox = new GuiTextField(8, this.fontRenderer, guiLeft + 62, guiTop + 57, 75, this.fontRenderer.FONT_HEIGHT);
        this.stackSizeBox.setEnableBackgroundDrawing(false);
        this.stackSizeBox.setMaxStringLength(32);
        this.stackSizeBox.setTextColor(0xFFFFFF);
        this.stackSizeBox.setFocused(true);
        this.stackSizeBox.setVisible(true);
        this.stackSizeBox.setText(Integer.toString(this.container.getStackSize()));
    }

    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        switch (button.id) {
            case 0 -> addQty(1);
            case 1 -> addQty(10);
            case 2 -> addQty(100);
            case 3 -> addQty(1000);
            case 4 -> addQty(-1);
            case 5 -> addQty(-10);
            case 6 -> addQty(-100);
            case 7 -> addQty(-1000);
            default -> super.actionPerformed(button);
        }
    }

    private void addQty(final int delta) {
        long current = parseValue();
        current = Math.clamp(current + delta, 1, Integer.MAX_VALUE);
        this.stackSizeBox.setText(Long.toString(current));
        this.container.setStackSize((int) current);
    }

    private long parseValue() {
        final String text = this.stackSizeBox.getText().trim();
        if (text.isEmpty()) {
            return 0;
        }
        try {
            return Long.parseLong(text);
        } catch (final NumberFormatException e) {
            return this.container.getStackSize();
        }
    }

    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            super.keyTyped(typedChar, keyCode);
            return;
        }
        final boolean allowed = (typedChar >= '0' && typedChar <= '9')
            || keyCode == Keyboard.KEY_BACK
            || keyCode == Keyboard.KEY_DELETE
            || keyCode == Keyboard.KEY_LEFT
            || keyCode == Keyboard.KEY_RIGHT;
        if (allowed && this.stackSizeBox.textboxKeyTyped(typedChar, keyCode)) {
            commitFromBox();
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    private void commitFromBox() {
        final long value = parseValue();
        if (value >= 1) {
            this.container.setStackSize((int) Math.min(Integer.MAX_VALUE, value));
        }
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.stackSizeBox.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawBG(final int offsetX, final int offsetY, final int mouseX, final int mouseY, final float partialTicks) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY, partialTicks);
        if (this.stackSizeBox != null) {
            this.stackSizeBox.drawTextBox();
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        final long value = parseValue();
        if (value >= 1) {
            this.container.setStackSize((int) Math.min(Integer.MAX_VALUE, value));
        }
    }
}
