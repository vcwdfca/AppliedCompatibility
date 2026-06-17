package github.formlessdragon.appcompat;

import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID)
public final class AppCompatConfig {

    public static Mixin mixin = new Mixin();

    private AppCompatConfig() {
    }

    public static final class Mixin {

        public boolean enableMMCE = true;

        public boolean enableGTCEu = true;
    }
}
