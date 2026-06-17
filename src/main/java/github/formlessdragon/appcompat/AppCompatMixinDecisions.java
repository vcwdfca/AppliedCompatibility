package github.formlessdragon.appcompat;

import net.minecraftforge.fml.common.Loader;

public final class AppCompatMixinDecisions {

    private static boolean enableMMCE = true;
    private static boolean enableGTCEu = true;
    public static final boolean mmceLoaded = Loader.isModLoaded("modularmachinery");
    public static final boolean topLoaded = Loader.isModLoaded("theoneprobe");
    public static final boolean mekengLoaded = Loader.isModLoaded("mekeng");
    public static final boolean gtceuLoaded = Loader.isModLoaded("gregtech");

    private AppCompatMixinDecisions() {
    }

    public static void refreshFromEnvironment() {
        enableMMCE = AppCompatConfig.mixin.enableMMCE;
        enableGTCEu = AppCompatConfig.mixin.enableGTCEu;
    }

    public static boolean shouldApply(final String mixinName) {
        final int split = mixinName.indexOf('.');
        if (split < 0) {
            return true;
        }

        final String group = mixinName.substring(0, split);

        return switch (group) {
            case "mmce" -> enableMMCE && mmceLoaded && shouldApplyMmce(mixinName);
            case "gtceu" -> enableGTCEu && gtceuLoaded;
            default -> true;
        };
    }

    private static boolean shouldApplyMmce(final String mixinName) {
        if (mixinName.startsWith("mmce.top")) {
            return topLoaded;
        }
        if (mixinName.startsWith("mmce.mekeng")) {
            return mekengLoaded;
        }
        return true;
    }
}
