package me.shedaniel.listener;

import me.shedaniel.utils.ConfigValue;
import net.minecraft.client.gui.Gui;

import java.util.List;

public interface OpenModConfigListener {
	
	public void openConfigGui(String modid);
	
	public boolean hasConfigGui(String modid);
	
}
