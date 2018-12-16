package me.shedaniel.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
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
	
	@Nullable
	private GuiModListContent guiModListContent;
	protected GuiTextField searchBox;
	protected GuiScreen previousGui;
	
	public GuiModList() {
		regenerateMods();
	}
	
	public static void regenerateMods() {
		modList = new ArrayList<>();
		for (ModInfo modInfo : RiftLoader.instance.getMods()) {
			RiftMod mod = new RiftMod(modInfo.id, modInfo.name, modInfo.source, false);
			mod.setAuthors(modInfo.authors);
			mod.setVersions(mod.loadValueFromJar(modInfo.source, "version"));
			mod.setUrl(mod.loadValueFromJar(modInfo.source, "url"));
			mod.setDescription(mod.loadValueFromJar(modInfo.source, "description", "A mod for Rift."));
			if (modInfo.id.equals("optifine")) {
				mod.setUrl("https://www.optifine.net");
				mod.setDescription(I18n.format("riftmodloader.optifine.description"));
			} else if (modInfo.id.equals("rift")) {
				mod.setUrl("https://minecraft.curseforge.com/projects/rift");
				mod.setDescription(I18n.format("riftmodloader.rift.description"));
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
		this.children.add(guiModListContent);
		this.setFocused(guiModListContent);
		this.searchBox = new GuiTextField(103, this.fontRenderer, this.width / 2 - 100, 22, 200, 20, this.searchBox) {
			@Override
			public void focusChanged(boolean var1) {
				super.focusChanged(true);
			}
			
			@Override
			public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
				guiModListContent.searchFilter(searchBox.getText(), false);
				return super.charTyped(p_charTyped_1_, p_charTyped_2_);
			}
		};
		this.children.add(searchBox);
		this.searchBox.setEnableBackgroundDrawing(true);
		this.searchBox.setCanLoseFocus(false);
		addButton(new GuiButton(104, this.width / 2 - 3, this.height - 30, 93, 20, I18n.format("riftmodlist.done")) {
			@Override
			public void onClick(double var1, double var3) {
				close();
				Minecraft.getInstance().displayGuiScreen(previousGui);
			}
		});
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		searchBox.drawTextField(mouseX, mouseY, partialTicks);
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

