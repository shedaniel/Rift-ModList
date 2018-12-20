package me.shedaniel.gui;

import me.shedaniel.RiftModList;
import me.shedaniel.gui.config.OptifineConfigListener;
import me.shedaniel.gui.config.RiftModListConfigListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import org.dimdev.riftloader.ModInfo;
import org.dimdev.riftloader.RiftLoader;

import javax.annotation.Nullable;
import java.util.*;

public class GuiModList extends GuiScreen {
	
	public static List<RiftMod> modList = new ArrayList<>();
	public static String configString;
	
	@Nullable
	private GuiModListContent guiModListContent;
	private GuiTextField searchBox;
	protected GuiScreen previousGui;
	private GuiButton configButton;
	
	public GuiModList() {
		configString = I18n.format("riftmodlist.config");
		regenerateMods();
	}
	
	public static void regenerateMods() {
		modList = new ArrayList<>();
		for (ModInfo modInfo : RiftLoader.instance.getMods()) {
			RiftMod mod = new RiftMod(modInfo.id, modInfo.name, modInfo.source, false);
			mod.setAuthors(modInfo.authors);
			mod.setVersions(mod.loadValueFromJar(modInfo.source, "version"));
			mod.setUrl(mod.loadValueFromJar(modInfo.source, "url", (modInfo.id.equals("optifine") ? "https://www.optifine.net" : modInfo.id.equals("rift") ?
					"https://minecraft.curseforge.com/projects/rift" : "Unidentified")));
			mod.setDescription(mod.loadValueFromJar(modInfo.source, "description",
					(modInfo.id.equals("optifine") ? I18n.format("riftmodlist.optifine.description") : modInfo.id.equals("rift") ?
							I18n.format("riftmodlist.rift.description") : "A mod for Rift.")));
			if (!mod.tryLoadPackIcon(modInfo.source, mod.loadValueFromJar(modInfo.source, "icon_file", "pack.png")) && modInfo.id.equals("rift")) {
				mod.setResourceLocation(new ResourceLocation("riftmodlist:textures/gui/rift_pack.png"));
			}
			mod.setConfigListener(mod.findConfigListener(mod.loadValueFromJar(modInfo.source, "config_listener", "")));
			if (modInfo.id.equals("optifine") && mod.getConfigListener() == null)
				mod.setConfigListener(new OptifineConfigListener());
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
		
		this.searchBox = new GuiTextField(103, this.fontRenderer, this.width / 2 - 100, 32, 200, 20, this.searchBox) {
			@Override
			public void setFocused(boolean var1) {
				super.setFocused(true);
			}
		};
		this.searchBox.setTextAcceptHandler((p_212350_1_, p_212350_2_) ->
		{
			this.guiModListContent.searchFilter(p_212350_2_);
		});
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
				if (!guiModListContent.getModList().get(guiModListContent.getCurrentIndex()).runConfigListener()) {
					displayString = I18n.format("riftmodlist.cannot_config");
					enabled = false;
				} else {
					guiModListContent.setCurrentIndex(-1);
					lastIndex = -1;
				}
			}
		});
		this.children.add(searchBox);
		this.children.add(guiModListContent);
		this.searchBox.setFocused(true);
		this.searchBox.setCanLoseFocus(false);
	}
	
	public void reloadSearch() {
		try {
			this.searchBox.setText("");
			this.guiModListContent.searchFilter("");
		} catch (Exception e) {
		}
	}
	
	@Override
	public boolean mouseScrolled(double p_mouseScrolled_1_) {
		return this.guiModListContent.mouseScrolled(p_mouseScrolled_1_);
	}
	
	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) ? true : this.searchBox.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}
	
	@Override
	public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
		return this.searchBox.charTyped(p_charTyped_1_, p_charTyped_2_);
	}
	
	public static int lastIndex = -1;
	
	private String searchBoxSuggestion = I18n.format("riftmodlist.search_mods");
	
	@Override
	public void tick() {
		this.searchBox.tick();
		this.searchBox.setSuggestion(searchBox.getText().equals("") ? searchBoxSuggestion : null);
		try {
			if (guiModListContent.getCurrentIndex() == -1) {
				configButton.enabled = false;
				return;
			}
			if (lastIndex != guiModListContent.getCurrentIndex()) {
				lastIndex = guiModListContent.getCurrentIndex();
				configButton.displayString = configString;
				configButton.enabled = guiModListContent.getModList().get(guiModListContent.getCurrentIndex()).hasConfigListener();
			}
		} catch (Exception e) {
		
		}
	}
	
	private String getSearchBoxText() {
		try {
			return this.searchBox.getText();
		} catch (Exception e) {
		}
		return "";
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.guiModListContent.drawScreen(mouseX, mouseY, partialTicks);
		this.searchBox.drawTextField(mouseX, mouseY, partialTicks);
		this.drawCenteredString(this.fontRenderer, I18n.format("riftmodlist.mods"), this.width / 2, 16, 16777215);
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

