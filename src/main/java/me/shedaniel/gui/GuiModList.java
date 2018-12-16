package me.shedaniel.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import org.dimdev.riftloader.ModInfo;
import org.dimdev.riftloader.RiftLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiModList extends GuiScreen {
	
	private List<RiftMod> modList = new ArrayList<>();
	
	@Nullable
	private GuiModListContent guiModListContent;
	
	public GuiModList() {
		for (ModInfo modInfo : RiftLoader.instance.getMods()) {
			RiftMod mod = new RiftMod(modInfo.id, modInfo.name, modInfo.source, false);
			mod.setAuthors(modInfo.authors);
			mod.setVersions(mod.loadValueFromJar(modInfo.source, "version"));
			mod.setUrl(mod.loadValueFromJar(modInfo.source, "url"));
			mod.setDescription(mod.loadValueFromJar(modInfo.source, "description", "A mod for Rift."));
			if (modInfo.id.equals("optifine")) {
				mod.setUrl("https://www.optifine.net");
				mod.setDescription("OptiFine is a Minecraft optimization mod.\n" +
						"It allows Minecraft to run faster and look better with full support for HD textures and many configuration options.");
			}
			mod.tryLoadPackIcon(modInfo.source, mod.loadValueFromJar(modInfo.source, "icon_file", "pack.png"));
			modList.add(mod);
		}
		Collections.sort(modList, (riftMod, anotherMod) -> {
			return riftMod.getName().compareTo(anotherMod.getName());
		});
	}
	
	@Override
	protected void initGui() {
		addButton(new GuiButton(501, this.width / 2 - 100, this.height - 30, I18n.format("riftmodlist.openFolder")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				Util.getOSType().openFile(RiftLoader.instance.modsDir);
			}
		});
		this.guiModListContent = new GuiModListContent(this, modList);
		this.eventListeners.add(guiModListContent);
		this.setFocused(guiModListContent);
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
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

