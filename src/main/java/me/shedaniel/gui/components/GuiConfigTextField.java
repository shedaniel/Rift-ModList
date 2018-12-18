package me.shedaniel.gui.components;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

import javax.annotation.Nullable;

public class GuiConfigTextField extends GuiTextField {
	
	public GuiConfigTextField(int p_i49853_1_, FontRenderer p_i49853_2_, int p_i49853_3_, int p_i49853_4_, int p_i49853_5_, int p_i49853_6_, @Nullable GuiTextField p_i49853_7_) {
		super(p_i49853_1_, p_i49853_2_, p_i49853_3_, p_i49853_4_, p_i49853_5_, p_i49853_6_, p_i49853_7_);
	}
	
	public void setPosY(int y) {
		this.y = y;
	}
}
