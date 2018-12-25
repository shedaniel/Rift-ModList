package me.shedaniel;

import me.shedaniel.gui.GuiConfigCategory;
import me.shedaniel.gui.GuiConfigScreen;
import me.shedaniel.gui.GuiModList;
import me.shedaniel.gui.RiftMod;
import me.shedaniel.utils.ConfigValue;
import org.dimdev.riftloader.listener.InitializationListener;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static me.shedaniel.gui.GuiModList.modList;
import static me.shedaniel.gui.GuiModList.regenerateMods;

public class RiftModList implements InitializationListener {
    
    public static GuiModList guiModList;
    
    @Override
    public void onInitialization() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.riftmodlist.json");
    }
    
    public static GuiConfigScreen getConfigScreen(List<ConfigValue> values, RiftMod mod, Consumer<List<ConfigValue>> onSave) {
        GuiConfigScreen configScreen = new GuiConfigScreen(guiModList, mod, RiftModList.guiModList.width, RiftModList.guiModList.height);
        Map<String, GuiConfigCategory> categoryMap = new HashMap<>();
        for(ConfigValue value : values) {
            String categoryName = value.getCategory();
            if (!categoryMap.containsKey(categoryName.toLowerCase())) {
                GuiConfigCategory category = new GuiConfigCategory(categoryName, configScreen);
                category.getConfigValues().add(value);
                categoryMap.put(categoryName.toLowerCase(), category);
            } else {
                GuiConfigCategory category = categoryMap.get(categoryName.toLowerCase());
                category.getConfigValues().add(value);
                categoryMap.put(categoryName.toLowerCase(), category);
            }
        }
        for(Map.Entry<String, GuiConfigCategory> entry : categoryMap.entrySet()) {
            entry.getValue().initComponents();
            configScreen.getCategories().put(entry.getKey(), entry.getValue());
            configScreen.getChildren().add(entry.getValue());
        }
        return configScreen;
    }
    
    public static RiftMod getModByID(String id) {
        if (modList == null || modList.isEmpty())
            regenerateMods();
        for(RiftMod mod : modList)
            if (mod.getId().equals(id))
                return mod;
        return null;
    }
    
}
