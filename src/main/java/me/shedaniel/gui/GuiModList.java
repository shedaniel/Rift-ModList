package me.shedaniel.gui;

import me.shedaniel.RiftModList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import org.dimdev.riftloader.ModInfo;
import org.dimdev.riftloader.RiftLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiModList extends GuiScreen {
	
	public static List<RiftMod> modList = new ArrayList<>();
	public static String configString;
	
	@Nullable
	private GuiModListContent guiModListContent;
	//protected GuiTextField searchBox;
	protected GuiScreen previousGui;
	private GuiButton configButton;
	
	public GuiModList() {
		configString = I18n.format("riftmodlist.config");
		regenerateMods();
	}
	
	public static void openOptifineConfig() {
		try {
			RiftModList.guiModList.close();
		} catch (Exception e) {
		}
		Minecraft.getInstance().displayGuiScreen(new GuiVideoSettings(Minecraft.getInstance().currentScreen, Minecraft.getInstance().gameSettings));
	}
	
	public static void regenerateMods() {
		modList = new ArrayList<>();
		for (ModInfo modInfo : RiftLoader.instance.getMods()) {
			RiftMod mod = new RiftMod(modInfo.id, modInfo.name, modInfo.source, false);
			mod.setAuthors(modInfo.authors);
			mod.setVersions(mod.loadValueFromJar(modInfo.source, "version"));
			mod.setUrl(mod.loadValueFromJar(modInfo.source, "url"));
			mod.setDescription(mod.loadValueFromJar(modInfo.source, "description", "A mod for Rift."));
			mod.setConfigMethod(mod.loadMethodFromJar(modInfo.source, "configure_gui"));
			if (modInfo.id.equals("optifine")) {
				mod.setUrl("https://www.optifine.net");
				mod.setDescription(I18n.format("riftmodlist.optifine.description"));
				mod.setConfigMethod(mod.loadMethodFromString("me.shedaniel.gui.GuiModList$openOptifineConfig"));
				
			} else if (modInfo.id.equals("rift")) {
				mod.setUrl("https://minecraft.curseforge.com/projects/rift");
				mod.setDescription(I18n.format("riftmodlist.rift.description"));
			}
			mod.tryLoadPackIcon(modInfo.source, mod.loadValueFromJar(modInfo.source, "icon_file", "pack.png"));
			modList.add(mod);
		}
		Collections.sort(modList, (riftMod, anotherMod) -> {
			return riftMod.getName().compareTo(anotherMod.getName());
		});
	}
	
	public void setPreviousGui(GuiScreen previousGui) {
		this.previousGui = previousGui;
	}
	
	@Override
	protected void initGui() {
		Minecraft.getInstance().keyboardListener.enableRepeatEvents(true);
		addButton(new GuiButton(501, this.width / 2 - 100, this.height - 30, 93, 20, I18n.format("riftmodlist.openFolder")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				Util.getOSType().openFile(RiftLoader.instance.modsDir);
			}
		});
		this.guiModListContent = new GuiModListContent(this, "");
		this.eventListeners.add(guiModListContent);
		this.setFocused(guiModListContent);
		/*
		this.searchBox = new GuiTextField(103, this.fontRenderer, this.width / 2 - 100, 22, 200, 20, this.searchBox) {
			@Override
			public void focusChanged(boolean var1) {
				super.focusChanged(true);
			}
			
			@Override
			public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
				guiModListContent.searchFilter(searchBox.getText(), true);
				return super.charTyped(p_charTyped_1_, p_charTyped_2_);
			}
		};
		this.children.add(searchBox);
		this.searchBox.setEnableBackgroundDrawing(true);
		this.searchBox.setCanLoseFocus(false);*/
		addButton(new GuiButton(104, this.width / 2 - 3, this.height - 30, 93, 20, I18n.format("riftmodlist.done")) {
			@Override
			public void onClick(double var1, double var3) {
				close();
				Minecraft.getInstance().displayGuiScreen(previousGui);
			}
		});
		addButton(configButton = new GuiConfigButton(105, this.width - 100, this.height - 30, 90, 20, configString) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				if (!guiModListContent.getModList().get(guiModListContent.getCurrentIndex()).runConfigMethod()) {
					displayString = I18n.format("riftmodlist.cannot_config");
					enabled = false;
				} else {
					guiModListContent.setCurrentIndex(-1);
					lastIndex = -1;
				}
			}
		});
	}
	
	public static int lastIndex = -1;
	
	@Override
	public void tick() {
		//searchBox.tick();
		try {
			if (guiModListContent.getCurrentIndex() == -1) {
				configButton.enabled = false;
				return;
			}
			if (lastIndex != guiModListContent.getCurrentIndex()) {
				lastIndex = guiModListContent.getCurrentIndex();
				configButton.displayString = configString;
				configButton.enabled = guiModListContent.getModList().get(guiModListContent.getCurrentIndex()).hasConfigMethod();
			}
		} catch (Exception e) {
		
		}
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		//searchBox.drawTextField(mouseX, mouseY, partialTicks);
		this.guiModListContent.drawScreen(mouseX, mouseY, partialTicks);
		this.drawCenteredString(this.fontRenderer, I18n.format("riftmodlist.mods"), this.width / 2, this.height / 16, 16777215);
		super.render(mouseX, mouseY, partialTicks);
	}
	
	Minecraft getMinecraftInstance() {
		return mc;
	}
	
	FontRenderer getFontRenderer() {
		return fontRenderer;
	}
	
	@Nullable
	public GuiModListContent getGuiModListContent() {
		return guiModListContent;
	}
	
}

