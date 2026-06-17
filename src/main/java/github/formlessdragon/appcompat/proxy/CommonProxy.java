package github.formlessdragon.appcompat.proxy;

import github.formlessdragon.appcompat.AppCompatConfig;
import github.formlessdragon.appcompat.bridge.mmce.AppCompatMMCEHooks;
import github.formlessdragon.appcompat.bridge.mmce.mekeng.AppCompatMekEngInitHooks;
import github.formlessdragon.appcompat.bridge.gtceu.GtceuMekanismRecipeBridge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static github.formlessdragon.appcompat.AppCompatMixinDecisions.*;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {
        if (AppCompatConfig.enableMMCE) {
            if (mmceLoaded) AppCompatMMCEHooks.init();
            if (mmceLoaded && mekengLoaded) AppCompatMekEngInitHooks.init();
        }
        if (AppCompatConfig.enableGTCEu && gtceuLoaded) GtceuMekanismRecipeBridge.init();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
