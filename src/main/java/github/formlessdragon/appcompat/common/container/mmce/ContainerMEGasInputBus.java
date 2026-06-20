package github.formlessdragon.appcompat.common.container.mmce;

import ae2.api.inventories.InternalInventory;
import ae2.api.inventories.PlatformInventoryWrapper;
import ae2.api.stacks.GenericStack;
import ae2.container.AEBaseContainer;
import ae2.container.SlotSemantics;
import ae2.container.guisync.GuiSync;
import ae2.container.slot.FakeSlot;
import ae2.core.definitions.AEItems;
import ae2.util.ConfigInventory;
import com.mekeng.github.common.me.inventory.impl.GasInventory;
import github.formlessdragon.appcompat.AppliedCompatibility;
import github.kasuminova.mmce.common.tile.MEGasInputBus;
import me.ramidzkh.mekae2.ae2.AEGasKey;
import me.ramidzkh.mekae2.ae2.AEGasKeyType;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.GasTankInfo;
import mekanism.api.gas.IGasHandler;
import mekanism.api.gas.IGasItem;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ContainerMEGasInputBus extends AEBaseContainer {

    private final MEGasInputBus owner;
    private final MirroredMachineUpgradeInventory upgrades;
    private final ConfigInventory configInventory;
    @GuiSync(40)
    public SyncedGasList tankGases = new SyncedGasList(Collections.emptyList());
    @GuiSync(41)
    public SyncedGasList configGases = new SyncedGasList(Collections.emptyList());
    @GuiSync(42)
    public int capacity = 8000;

    public ContainerMEGasInputBus(final MEGasInputBus owner, final EntityPlayer player) {
        super(player.inventory, owner);
        this.owner = owner;
        this.configInventory = ConfigInventory.configStacks(9)
                                              .supportedType(AEGasKeyType.TYPE)
                                              .allowOverstacking(true)
                                              .changeListener(this::pushConfigToOwner)
                                              .build();

        for (int i = 0; i < 9; i++) {
            this.addSlot(new GasConfigSlot(this.configInventory.createGuiWrapper(), i, 8 + 18 * i, 35), SlotSemantics.CONFIG);
        }

        final PlatformInventoryWrapper upgradesBacking = new PlatformInventoryWrapper(owner.getInventoryByName("upgrades"));
        this.upgrades = new MirroredMachineUpgradeInventory(owner.getVisualItemStack().getItem(), 5, upgradesBacking);
        this.setupUpgrades(this.upgrades);
        for (int i = 0; i < 5; i++) {
            this.getSlot(i + 9).xPos = 187;
            this.getSlot(i + 9).yPos = 8 + 18 * i;
        }

        this.addPlayerInventorySlots(8, 149);

        this.registerClientAction("setConfigGas", String.class, this::setConfigGas);
    }

    private static int normalizeGasAmount(final long amount) {
        if (amount < 1) {
            return 1;
        }
        if (amount > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) amount;
    }

    private static Gas parseGas(final String spec) {
        if (spec.startsWith("#")) {
            try {
                final int id = Integer.parseInt(spec.substring(1));
                final List<Gas> gases = GasRegistry.getRegisteredGasses();
                return id >= 0 && id < gases.size() ? gases.get(id) : null;
            } catch (final NumberFormatException e) {
                AppliedCompatibility.LOGGER.warn("Invalid gas registry id: {}", spec, e);
                return null;
            }
        }
        return GasRegistry.getGas(spec);
    }

    public void sendSetConfigGas(final int slotNumber, final Gas gas) {
        if (gas == null) {
            sendClientAction("setConfigGas", slotNumber + ":");
            return;
        }
        final String name = gas.getName();
        if (name != null && !name.isEmpty()) {
            sendClientAction("setConfigGas", slotNumber + ":" + name);
            return;
        }
        final int id = GasRegistry.getGasID(gas);
        if (id >= 0) {
            sendClientAction("setConfigGas", slotNumber + ":#" + id);
        }
    }

    private void setConfigGas(final String payload) {
        final int sep = payload.indexOf(':');
        if (sep < 0) {
            return;
        }
        final int slot;
        try {
            slot = Integer.parseInt(payload.substring(0, sep));
        } catch (final NumberFormatException e) {
            AppliedCompatibility.LOGGER.warn("Invalid gas config slot payload: {}", payload, e);
            return;
        }
        final String gasSpec = payload.substring(sep + 1);
        final GasInventory config = this.owner.getConfig();
        if (config == null || slot < 0 || slot >= config.size() || slot >= this.inventorySlots.size()
            || !(this.inventorySlots.get(slot) instanceof GasConfigSlot)) {
            return;
        }
        if (gasSpec.isEmpty()) {
            config.setGas(slot, null);
            this.configInventory.setStack(slot, null);
            return;
        }
        final Gas gas = parseGas(gasSpec);
        if (gas == null) {
            return;
        }
        final GasStack gasStack = new GasStack(gas, 1);
        config.setGas(slot, gasStack);
        this.configInventory.setStack(slot, new GenericStack(Objects.requireNonNull(AEGasKey.of(gasStack)), 1));
    }

    @Override
    public void broadcastChanges() {
        if (isServerSide()) {
            if (this.upgrades != null) {
                this.upgrades.pullFromBacking();
            }
            refreshCapacity();
            this.tankGases = SyncedGasList.from(this.owner.getTanks());
            this.configGases = SyncedGasList.from(this.owner.getConfig());
            pullConfigFromOwner();
        }
        super.broadcastChanges();
    }

    public MEGasInputBus getOwner() {
        return this.owner;
    }

    private void pushConfigToOwner() {
        final GasInventory config = this.owner.getConfig();
        if (config == null) {
            return;
        }
        for (int i = 0; i < this.configInventory.size(); i++) {
            final GenericStack stack = this.configInventory.getStack(i);
            if (stack == null || !(stack.what() instanceof AEGasKey gasKey)) {
                config.setGas(i, null);
                continue;
            }
            config.setGas(i, gasKey.toStack(1));
        }
    }

    private void pullConfigFromOwner() {
        final GasInventory config = this.owner.getConfig();
        if (config == null) {
            return;
        }
        for (int i = 0; i < config.size(); i++) {
            final GasStack stack = config.getGasStack(i);
            final AEGasKey key = stack == null ? null : AEGasKey.of(stack);
            if (key != null && stack.amount > 0) {
                this.configInventory.setStack(i, new GenericStack(key, 1));
            } else {
                this.configInventory.setStack(i, null);
            }
        }
    }

    private void refreshCapacity() {
        final int installed = this.upgrades == null ? 0 : this.upgrades.getInstalledUpgrades(AEItems.CAPACITY_CARD.item());
        this.capacity = (int) (Math.pow(4, installed + 1) * 2000);
        this.owner.getTanks().setCap(this.capacity);
    }

    public static final class GasConfigSlot extends FakeSlot {

        private GasConfigSlot(final InternalInventory inventory, final int slotIndex, final int x, final int y) {
            super(inventory, slotIndex, x, y);
            setNotDraggable();
        }

        public static boolean isGasFilterStack(final ItemStack stack) {
            return toGasFilterStack(stack).isEmpty() == stack.isEmpty();
        }

        private static ItemStack toGasFilterStack(final ItemStack stack) {
            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            }
            final GenericStack genericStack = GenericStack.unwrapItemStack(stack);
            if (genericStack != null) {
                if (genericStack.what() instanceof AEGasKey) {
                    return GenericStack.wrapInItemStack(new GenericStack(genericStack.what(), 1));
                }
                return ItemStack.EMPTY;
            }
            final GasStack contained = getContainedGas(stack);
            final AEGasKey key = contained == null ? null : AEGasKey.of(contained);
            if (key == null) {
                return ItemStack.EMPTY;
            }
            return GenericStack.wrapInItemStack(new GenericStack(key, 1));
        }

        private static GasStack getContainedGas(final ItemStack stack) {
            final ItemStack single = stack.copy();
            single.setCount(1);
            if (single.getItem() instanceof IGasItem gasItem) {
                final GasStack gas = gasItem.getGas(single);
                if (gas != null && gas.amount > 0) {
                    return gas.copy();
                }
            }
            if (Capabilities.GAS_HANDLER_CAPABILITY != null) {
                final IGasHandler handler = single.getCapability(Capabilities.GAS_HANDLER_CAPABILITY, null);
                if (handler != null) {
                    for (final GasTankInfo info : handler.getTankInfo()) {
                        final GasStack gas = info.getGas();
                        if (gas != null && gas.amount > 0) {
                            return gas.copy();
                        }
                    }
                }
            }
            return null;
        }

        @Override
        public boolean canSetFilterTo(final ItemStack stack) {
            if (stack.isEmpty()) {
                return super.canSetFilterTo(stack);
            }
            final ItemStack filterStack = toGasFilterStack(stack);
            return !filterStack.isEmpty() && super.canSetFilterTo(filterStack);
        }

        @Override
        public void putStack(final ItemStack stack) {
            if (stack.isEmpty()) {
                super.putStack(ItemStack.EMPTY);
                return;
            }
            final ItemStack filterStack = toGasFilterStack(stack);
            if (!filterStack.isEmpty()) {
                super.putStack(filterStack);
            }
        }

        @Override
        public void increase(final ItemStack stack) {
            putStack(stack);
        }

        @Override
        public void decrease(final ItemStack stack) {
            if (stack.isEmpty()) {
                putStack(ItemStack.EMPTY);
            } else {
                putStack(stack);
            }
        }

        @Override
        public void setGenericFilter(final GenericStack stack) {
            if (stack != null && stack.what() instanceof AEGasKey) {
                putStack(GenericStack.wrapInItemStack(new GenericStack(stack.what(), 1)));
            }
        }
    }
}
