package appeng.util.item;

import appeng.api.config.FuzzyMode;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class ItemList implements IItemList<IAEItemStack> {

    private final Map<IAEItemStack, IAEItemStack> records = new Object2ObjectLinkedOpenHashMap<>();

    @Override
    public void addStorage(final IAEItemStack option) {
        getOrCreate(option).incStackSize(option.getStackSize());
    }

    @Override
    public void addCrafting(final IAEItemStack option) {
        getOrCreate(option).setCraftable(true);
    }

    @Override
    public void addRequestable(final IAEItemStack option) {
        getOrCreate(option).incCountRequestable(option.getStackSize());
    }

    @Override
    public IAEItemStack getFirstItem() {
        final Iterator<IAEItemStack> iterator = iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    @Override
    public int size() {
        return this.records.size();
    }

    @Override
    public void resetStatus() {
        for (final IAEItemStack stack : this.records.values()) {
            stack.setCountRequestable(0);
            stack.setCraftable(false);
        }
    }

    @Override
    public void add(final IAEItemStack option) {
        getOrCreate(option).add(option);
    }

    @Override
    public IAEItemStack findPrecise(final IAEItemStack option) {
        if (option == null) {
            return null;
        }
        return this.records.get(option);
    }

    @Override
    public Collection<IAEItemStack> findFuzzy(final IAEItemStack filter, final FuzzyMode fuzzy) {
        final ObjectArrayList<IAEItemStack> result = new ObjectArrayList<>();
        if (filter == null) {
            return result;
        }
        for (final IAEItemStack stack : this.records.values()) {
            if (stack.fuzzyComparison(filter, fuzzy)) {
                result.add(stack);
            }
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return this.records.isEmpty();
    }

    @Override
    public @NonNull Iterator<IAEItemStack> iterator() {
        return this.records.values().iterator();
    }

    private IAEItemStack getOrCreate(final IAEItemStack option) {
        if (option == null) {
            throw new IllegalArgumentException("Cannot add a null item stack");
        }
        final IAEItemStack existing = this.records.get(option);
        if (existing != null) {
            return existing;
        }
        final IAEItemStack empty = option.empty();
        this.records.put(option, empty);
        return empty;
    }
}
