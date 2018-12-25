package me.shedaniel.mixin;

import me.shedaniel.RiftModList;
import me.shedaniel.gui.GuiModList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public class MixinGuiIngameMenu extends GuiScreen {
	
	@Inject(at = @At("RETURN"), method = "initGui()V")
	public void drawMenuButton(CallbackInfo info) {
		addButton(new GuiButton(101, width / 2 - 100, this.height / 4 + 8 + 24 * 2, 200, 20, I18n.format("riftmodlist.mods", RiftLoader.instance.getMods().size())) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				if (RiftModList.guiModList == null)
					RiftModList.guiModList = new GuiModList();
				else
					RiftModList.guiModList.getGuiModListContent().setCurrentIndex(-1);
				RiftModList.guiModList.lastIndex = -1;
				RiftModList.guiModList.setPreviousGui(Minecraft.getInstance().currentScreen);
				RiftModList.guiModList.reloadSearch();
				mc.displayGuiScreen(RiftModList.guiModList);
			}
		});
	}
	
}
