package me.shedaniel;

import me.shedaniel.api.ConfigRegistry;
import me.shedaniel.gui.RiftMod;
import org.dimdev.riftloader.listener.InitializationListener;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import static me.shedaniel.gui.GuiModList.modList;
import static me.shedaniel.gui.GuiModList.regenerateMods;

public class RiftModList implements InitializationListener {
    
    public static RiftMod getModByID(String id) {
        if (modList == null || modList.isEmpty())
            regenerateMods();
        for(RiftMod mod : modList)
            if (mod.getId().equals(id))
                return mod;
        return null;
    }
    
    @Override
    public void onInitialization() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.riftmodlist.json");
        ConfigRegistry.registerConfig("riftmodlist", () -> {
            System.out.println("This should error. :) Erroring is normal");
            Integer.valueOf("abc");
        });
    }
    
}
