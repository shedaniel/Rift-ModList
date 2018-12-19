package me.shedaniel.gui.config;

import me.shedaniel.RiftModList;
import me.shedaniel.gui.components.GuiConfigTextField;
import me.shedaniel.listener.OpenModConfigListener;
import me.shedaniel.utils.ConfigValue;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class RiftModListConfigListener implements OpenModConfigListener {
	@Override
	public void openConfigGui() {
		List<ConfigValue> list = new ArrayList<>();
		list.add(ConfigValue.createConfigValue("General", "Name", GuiConfigTextField.TextFieldInputType.TEXT, ""));
		list.add(ConfigValue.createConfigValue("General", "Random Int", GuiConfigTextField.TextFieldInputType.NUMBER, 123));
		list.add(ConfigValue.createConfigValue("Developer", "Just input here", GuiConfigTextField.TextFieldInputType.TEXT, "Default"));
		Minecraft.getInstance().displayGuiScreen(RiftModList.getConfigScreen(this, RiftModList.guiModList, list, RiftModList.getModByID("riftmodlist")));
	}
	
	@Override
	public void onSave(List<ConfigValue> values) {
		values.forEach(configValue -> {
			System.out.println(configValue.toString());
		});
	}
	
	@Override
	public boolean autoSaveOnGuiExit() {
		return false;
	}
}
