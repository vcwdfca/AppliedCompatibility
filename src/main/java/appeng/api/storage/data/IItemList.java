package appeng.api.storage.data;

import appeng.api.config.FuzzyMode;

import java.util.Collection;

public interface IItemList<T extends IAEStack<T>> extends Iterable<T> {

    void addStorage(T option);

    void addCrafting(T option);

    void addRequestable(T option);

    T getFirstItem();

    int size();

    void resetStatus();

    void add(T option);

    T findPrecise(T option);

    Collection<T> findFuzzy(T filter, FuzzyMode fuzzy);

    boolean isEmpty();
}
