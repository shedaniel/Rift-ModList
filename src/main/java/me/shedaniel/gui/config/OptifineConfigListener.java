package me.shedaniel.gui.config;

import me.shedaniel.RiftModList;
import me.shedaniel.listener.OpenModConfigListener;
import me.shedaniel.utils.ConfigValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiVideoSettings;

import java.util.List;

public class OptifineConfigListener implements OpenModConfigListener {
	
	@Override
	public void openConfigGui() {
		try {
			RiftModList.guiModList.close();
		} catch (Exception e) {
		}
		Minecraft.getInstance().displayGuiScreen(new GuiVideoSettings(Minecraft.getInstance().currentScreen, Minecraft.getInstance().gameSettings));
	}
	
	@Override
	public void onSave(List<ConfigValue> values) {
	
	}
	
	@Override
	public boolean autoSaveOnGuiExit() {
		return false;
	}
	
}
