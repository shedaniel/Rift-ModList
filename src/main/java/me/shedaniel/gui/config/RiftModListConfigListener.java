package me.shedaniel.gui.config;

import me.shedaniel.RiftModList;
import me.shedaniel.listener.OpenModConfigListener;
import me.shedaniel.utils.ConfigValue;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class RiftModListConfigListener implements OpenModConfigListener {
    
    @Override
    public void openConfigGui(String modid) {
        if (modid.equals("riftmodlist")) {
            List<ConfigValue> list = new ArrayList<>();
            list.add(ConfigValue.createConfigValue("General", "Play with this checkbox it is fun", ConfigValue.ValueType.BOOLEAN_CHECKBOX, false));
            list.add(ConfigValue.createConfigValue("General", "Play with this button it is fun", ConfigValue.ValueType.BOOLEAN_BUTTON, false));
            list.add(ConfigValue.createSliderConfigValue("Cool Sliders", "Play with this slider it is fun", ConfigValue.ValueType.SLIDER, .5D, aDouble -> {
                return String.format("Value: %d", Math.round(aDouble * 100));
            }));
            Minecraft.getInstance().displayGuiScreen(RiftModList.getConfigScreen(this, RiftModList.guiModList, list, RiftModList.getModByID("riftmodlist"), values -> {
            
            }));
        }
    }
    
    @Override
    public boolean hasConfigGui(String modid) {
        return modid.equals("riftmodlist");
    }
    
}
