package github.formlessdragon.appcompat;

import net.minecraftforge.fml.common.Loader;

public final class AppCompatMixinDecisions {

    private static boolean enableMMCE = true;
    private static boolean enableGTCEu = true;
    private static boolean mmceLoaded;
    private static boolean topLoaded;
    private static boolean mekengLoaded;
    private static boolean gtceuLoaded;

    private AppCompatMixinDecisions() {
    }

    public static void refreshFromEnvironment() {
        enableMMCE = AppCompatConfig.mixin.enableMMCE;
        enableGTCEu = AppCompatConfig.mixin.enableGTCEu;
        mmceLoaded = Loader.isModLoaded("modularmachinery");
        topLoaded = Loader.isModLoaded("theoneprobe");
        mekengLoaded = Loader.isModLoaded("mekeng");
        gtceuLoaded = Loader.isModLoaded("gregtech");
    }

    public static boolean shouldApply(final String mixinName) {
        final int split = mixinName.indexOf('.');
        if (split < 0) {
            return true;
        }

        final String group = mixinName.substring(0, split);

        return switch (group) {
            case "mmce" -> enableMMCE && mmceLoaded && shouldApplyMmce(mixinName, topLoaded, mekengLoaded);
            case "gtceu" -> enableGTCEu && gtceuLoaded;
            default -> true;
        };
    }

    private static boolean shouldApplyMmce(final String mixinName, final boolean topLoaded, final boolean mekengLoaded) {
        if (mixinName.startsWith("mmce.top")) {
            return topLoaded;
        }
        if (mixinName.startsWith("mmce.mekeng")) {
            return mekengLoaded;
        }
        return true;
    }
}
