package github.formlessdragon.appcompat;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinConfigPlugin implements IMixinConfigPlugin {

    private static final String MIXIN_ROOT = "github.formlessdragon.appcompat.mixins.";

    @Override
    public void onLoad(final String mixinPackage) {
        if ("github.formlessdragon.appcompat.mixins".equals(mixinPackage)) {
            ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);
            AppCompatMixinDecisions.refreshFromEnvironment();
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
        String mixinName = mixinClassName;
        if (mixinName.startsWith(MIXIN_ROOT)) {
            mixinName = mixinName.substring(MIXIN_ROOT.length());
        }

        return AppCompatMixinDecisions.shouldApply(mixinName);
    }

    @Override
    public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {

    }
}
