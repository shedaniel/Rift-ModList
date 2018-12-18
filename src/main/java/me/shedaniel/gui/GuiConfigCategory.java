package me.shedaniel.gui;

import me.shedaniel.gui.components.GuiConfigTextField;
import me.shedaniel.utils.ConfigValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiEventHandler;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiConfigCategory extends GuiEventHandler {
	
	protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
	protected static final ResourceLocation ARROWS_TEXTURES = new ResourceLocation("textures/gui/resource_packs.png");
	
	private List<ConfigValue> configValues;
	private List<IGuiEventListener> listeners;
	private GuiConfigScreen gui;
	private int width;
	private String name;
	private boolean contracted;
	private int xPos, yPos;
	
	public GuiConfigCategory(String name, GuiConfigScreen gui) {
		this.name = name;
		this.configValues = new ArrayList<>();
		this.listeners = new ArrayList<>();
		this.gui = gui;
		this.width = gui.width;
		this.contracted = false;
		this.xPos = -100;
		this.yPos = -100;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public List<ConfigValue> getConfigValues() {
		return configValues;
	}
	
	@Override
	protected List<IGuiEventListener> getChildren() {
		return new ArrayList<>();
	}
	
	public int getSize() {
		if (contracted)
			return 20;
		return configValues.size() * 24 + 20;
	}
	
	public void initComponents() {
		for (int i = 0; i < configValues.size(); i++) {
			ConfigValue value = configValues.get(i);
			System.out.println(value.getType().name());
			if (value.getType().equals(ConfigValue.ValueType.STRING)) {
				GuiConfigTextField textField = null;
				listeners.add(textField = new GuiConfigTextField(1000 + i, Minecraft.getInstance().fontRenderer, Minecraft.getInstance().fontRenderer.getStringWidth(value.getName() + ": ") + 16,
						0, 200, 16, textField));
				this.getChildren().add(textField);
			}
		}
		System.out.println(configValues.size() + "." + listeners.size());
	}
	
	public void drawCategory(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
		this.xPos = xPos;
		this.yPos = yPos;
		Minecraft.getInstance().getTextureManager().bindTexture(BUTTON_TEXTURES);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		
		int j = mouseYIn > yPos && mouseYIn < yPos + 20 ? 20 : 0;
		//Start and End
		this.drawTexturedModalRect(0, yPos, 0, 66 + j, 20, 20);
		this.drawTexturedModalRect(this.width - 20, yPos, 180, 66 + j, 20, 20);
		
		//Middle
		if (this.width > 39)
			for (int i = 20; i < width - 20; i += 10)
				this.drawTexturedModalRect(i, yPos, 10, 66 + j, 10, 20);
		
		this.drawString(Minecraft.getInstance().fontRenderer, this.name, 12, yPos + 6, mouseYIn > yPos && mouseYIn < yPos + 20 ? 16777120 : 14737632);
		
		j = mouseYIn > yPos && mouseYIn < yPos + 20 ? 32 : 0;
		Minecraft.getInstance().getTextureManager().bindTexture(ARROWS_TEXTURES);
		if (contracted)
			Gui.drawModalRectWithCustomSizedTexture(this.width - 18, yPos + 7, 114, 5 + j, 16, 16, 256, 256);
		else
			Gui.drawModalRectWithCustomSizedTexture(this.width - 18, yPos + 7, 82, 20 + j, 16, 16, 256, 256);
		if (contracted)
			return;
		
		for (int i = 0; i < configValues.size(); i++) {
			ConfigValue value = configValues.get(i);
			IGuiEventListener listener = listeners.get(i);
			if (value.getType().equals(ConfigValue.ValueType.STRING) && listener instanceof GuiConfigTextField) {
				this.drawString(Minecraft.getInstance().fontRenderer, value.getName() + ":", 10, yPos + 28 + i * 24, 16777120);
				((GuiConfigTextField) listener).setPosY(yPos + 28 + i * 24 - 4);
				((GuiConfigTextField) listener).drawTextField(mouseXIn, mouseXIn, partialTicks);
			}
		}
	}
	
	public void tick() {
	
	}
	
	public boolean mouseClicked(double mouseX, double mouseY, int p_mouseClicked_5_) {
		if (mouseY > yPos && mouseY < yPos + 20) {
			contracted = !contracted;
			Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			return true;
		}
		for (IGuiEventListener listener : listeners)
			if (listener.mouseClicked(mouseX, mouseY, p_mouseClicked_5_))
				return true;
		return false;
	}
	
	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		for (IGuiEventListener listener : listeners)
			if (listener.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_))
				return true;
		return false;
	}
	
	@Override
	public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
		for (IGuiEventListener listener : listeners)
			if (listener.charTyped(p_charTyped_1_, p_charTyped_2_))
				return true;
		return false;
	}
}