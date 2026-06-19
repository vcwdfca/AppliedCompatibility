package appeng.fluids.util;

import appeng.api.config.FuzzyMode;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IItemList;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class FluidList implements IItemList<IAEFluidStack> {

    private final Map<IAEFluidStack, IAEFluidStack> records = new LinkedHashMap<>();

    @Override
    public void addStorage(final IAEFluidStack option) {
        getOrCreate(option).incStackSize(option.getStackSize());
    }

    @Override
    public void addCrafting(final IAEFluidStack option) {
        getOrCreate(option).setCraftable(true);
    }

    @Override
    public void addRequestable(final IAEFluidStack option) {
        getOrCreate(option).incCountRequestable(option.getStackSize());
    }

    @Override
    public IAEFluidStack getFirstItem() {
        final Iterator<IAEFluidStack> iterator = iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    @Override
    public int size() {
        return this.records.size();
    }

    @Override
    public void resetStatus() {
        for (final IAEFluidStack stack : this.records.values()) {
            stack.setCountRequestable(0);
            stack.setCraftable(false);
        }
    }

    @Override
    public void add(final IAEFluidStack option) {
        getOrCreate(option).add(option);
    }

    @Override
    public IAEFluidStack findPrecise(final IAEFluidStack option) {
        if (option == null) {
            return null;
        }
        return this.records.get(option);
    }

    @Override
    public Collection<IAEFluidStack> findFuzzy(final IAEFluidStack filter, final FuzzyMode fuzzy) {
        final ArrayList<IAEFluidStack> result = new ArrayList<>();
        if (filter == null) {
            return result;
        }
        for (final IAEFluidStack stack : this.records.values()) {
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
    public @NonNull Iterator<IAEFluidStack> iterator() {
        return this.records.values().iterator();
    }

    private IAEFluidStack getOrCreate(final IAEFluidStack option) {
        if (option == null) {
            throw new IllegalArgumentException("Cannot add a null fluid stack");
        }
        return this.records.computeIfAbsent(option, IAEFluidStack::empty);
    }
}
