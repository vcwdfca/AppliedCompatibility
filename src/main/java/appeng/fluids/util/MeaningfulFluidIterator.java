package appeng.fluids.util;

import appeng.api.storage.data.IAEFluidStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MeaningfulFluidIterator implements Iterator<IAEFluidStack> {

    private final Iterator<IAEFluidStack> parent;
    private IAEFluidStack next;

    public MeaningfulFluidIterator(final Iterator<IAEFluidStack> parent) {
        this.parent = parent;
        advance();
    }

    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    @Override
    public IAEFluidStack next() {
        if (this.next == null) {
            throw new NoSuchElementException();
        }
        final IAEFluidStack result = this.next;
        advance();
        return result;
    }

    private void advance() {
        this.next = null;
        while (this.parent.hasNext()) {
            final IAEFluidStack candidate = this.parent.next();
            if (candidate != null && candidate.isMeaningful()) {
                this.next = candidate;
                return;
            }
        }
    }
}
