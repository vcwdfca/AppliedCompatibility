package github.formlessdragon.appcompat.bridge.mmce;

import ae2.api.networking.IGrid;
import ae2.api.networking.security.IActionHost;
import ae2.api.stacks.AEKey;
import ae2.api.stacks.GenericStack;
import github.kasuminova.mmce.common.util.InfItemFluidHandler;

import java.util.List;

public interface PatternProviderCacheExtension {

    boolean push(InfItemFluidHandler handler, AEKey what, long amount);

    void appendStacks(List<GenericStack> stacks, InfItemFluidHandler handler);

    void returnStacks(IGrid grid, IActionHost host, InfItemFluidHandler handler);
}
