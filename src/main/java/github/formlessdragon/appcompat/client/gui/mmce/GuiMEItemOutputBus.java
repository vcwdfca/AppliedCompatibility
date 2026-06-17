package github.formlessdragon.appcompat.client.gui.mmce;

import ae2.client.gui.Icon;
import ae2.client.gui.style.GuiStyleManager;
import ae2.client.gui.widgets.IconButton;
import ae2.container.slot.AppEngSlot;
import github.formlessdragon.appcompat.common.container.mmce.ContainerMEItemOutputBus;
import github.kasuminova.mmce.common.tile.MEItemOutputBus;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.IOException;

public class GuiMEItemOutputBus extends GuiMEBusBase<ContainerMEItemOutputBus> {

    private final ContainerMEItemOutputBus container;

    public GuiMEItemOutputBus(final MEItemOutputBus te, final EntityPlayer player) {
        super(new ContainerMEItemOutputBus(te, player), player.inventory,
            GuiStyleManager.loadStyleDoc("/screens/appcompat_me_item_output_bus.json"));
        this.container = (ContainerMEItemOutputBus) this.inventorySlots;
    }

    @Override
    public void initGui() {
        final StackSizeButton button = new StackSizeButton(this.container::requestOpenStackSize);
        this.widgets.add("stackSize", button);
        super.initGui();
    }

    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        super.actionPerformed(button);
    }

    @Override
    protected boolean shouldRenderSingleCountSlot(final Slot slot) {
        return slot instanceof AppEngSlot;
    }

    private static final class StackSizeButton extends IconButton {

        private StackSizeButton(final Runnable onPress) {
            super(onPress);
            this.setMessage(new TextComponentTranslation("gui.meitembus.stack_size.config"));
            this.setDisableBackground(false);
        }

        @Override
        protected Icon getIcon() {
            return Icon.COG;
        }

        @Override
        protected Item getItemOverlay() {
            return null;
        }

    }
}
