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
		list.add(ConfigValue.createConfigValue("General", "Play with this checkbox it is fun", ConfigValue.ValueType.BOOLEAN, false));
		Minecraft.getInstance().displayGuiScreen(RiftModList.getConfigScreen(this, RiftModList.guiModList, list, RiftModList.getModByID("riftmodlist")));
	}
	
	@Override
	public void onSave(List<ConfigValue> values) {
	
	}
	
	@Override
	public boolean autoSaveOnGuiExit() {
		return false;
	}
}
