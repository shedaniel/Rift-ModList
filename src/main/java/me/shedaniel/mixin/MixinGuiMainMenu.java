package me.shedaniel.mixin;

import me.shedaniel.RiftModList;
import me.shedaniel.gui.GuiModList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen {
	
	@ModifyArg(
			method = "addSingleplayerMultiplayerButtons",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiMainMenu;addButton(Lnet/minecraft/client/gui/GuiButton;)Lnet/minecraft/client/gui/GuiButton;",
					ordinal = 2
			)
	)
	private GuiButton getRealmsButton(GuiButton original) {
		GuiButton button = new GuiButton(original.id, width / 2 + 2, original.y, 98, 20, I18n.format("menu.online")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				switchToRealms();
			}
		};
		return button;
	}
	
	@Inject(method = "addSingleplayerMultiplayerButtons", at = @At("RETURN"))
	private void onAddSingleplayerMultiplayerButtons(int y, int dy, CallbackInfo ci) {
		GuiButton button = new GuiButton(100, width / 2 - 100, y + dy * 2, 98, 20, I18n.format("riftmodlist.mods")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				if (RiftModList.guiModList == null)
					RiftModList.guiModList = new GuiModList();
				mc.displayGuiScreen(RiftModList.guiModList);
			}
		};
		addButton(button);
	}
	
	@Redirect(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiMainMenu;drawString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V",
					ordinal = 0
			)
	)
	
	private void onDrawMinecraftVersion(GuiMainMenu gui, FontRenderer fontRenderer, String s, int x, int y, int color) {
		drawString(fontRenderer, s, x, y - 10, color);
		drawString(fontRenderer, I18n.format("riftmodlist.mods_loaded", RiftLoader.instance.getMods().size()), x, y, color);
	}
	
	private void switchToRealms()
	{
		RealmsBridge realmsbridge = new RealmsBridge();
		realmsbridge.switchToRealms(this);
	}
	
}
