package github.formlessdragon.appcompat.proxy;

import github.formlessdragon.appcompat.bridge.mmce.AppCompatInitHooks;
import github.formlessdragon.appcompat.bridge.mmce.mekeng.AppCompatMekEngInitHooks;
import github.formlessdragon.appcompat.bridge.gtceu.GtceuMekanismRecipeBridge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        AppCompatInitHooks.init();
        GtceuMekanismRecipeBridge.init();
        initMekEng();
    }

    public void init(FMLInitializationEvent event) {
        AppCompatInitHooks.init();
        initMekEng();
    }

    public void postInit(FMLPostInitializationEvent event) {
        AppCompatInitHooks.init();
        initMekEng();
    }

    protected void initMekEng() {
        if (Loader.isModLoaded("modularmachinery") && Loader.isModLoaded("mekeng")) {
            AppCompatMekEngInitHooks.init();
        }
    }
}
