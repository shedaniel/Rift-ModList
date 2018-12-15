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
import java.util.List;

public class GuiModList extends GuiScreen {
	
	private List<RiftMod> modList = new ArrayList<>();
	
	@Nullable
	private GuiModListContent guiModListContent;
	
	public GuiModList() {
		for (ModInfo modInfo : RiftLoader.instance.getMods()) {
			RiftMod mod = new RiftMod(modInfo.id, modInfo.name, modInfo.source);
			mod.setAuthors(modInfo.authors);
			modList.add(mod);
		}
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
		this.children.add(guiModListContent);
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
//
//	@Override
//	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
//		guiModListScrollable.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
//		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
//	}
}

