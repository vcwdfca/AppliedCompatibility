package github.formlessdragon.appcompat.bridge.mmce;

import ae2.api.networking.IGrid;
import ae2.api.networking.security.IActionHost;
import ae2.api.stacks.AEKey;
import ae2.api.stacks.GenericStack;
import github.kasuminova.mmce.common.util.InfItemFluidHandler;

import java.util.ArrayList;
import java.util.List;

public final class PatternProviderCacheExtensions {

    private static final List<PatternProviderCacheExtension> EXTENSIONS = new ArrayList<>();

    private PatternProviderCacheExtensions() {
    }

    public static synchronized void register(final PatternProviderCacheExtension extension) {
        if (!EXTENSIONS.contains(extension)) {
            EXTENSIONS.add(extension);
        }
    }

    public static boolean push(final InfItemFluidHandler handler, final AEKey what, final long amount) {
        for (final PatternProviderCacheExtension extension : snapshot()) {
            if (extension.push(handler, what, amount)) {
                return true;
            }
        }
        return false;
    }

    public static void appendStacks(final List<GenericStack> stacks, final InfItemFluidHandler handler) {
        for (final PatternProviderCacheExtension extension : snapshot()) {
            extension.appendStacks(stacks, handler);
        }
    }

    public static void returnStacks(final IGrid grid, final IActionHost host, final InfItemFluidHandler handler) {
        for (final PatternProviderCacheExtension extension : snapshot()) {
            extension.returnStacks(grid, host, handler);
        }
    }

    private static synchronized List<PatternProviderCacheExtension> snapshot() {
        return List.copyOf(EXTENSIONS);
    }
}
